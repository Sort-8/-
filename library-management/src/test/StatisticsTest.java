package test;

import java.util.List;

import com.entity.Book;
import com.entity.Book_type;
import com.service.BookService;
import com.service.StatisticsService;
import com.service.TypeService;
import com.service.impl.BookServiceImpl;
import com.service.impl.StatisticsServiceImpl;
import com.service.impl.TypeServiceImpl;

public class StatisticsTest {
	public static void main(String args[]) {
		StatisticsService statisticsService = new StatisticsServiceImpl();
		TypeService typeService = new TypeServiceImpl();
		BookService bookService = new BookServiceImpl();
		List<Book_type> typeList = typeService.getAllType();
		List<Book> bookList = bookService.getAllBooks();
//		System.out.println( statisticsService.getTypesAndNumber(typeList,bookList));;
		System.out.println( statisticsService.getPressNumber(bookList));;
	}
}
