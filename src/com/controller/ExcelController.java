package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.entity.Book;
import com.entity.User;
import com.service.ExcelService;
import com.service.impl.ExcelServiceImpl;
import com.util.Constant;
import com.util.ReturnResult;
import com.util.StringUtil;
import com.util.WriteExcelFile;
import com.vo.AjaxResult;

@WebServlet("/excel")
@MultipartConfig
public class ExcelController<T> extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ExcelService excelService = new ExcelServiceImpl();
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String entity = request.getParameter("entity");
		
		if("import".equals(method)) {  //导入
			Part part = request.getPart("filePath");
			String fileName = StringUtil.getFileName(part);
			InputStream in = part.getInputStream();
			part.delete();
			int res = 0;
			try {
				if("book".equals(entity)) {
					res = excelService.importEntity(in,fileName,Book.class);
				}else {
					res = excelService.importEntity(in,fileName,User.class);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			if(res==0) {
				ajaxResult = ReturnResult.error(Constant.RESCODE_EXCEPTION, excelService.getErrorMsg());
			}else {
				ajaxResult = ReturnResult.success("导入成功", Constant.RESCODE_SUCCESS, res);
			}
			
		}else if("export".equals(method)) { //导出
			String newFileName = StringUtil.getFileName();
			InputStream in = null;
			try {
				if("book".equals(entity)) {
					List<Book> list = excelService.getAllBook();
					in = WriteExcelFile.writeExcel(list);
				}else if("user".equals(entity)) {
					List<User> list = excelService.getAllUser();
					in = WriteExcelFile.writeExcel(list);
				}
				if(in!=null) {
					response.setHeader("Content-Type", "application/x-xls"); //发送一次请求
					response.setHeader("Content-Disposition", "attachment;filename="+ newFileName);
					ServletOutputStream out = response.getOutputStream();
					out.flush();
					int aRead=0;
					byte[] b= new byte[1024];
					while ((aRead = in.read(b))!=-1 && in!=null) {
						out.write(b,0,aRead);
					}
					out.flush();
					in.close();
					out.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("导出失败");
				ajaxResult = ReturnResult.error(Constant.RESCODE_EXCEPTION, excelService.getErrorMsg());
			}
			
		}else if("template".equals(method)) { //下载模板
			String newFileName = StringUtil.getFileName();
			InputStream in = null;
			try {
				List<String> list = excelService.getEntityTemplate(entity);
				in = WriteExcelFile.writeTemplateExcel(list);
				if(in!=null) {
					response.setHeader("Content-Type", "application/x-xls"); //发送一次请求
					response.setHeader("Content-Disposition", "attachment;filename="+ newFileName);
					ServletOutputStream out = response.getOutputStream();
					out.flush();
					int aRead=0;
					byte[] b= new byte[1024];
					while ((aRead = in.read(b))!=-1 && in!=null) {
						out.write(b,0,aRead);
					}
					out.flush();
					in.close();
					out.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("模板制作失败");
				ajaxResult = ReturnResult.error(Constant.RESCODE_EXCEPTION, excelService.getErrorMsg());
			}
		}
		ReturnResult.returnResult(ajaxResult, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
