package com.util;

public class Constant {
	
	/**
	 * 数据库连接常量
	 */
	public static final String DATABASE = "library"; 					//数据库
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 	//驱动
	public static final String URL = "jdbc:mysql://localhost:3306/"+ 
	DATABASE+"?characterEncoding=utf-8"; 								//连接地址
	public static final String USR = "root";							//用户名
	public static final String PWD = "123456";							//密码
	public static int InitialPoolSize = 10;								//初始化连接数
	public static int MinPoolSize = 1;									//最小连接数
	public static int MaxPoolSize = 10;									//最大连接数
	public static int MaxStatements = 50;								//最长等待时间
	public static int MaxIdleTime = 60;									//最大空闲时间，单位毫秒
	
	/**
     * 数据请求返回码
     * */
    public static final int RESCODE_SUCCESS = 1000;             //成功
    public static final int RESCODE_SUCCESS_MSG = 1001;         //成功(有返回信息)
    public static final int RESCODE_EXCEPTION = 1002;           //请求抛出异常
    public static final int RESCODE_NOLOGIN = 1003;             //未登录状态
    public static final int RESCODE_NOEXIST = 1004;        	    //查询结果为空
    public static final int RESCODE_NOAUTH = 1005;              //无操作权限
    public static final int RESCODE_LOGINEXPIRE = 1006;         //登录过期
    public static final int RESCODE_INSERTERROR = 1007;         //插入失败
    public static final int RESCODE_MODIFYERROR= 1008;          //修改失败
    public static final int RESCODE_DELETEERROR = 1010;         //删除失败
	public static final Integer RESCODE_EXIST = 1011;  			//帐号已存在
	
	/**
	 * 全局编码
	 */
	public static final String REQUESTCODE = "utf-8";
	public static final String JSONREPONSECODE = "text/json;charset=UTF-8";
	public static final String HTMLREPONSECODE = "text/html;charset=UTF-8";
	
	/**
	 * 验证码配置
	 */
	public static int MaxImgCodeActiveTime = 600; 	//验证码有效期：600秒
	public static int Imgwidth = 100; 				//图片宽度
	public static int ImgHeight = 200; 				//图片高度
	public static int CodeCount = 4; 				//验证码字符数
	public static int LineWidth = 4; 				//干扰线宽度
	public static int ValidateCode = 1;				//验证码标识
	public static int IdentityCode = 2;				//身份标识
	
	
	/**
	 * 权限配置
	 */
	public static int AuthUser = 0; //用户权限标识
	public static int AuthAdmin = 1; //管理员权限标识

	public static final int JWT_ERRCODE_EXPIRE = 1010;         //token过期
    public static final int JWT_ERRCODE_FAIL = 1012;         //验证不通过
    public static final int JWT_RESCODE_SUCCESS = 1013;     //验证通过
    public static final long JWT_EXPIRE_TIME = 60*10;    //10分钟过期时间
    public static final String JWT_SECRET = "ioSjcoa21849sa"; //私钥
}
