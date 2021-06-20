/**
 * 路由
 */
layui.config({
    base: '../static/layui/lay/extend/'
}).extend({
    notice: 'notice'
});

 //获取项目路径
var projectPath = "http://localhost:8080/library-management";
function getProjectPath(){
	return this.projectPath;
}

//获取用户对象
function getUser(){
	return JSON.parse(localStorage.user); 
}

