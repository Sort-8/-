//拿到当前网址
var currentPath = getProjectPath();//获取项目路径
layui.use(['layer'],function () {
    var layer = layui.layer;
})
$(function () {
    // 页面初始化生成验证码
    window.onload = createCode();
    // 验证码切换
    $('#loginCode').click(function () {
        createCode();
    });
    // 登陆事件
    $('#loginBtn').click(function () {
        login();
    });
    // 注册事件
    $('#loginRegister').click(function () {
        register();
    });
});
// 生成验证码
function createCode() {
	document.getElementById("loginCode").src=currentPath+"/getcode?"+Math.random();
}
// 校验用户名、密码
function validateCode() {
    var loginUsername = $('#loginUsername').val();
    var loginPassword = $('#loginPassword').val();
    var loginImgCode = $('#loginCard').val();
    if ($.trim(loginUsername) == '' || $.trim(loginUsername).length<=0){
        layer.msg('帐号不能为空', {icon: 2,time: 1000});
        return false;
    }
    if ($.trim(loginPassword) == '' || $.trim(loginPassword).length<=0){
        layer.msg("密码不能为空", {icon: 2,time: 1000});
        return false;
    }
    if (loginImgCode.length<=0){
    	layer.msg("验证码不能为空", {icon: 2,time: 1000});
        return false;
    }
    return true;
}
// 登录流程
function login() {
    if (!validateCode()){
        //阻断提示
    }else {
        var loginUsername = $('#loginUsername').val();
        var loginPassword = $('#loginPassword').val();
        var loginImgCode = $('#loginCard').val();
        var loginLoadIndex = layer.load(2);
        $('#loginBtn').val("正在登录...");
        $.ajax({
            type:"post",
            url:currentPath +"/user",
            data:{
            	"usr":loginUsername,
                "pwd":loginPassword,
                "method":'login',
                "imgCode":loginImgCode,
            },
            success:function (data) {
                if (data.code == 1001){ //登录成功
                	localStorage.user = JSON.stringify(data.data.obj);
                	localStorage.sessionID = data.data.sessionID;
                	localStorage.username = data.data.obj.usr;
                	window.location.href = 'index.jsp';
                }else if(data.code==1011){
                	layer.msg("验证码不匹配", {icon: 2,time: 1000});
                	$('#loginCard').val('');
                }else{
                	layer.msg("帐号或密码错误", {icon: 2,time: 1000});
                }
                layer.close(loginLoadIndex);
                $('#loginBtn').val("登录");
                createCode();
            },
            error:function () {
                layer.close(loginLoadIndex);
                layer.msg("请求超时", {icon: 2,time: 1000});
            }
        });
    }
}
//帐号是否已存在
function isExist(){
	var usr = $('#registerUsername').val();
	if(usr.length<=0){
		 layer.msg('帐号不能为空', {icon: 2,time: 1000});
		 return;
	}else{
		var registerLoadIndex = layer.load(2);
		$.ajax({
			type:"post",
			url:currentPath +"/user",
			data:{
				"method":"searchUser",
				"parmName":"usr",
				"parmValue":usr,
			},
			success:function(data){
				if(data.count==1){ //帐号已存在
					layer.msg("帐号已存在", {icon: 2,time: 1000});
					$('#registerUsername').val('');
					$('#registerUsername').focus();
				}
				layer.close(registerLoadIndex);
			},
			error:function () {
                layer.close(registerLoadIndex);
                layer.msg("请求超时", {icon: 2,time: 1000});
            }
		})
	}
	
}
// 注册流程
function register() {
    layer.open({
        type:'1',
        content:$('.registerPage'),
        title:'注册',
        area:['450px','400px'],
        btn:['注册','重置','取消'],
        closeBtn:'1',
        btn1:function (index,layero) {
            //注册回调
            layer.close(index);
            var registerUsername = $('#registerUsername').val();
            var registerPassword = $('#registerPassword').val();
            var registerWellPassword = $('#registerWellPassword').val();
            var selectValue = $('#roleSelect option:selected').val();
            var registerLoadIndex = layer.load(2);
            $.ajax({
                type:'post',
                url:currentPath +"/user",
                data:{
                	"method":"register",
                	"role_id":"2",
                	"usr":registerUsername,
                	"pwd":registerPassword,
                	"name":" ",
                	"email":" ",
                	"sex":" ",
                },
                success:function (data) {
                	if(data.code==1000){ //注册成功
                		layer.msg("注册成功", {icon: 1,time: 1000});
                	}else if(data.code==1011){
                		layer.msg("帐号已存在", {icon: 2,time: 1000});
                	}
                	layer.close(registerLoadIndex);
                },
                error:function () {
                    layer.close(registerLoadIndex);
                    layer.msg("请求超时", {icon: 2,time: 1000});
                }
            });
        },
        btn2:function (index,layero) {
            //重置回调
            resetAll();
            // 防止注册页面关闭
            return false;
        },
        btn3:function (index,layero) {
            //取消回调
        	resetAll();
        	layer.close(index);
        }
    })
}

//重置输入框
function resetAll(){
	$('#registerUsername').val("");
    $('#registerPassword').val("");
    $('#registerWellPassword').val("");
    $('#roleSelect').val("");
}
