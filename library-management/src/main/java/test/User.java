package test;
public class User {
    public String userId;
    public String password;
    public String name;
    public String sex;
    public int age;
    public String department;
    public String position;
    public String phone;
    public String permission;
    public int status;
	public User(String userId, String password, String name, String sex, int age, String department, String position,
			String phone, String permission, int status) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.department = department;
		this.position = position;
		this.phone = phone;
		this.permission = permission;
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", sex=" + sex + ", age=" + age
				+ ", department=" + department + ", position=" + position + ", phone=" + phone + ", permission="
				+ permission + ", status=" + status + "]";
	}
    
}
