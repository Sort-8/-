package test;

import java.util.List;

import com.entity.Book_record;
import com.service.RecordService;
import com.service.impl.RecordServiceImpl;

public class Book_recordTest {
	public static void main(String args[]) {
		RecordService recordService = new RecordServiceImpl();
		List<Book_record> list = recordService.searchBookRecord("数据库", true);
		System.out.println(list.get(0));
	}
}
