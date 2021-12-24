package com.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 连接池工具类
 * 2021/6/8
 * @author 庞海
 *
 */
public final class ConnectionPool {  
    //使用单例模式创建数据库连接池  
    private static ConnectionPool instance;  
    private static ComboPooledDataSource dataSource;  

    private ConnectionPool() throws SQLException, PropertyVetoException {  
        dataSource = new ComboPooledDataSource();  

        dataSource.setUser(Constant.USR);     //用户名  
        dataSource.setPassword(Constant.PWD); //密码  
        dataSource.setJdbcUrl(Constant.URL);//数据库地址  
        dataSource.setDriverClass(Constant.DRIVER);  
        dataSource.setInitialPoolSize(Constant.InitialPoolSize); //初始化连接数  
        dataSource.setMinPoolSize(Constant.MinPoolSize);//最小连接数  
        dataSource.setMaxPoolSize(Constant.MaxPoolSize);//最大连接数  
        dataSource.setMaxStatements(Constant.MaxStatements);//最长等待时间  
        dataSource.setMaxIdleTime(Constant.MaxIdleTime);//最大空闲时间，单位毫秒  
    }  

    public static final ConnectionPool getInstance() {  
        if (instance == null) {  
            try {  
                instance = new ConnectionPool();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return instance;  
    }  

    public synchronized final Connection getConnection() {  
        Connection conn = null;  
        try {  
            conn = dataSource.getConnection();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return conn;  
    }  
}  