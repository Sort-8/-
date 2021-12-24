package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entity.Book;
import com.entity.User;
import com.util.Dao;
import com.util.ReadExcelFile;

public class DaoTest {
	 public static void main(String[] args) throws SQLException {  
		File file = new File("C:\\Users\\Administrator\\Desktop\\goods.xlsx");
		System.out.println(file.isFile());
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//List<Map> list = ReadExcelFile.getExcelInfo(input,"goods.xlsx");
		//System.out.println(Dao.instance().mapInsert(list, new Goods().getClass()));
		List<String> parmName=new ArrayList<String>();
		List<String> values=new ArrayList<String>();
		parmName.add("gname");parmName.add("gprice");
		values.add("苹果");values.add("4");
//		List<Goods> list = Dao.instance().pageQuery(new User(), 1, 10); 
//		System.out.println(list.toString());
		
//		list = Dao.instance().searchByConditions(new Goods(), parmName, values);
//		System.out.println(list.toString());
		
//		List<User> bookList = Dao.instance().searchAll(new Book());
//		System.out.println(bookList.toString());
		List<Map> list = new ArrayList<Map>();
		Map<String, Object> map = new HashMap<String, Object>();
	}
	 
}
