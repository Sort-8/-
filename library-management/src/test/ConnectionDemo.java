package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.ConnectionPool;

public class ConnectionDemo {  
    public static void main(String[] args) throws SQLException {  
        long totleTime1 = 0;
        long totleTime2 = 0;

        System.out.println("使用连接池................................");  
        long sumTime = 0;  
        for (int i = 0; i < 800; i++) {  
            long beginTime = System.currentTimeMillis();  
            Connection conn = ConnectionPool.getInstance().getConnection();  
            try {  
                PreparedStatement pstmt = conn.prepareStatement("select * from goods");  
                ResultSet rs = pstmt.executeQuery();  
                while (rs.next()) {  
                     // do nothing...  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    conn.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  

            long endTime = System.currentTimeMillis();  
            long countTime = endTime - beginTime ;
            if(i!=0)
            	sumTime+=countTime;
            totleTime1 += countTime;
            System.out.println("第" + (i + 1) + "次执行花费时间为:" + (countTime)+"  总时间："+totleTime1);  
            
        }  
        System.out.println("除去第一次执行花费时间："+(sumTime));
        
        System.out.println("不使用连接池................................");  
        
        sumTime = 0;
        for (int i = 0; i < 800; i++) {  
            long beginTime = System.currentTimeMillis();  
            MysqlDataSource mds = new MysqlDataSource();  
            mds.setURL("jdbc:mysql://localhost:3306/library?useSSL=false");  
            mds.setUser("root");  
            mds.setPassword("123456");  
            Connection conn = mds.getConnection();  
            try {  
                PreparedStatement pstmt = conn.prepareStatement("select * from goods");  
                ResultSet rs = pstmt.executeQuery();  
                while (rs.next()) {  
                                    // do nothing...  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    conn.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
            long endTime = System.currentTimeMillis(); 
            long countTime = endTime - beginTime ;
            sumTime+=countTime;
            
            totleTime2 += countTime;
            System.out.println("第" + (i + 1) + "次执行花费时间为:"  
                                + (countTime)+"  总时间："+totleTime2);  
        }  
        System.out.println("执行花费时间："+(sumTime));
    }
} 