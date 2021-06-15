package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.entity.Goods;
import com.util.ConnectionPool;
import com.util.Dao;
import com.util.ReadExcelFile;
import com.util.StringUtil;

@WebServlet("/ExcelFileServlet")
@MultipartConfig
public class ExcelFileServlet<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String savePath = request.getServletContext().getRealPath("/uploadFile");
		File f = new File(savePath);
		//文件夹不存在，则创建文件夹
		if(!f.exists()) {
			f.mkdir();
		}
		Part part = request.getPart("filePath");
		String fileName = StringUtil.getFileName(part);
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmss");
		String newFileName = sdf.format(date) + StringUtil.getRandNum() + fileType;
		System.out.println(savePath + File.separator + newFileName);
		InputStream in = part.getInputStream();
		List<Map> list = ReadExcelFile.getExcelInfo(in,fileName);
		part.delete();
		if(list!=null) {
			Connection con = ConnectionPool.getInstance().getConnection();
			try {
				PreparedStatement ps = con.prepareStatement("insert into goods values(null,?,?)");
				for(int i=0;i<list.size();i++) {
					System.out.println(list.get(i));
					Iterator<Map.Entry<String, Object>> iterator1 = list.get(i).keySet().iterator();
					while (iterator1.hasNext()){
				        System.out.println("iterator1:"+iterator1.next());
				    }
				}
				Dao.instance().mapInsert(list, new Goods().getClass());
				System.out.println(Dao.instance().getErrorMsg());
				/*
				for(Goods goods:list) {
					System.out.println(goods.getGname()+" "+goods.getGprice());
					ps.setString(1, goods.getGname());
					ps.setDouble(2, goods.getGprice());
					ps.addBatch();
				}
				ps.executeBatch();
				ps.close();
				con.close();*/
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println(ReadExcelFile.getErrorInfo());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
