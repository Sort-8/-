package test;

import com.entity.Book;
import com.util.StringUtil;

public class StringTest {
	public static void main(String args[]) {
		System.out.println(StringUtil.getFileName());
		Book book = new Book();
		book.setBook_id(11);
		System.out.println(StringUtil.isNum( ""+book.getBook_id() ));
		System.out.println(StringUtil.getParmName("user"));
		
		System.out.println(StringUtil.isNum("321.4"));
		System.out.println(Math.round(Double.valueOf("3213.0")));
	}
}
