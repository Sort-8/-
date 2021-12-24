package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDataSource 
{
    String url = "jdbc:mysql://127.0.0.1:3306/goods?useSSL=false";
    String user = "root";
    String password = "123456";
    //连接
    private  Connection conn = null;

    //静态块
    static
    {
        //实例化驱动类
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("mysql驱动装载失败");
            e.printStackTrace();
        }
    }

    //取得数据库连接
    public Connection getConnection()
    {


        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("无法取得数据库连接");
            e.printStackTrace();
        }

        return conn;
    }

    //关闭连接
    public void closeConnection()
    {
        try 
        {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("无法关闭数据库联接");
            e.printStackTrace();
        }
    }

    public void setURL(String url) {
        this.url = url;

    }

    public void setUser(String user) {
        this.user = user;

    }

    public void setPassword(String password) {
        this.password = password;

    }
}
