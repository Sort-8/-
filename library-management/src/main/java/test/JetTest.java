package test;

public class JetTest {
	public static void main(String args[]) {
		User user = new User();
		user.setUserId("12312");
		user.setDepartment("研发部");
		String token = TokenUtil.geneToken(user);
		System.out.println(token);
		String claims = TokenUtil.parseToken(token).getSubject();
		System.out.println(TokenUtil.parseToken(token));
		System.out.println(TokenUtil.parseToken(token).get("department"));
		System.out.println(claims);
	}
}
