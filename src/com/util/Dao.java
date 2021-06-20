package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库增删改查操作
 * 日期：2021/6/7
 * @author 庞海
 * @version 1.0
 * 
 */
public class Dao {
	private static final Dao dao = new Dao();
	private static boolean fuzzyQuery = false; //默认不开启模糊搜索
	private String msg = "插入成功";
	public Dao() {}
	
	/**
	 * 返回实例化的对象
	 * @return
	 */
	public static Dao instance() {
		return dao;
	}
	
	/**
	 * 开启模糊搜索
	 * @param flag
	 */
	public static Dao setfuzzyQuery(boolean flag) {
		fuzzyQuery = flag;
		return dao;
	}
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public String getErrorMsg() {
		return this.msg;
	}
	
	/**
	 * 插入一个对象到数据库
	 * @param o
	 * @return
	 */
	public int insert(Object o) {
		int row = 0;
		Class<? extends Object> clazz = o.getClass();
		String tableName = getTableName(clazz);
		Field fields[] = clazz.getDeclaredFields(); 
		Method methods[] = getGetMethod(clazz);
		//构造sql语句
		StringBuffer sql = new StringBuffer("insert into "+tableName+" value(");
		for(int i=0;i<fields.length;i++) {
			sql.append("?,");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(");");
		//执行get方法获取对象的值
		Object[] res = inokeMethod(o,methods,null);
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement st = con.prepareStatement(new String(sql));
			for(int i=0;i<res.length;i++) {
				st.setObject(i+1, res[i]);
			}
			row = st.executeUpdate();
			printExecuteSql(st); 
			con.close();
		} catch (SQLException e) {
			this.msg="插入失败：id已存在";
			e.printStackTrace();
		}
		return row;
	}

	/**
	 * map类型的数组批量插入数据库
	 * @param list
	 * @param clazz
	 * @return
	 */
	public int mapInsert(List<Map> list,Class<?> clazz) {
		int row = 0;
		if(list==null||list.isEmpty()) {
			msg = "参数list为空";
			return 0;
		}
		if(clazz==null) {
			msg = "参数class对象为空";
			return 0;
		}
		Object obj = null;
		String tableName = getTableName(clazz);
		Field fields[] = clazz.getDeclaredFields(); 
		//构造sql语句
		StringBuffer sql = new StringBuffer("insert into "+tableName+"(");
		for(int i=1;i<fields.length;i++) {  //跳过id属性
			sql.append(fields[i].getName()+",");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") value(");
		for(int i=1;i<fields.length;i++) {
			sql.append("?,");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(");");
		
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(new String(sql));
			for(int i=0;i<list.size();i++) {
				Object[] res = new Object[list.get(0).size()];
				Map<String, Object> map = list.get(i);
				for(int j=1;j<=map.size();j++) {  //跳过id属性
					ps.setObject(j, map.get(fields[j].getName()) );
				}
				System.out.println(ps);
				ps.addBatch();
				row++;
			}
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	/**
	 * 删除操作
	 * @param o
	 * @return
	 */
	public int delete(Object o) {
		int row = 0;
		Class<? extends Object> clazz = o.getClass();
		Field fields[] = clazz.getDeclaredFields();
		String tableName = getTableName(clazz);
		//构造sql语句
		StringBuffer sql = new StringBuffer("delete from "+tableName+" where ");
		sql.append(fields[0].getName());
		sql.append("=?");
		Method getter[] = getGetMethod(clazz);
		Object res[] = inokeMethod(o, getter, null);
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement st = con.prepareStatement(new String(sql));
				st.setObject(1, res[0]);
			row = st.executeUpdate();
			printExecuteSql(st); 
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	/**
	 * 更新数据库的值
	 * @param o
	 * @return
	 */
	public int update(Object o) {
		int row = 0;
		Class<? extends Object> clazz = o.getClass();
		Field fields[] = clazz.getDeclaredFields();
		String tableName = getTableName(clazz);
		//构造sql语句
		StringBuffer sql = new StringBuffer("update "+tableName+" set ");
		for(int i=1;i<fields.length;i++) {
			sql.append(fields[i].getName()+"=?,");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(" where "+fields[0].getName()+"=?");
		Method getter[] = getGetMethod(clazz);
		Object res[] = inokeMethod(o, getter, null);
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement st = con.prepareStatement(new String(sql));
			int i = 1;
			for(;i<getter.length;i++) {
				st.setObject(i, res[i]);
			}
			st.setObject(i, res[0]);
			row = st.executeUpdate();
			printExecuteSql(st); 
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	/**
	 * 简单查询数据库
	 * @param <T>
	 * @param o
	 * @return
	 */
	public <T> List<T> selectOne(Object o) {
		List<T> list = new ArrayList<T>();
		Class<? extends Object> clazz = o.getClass();
		Field fields[] = clazz.getDeclaredFields();
		String tableName = getTableName(clazz);
		Method getter[] = getGetMethod(clazz);
		Object res[] = inokeMethod(o,getter,null);
		Object values[] = new Object[res.length];
		//构造sql语句
		StringBuffer sql = new StringBuffer("select * from "+tableName+" where ");
		for(int i=0;i<res.length;i++) {
			if(!isBaseDefaultValue(res[i])) { //如果是默认值，跳过
				if(fuzzyQuery) {
					sql.append(fields[i].getName()+" like ? and ");
				}else {
					sql.append(fields[i].getName()+" = ? and ");
				}
				values[i] = res[i];
			}
		}
		sql.delete(sql.lastIndexOf("and"),sql.length()-1);
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement st = con.prepareStatement(new String(sql));
			int index=1; //从第一个下标开始赋值
			for(int i=0;i<values.length;i++) {
				if(values[i]!=null) {
					if(fuzzyQuery) {
						st.setObject(index, "%"+values[i]+"%");
					}else {
						st.setObject(index, values[i]);
					}
					index++;
				}
			}
			ResultSet rs = st.executeQuery();
			printExecuteSql(st); 
			ResultSetMetaData rsmd = rs.getMetaData(); //获取结果集的元数据
			int columns = rsmd.getColumnCount(); //获取表列数
			while(rs.next()) {
				T obj = (T) clazz.newInstance();//实例化对象
				for(int i=0;i<columns;i++) {
					String columnsName = rsmd.getColumnName(i+1); //获取列名
					Object val = rs.getObject(i+1); //获取结果集的值
					String setter = "set"+StringUtil.toUpperFirst(columnsName);
					Method method = clazz.getMethod(setter,fields[i].getType());//寻找set方法
					method.invoke(obj, val);  //执行set方法
				}
				list.add(obj);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		fuzzyQuery = false;
		return list;
	}

	/**
	 * 查询表中所有数据
	 * @param <T>
	 * @param o
	 * @return
	 */
	public <T> List<T> searchAll(Object o) {
		return this.searchByConditions(o, new ArrayList<String>(), new ArrayList<String>());
	}
	
	/**
	 * 条件查询
	 * @param <T>
	 * @param o
	 * @param parmName
	 * @param values
	 * @return
	 */
	public <T> List<T> searchByConditions(Object o,List<String> parmName,List<String> values) {
		if(parmName==null||values==null){
			msg = "参数或值为空";
			return null;
		}
		if(parmName.size()!=values.size()) {
			msg = "参数和值的长度不一致";
			return null;
		}
		List<T> list = new ArrayList<T>();
		Class<? extends Object> clazz = o.getClass();
		Field fields[] = clazz.getDeclaredFields();
		String tableName = getTableName(clazz);
		//构造sql语句
		StringBuffer sql = new StringBuffer("select * from "+tableName+" where 1=1 and ");
		for(int i=0;i<values.size();i++) {
			if(fuzzyQuery) {
				sql.append(parmName.get(i)+" like ? and ");
			}else {
				sql.append(parmName.get(i)+" = ? and ");
			}
		}
		sql.delete(sql.lastIndexOf("and"),sql.length()-1);
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement st = con.prepareStatement(new String(sql));
			int index=1; //从第一个下标开始赋值
			for(int i=0;i<values.size();i++) {
				if(fuzzyQuery) {
					st.setObject(index, "%"+values.get(i)+"%");
				}else {
					st.setObject(index, values.get(i));
				}
				index++;
			}
			printExecuteSql(st); 
			ResultSet rs = st.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData(); //获取结果集的元数据
			int columns = rsmd.getColumnCount(); //获取表列数
			while(rs.next()) {
				T obj = (T) clazz.newInstance();//实例化对象
				for(int i=0;i<columns;i++) {
					String columnsName = rsmd.getColumnName(i+1); //获取列名
					Object val = rs.getObject(i+1); //获取结果集的值
					String setter = "set"+StringUtil.toUpperFirst(columnsName);
					Method method = clazz.getMethod(setter,fields[i].getType());//寻找set方法
					method.invoke(obj, val);  //执行set方法
				}
				list.add(obj);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		fuzzyQuery = false;
		return list;
	}
	
	/**
	 * 分页查询
	 * @param <T>
	 * @param o
	 * @param parmName
	 * @param valueName
	 * @return
	 */
	public <T> List<T> pageQuery(Object o,int currentPage,int onePageNumber){
		List<T> list = new ArrayList<T>();
		Class<? extends Object> clazz = o.getClass();
		Field fields[] = clazz.getDeclaredFields();
		String tableName = getTableName(clazz);
		//构造sql语句
		StringBuffer sql = new StringBuffer("select * from "+tableName
											+" limit "
											+(currentPage-1)*onePageNumber+","
											+onePageNumber);
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement st = con.prepareStatement(new String(sql));
			ResultSet rs = st.executeQuery();
			printExecuteSql(st); 
			ResultSetMetaData rsmd = rs.getMetaData(); //获取结果集的元数据
			int columns = rsmd.getColumnCount(); //获取表列数
			while(rs.next()) {
				T obj = (T) clazz.newInstance();//实例化对象
				for(int i=0;i<columns;i++) {
					String columnsName = rsmd.getColumnName(i+1); //获取列名
					Object val = rs.getObject(i+1); //获取结果集的值
					String setter = "set"+StringUtil.toUpperFirst(columnsName);
					Method method = clazz.getMethod(setter,fields[i].getType());//寻找set方法
					method.invoke(obj, val);  //执行set方法
				}
				list.add(obj);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		fuzzyQuery = false;
		return list;
	}
	
	
	/**
	 * 将执行的sql语句输出到控制台
	 * @param st
	 */
	private void printExecuteSql(PreparedStatement st) {
		String psName = st.toString();
		if(psName.lastIndexOf(":")!=-1)
			System.out.println(psName.substring(psName.lastIndexOf(":")+1));
	}
	
	/**
	 * 返回数据库对应的表名
	 * @param clazz
	 * @return
	 */
	public String getTableName(Class<? extends Object> clazz) {
		return StringUtil.toLowerFirst(clazz.getSimpleName());
	}
	
	/**
	 * 判断对象是否为默认值
	 * @param object
	 * @return
	 */
	private boolean isBaseDefaultValue(Object object) {
		if(object==null) {
			return true;
		}else if(object.toString().equals("0")) {
			return true;
		}else if(object.toString().equals("0.0")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 执行实体方法
	 * @param entity
	 * @param methods
	 * @param args
	 * @return
	 */
	private Object[] inokeMethod(Object entity,Method[] methods,Object... args) {
		Object objs[] = new Object[methods.length];
		try {
			for(int i=0;i<methods.length;i++){
				objs[i] = methods[i].invoke(entity, args);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return objs;
	}
	
	/**
	 * 筛选get方法
	 * @param clazz
	 * @return
	 */
	private Method[] getGetMethod(Class<?> clazz) {
		Field fields[] = clazz.getDeclaredFields();
		Method methods[] = new Method[fields.length];
		for(int i=0;i<fields.length;i++) {
			String fieldName = fields[i].getName();
			try {
				String name = "get"+StringUtil.toUpperFirst(fieldName);
				methods[i] = clazz.getMethod(name, null);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return methods;
	}
}
