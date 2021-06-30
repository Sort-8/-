package test;

import org.junit.Test;

import com.service.impl.UserServiceImpl;

public class LoginTest {
	@Test
	public static void main(String[] args) {
		System.out.println(new UserServiceImpl().login("111", "123"));
	}
}
