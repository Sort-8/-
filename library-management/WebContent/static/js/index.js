/**主页js*/

//获取对象
var user = getUser();  
$(function () {
	//获取项目路径
	var currentPath = getProjectPath();
	
	//添加右上角用户名
	document.getElementById("username").innerText = "欢迎 "+user.name;
	
})
layui.use(['jquery', 'layer', 'miniAdmin','miniTongji'], function () {
	
    var $ = layui.jquery,
        layer = layui.layer,
        miniAdmin = layui.miniAdmin,
        miniTongji = layui.miniTongji;
    var iniUrl = "";
    if(user.auth==1){
		iniUrl = "static/layuimini/api/initAdmin.json";
	}else if(user.auth==0){
		iniUrl = "static/layuimini/api/initUser.json";
	}else{
		layer.msg('会话已过期，请重新登录', {
    	  icon: 2,
    	  time: 1000 
    	}, function () {
        	localStorage.clear();
            window.location = 'login.jsp';
        });
		return;
	}
    var options = {
        iniUrl: iniUrl,    // 初始化接口
        clearUrl: "static/layuimini/api/clear.json", // 缓存清理接口
        urlHashLocation: true,      // 是否打开hash定位
        bgColorDefault: false,      // 主题默认配置
        multiModule: true,          // 是否开启多模块
        menuChildOpen: false,       // 是否默认展开菜单
        loadingTime: 0,             // 初始化加载时间
        pageAnim: true,             // iframe窗口动画
        maxTabNum: 20,              // 最大的tab打开数量
    };
    miniAdmin.render(options);
    $('.login-out').on("click", function () {
        layer.msg('退出登录成功', {
        	  icon: 1,
        	  time: 1000 
        	}, function () {
            window.location = 'login.jsp';
        });
    });
});
