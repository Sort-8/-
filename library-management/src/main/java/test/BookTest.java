package test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.entity.Book;
import com.entity.Book_record;
import com.service.BookService;
import com.service.ExcelService;
import com.service.impl.BookServiceImpl;
import com.service.impl.ExcelServiceImpl;
import com.util.Dao;
import com.util.DateUtil;
import com.util.StringUtil;
import com.util.WriteExcelFile;

public class BookTest {
	 public static void main(String[] args) throws SQLException {  
		 BookService bookService = new BookServiceImpl();
		 List<Book> list = bookService.getPageBook(new Book(),1,5);
//		 System.out.println(list);
//		 System.out.println(bookService.getErrorMsg());
//		 System.out.println(DateUtil.getNow());
		 ExcelService excelService = new ExcelServiceImpl();
		 List<Book> list1 = excelService.getAllBook();
		 InputStream in = WriteExcelFile.writeExcel(list1);
		 List<String> names = StringUtil.getRowHeader(list1.get(0));
	 }
	 
	 
	 @Test
	 public void test() {
		Book_record book_record = new Book_record();
		book_record.setUser_id(Integer.valueOf("9"));
//		System.out.println(Dao.instance().selectOne(book_record));;
//		System.out.println(Dao.instance().searchAll(new Book_record()));;
		/*Field fields[] = Book.class.getDeclaredFields();
		for(Field field:fields) {
			System.out.println(field.getName());
		}
		*/
		BookService bookService = new BookServiceImpl();
		bookService.returnBook("17", "6","17");
		//List<String> pname = new ArrayList<String>();
		//List<String> pvalue = new ArrayList<String>();
		//pname.add("book_id");
		//pvalue.add("9");
		///List<Book> list = Dao.instance().searchByConditions(new Book(), pname, pvalue);
		//System.out.println(list.get(0));
		
		Book book = new Book();
		book.setBook_id(9);
		System.out.println(Dao.instance().selectOne(book));;
	 }
}
