/**
 * 基本信息
 */

layui.use(['layer'],function () {
	layer = layui.layer;
})
$(function(){
	getUserInfo();
	getUserLimit();
	getUserBorrow();
	$('#old_password').blur(function() {
		var pwd = $('#old_password')
		if(pwd.val()!='')
			validatePwd(pwd);
		else{
			layer.msg("密码不能为空", {icon: 2,time: 1000});
		}
	})
	$('#two_password').blur(function() {
		var one_pwd = $('#new_password')
		var two_pwd = $('#two_password')
		if(one_pwd.val()!=two_pwd.val()){
			layer.msg("密码不一致", {icon: 2,time: 1000});
		}
	})
})

var user = getUser();
var sessionID = localStorage.sessionID;
var currentPath = getProjectPath();
var LoadIndex;
var layer;
function getUserInfo(){
	layui.use(['layer'],function () {
	    layer = layui.layer;
	    LoadIndex = layer.load(2);
		$.ajax({
            type:"post",
            url:currentPath +"/user",
            data:{
            	"sessionID":sessionID,
                "user_id":user.user_id,
                "up_user_id":user.user_id,
                "method":'searchUser',
                "parmName":'user_id',
                "parmValue":user.user_id,
            },
            success:function (data) {
                setUserInfo(data);
                layer.close(LoadIndex);
            },
            error:function () {
                layer.close(LoadIndex);
                layer.msg("请求超时", {icon: 2,time: 1000});
            }
        });
	})
}

//设置基本信息
function setUserInfo(data){
	document.getElementById('usr').value=(data.data[0].usr);
	document.getElementById('name').value=(data.data[0].name);
	document.getElementById('sex').value=(data.data[0].sex);
	document.getElementById('email').value=(data.data[0].email);
}

//获取用户限制
function getUserLimit(){
	layui.use(['layer'],function () {
	    layer = layui.layer;
	    LoadIndex = layer.load(2);
		$.ajax({
            type:"post",
            url:currentPath +"/record",
            data:{
            	"sessionID":sessionID,
                "user_id":user.user_id,
                "method":'getMyLend',
            },
            success:function (data) {
                if (data.code == 1000){ //登录成功
                	setUserLimit(data);
                }
                layer.close(LoadIndex);
            },
            error:function () {
                layer.close(LoadIndex);
                layer.msg("请求超时", {icon: 2,time: 1000});
            }
        });
	})
}

//设置借阅总数
function setUserLimit(data){
	var lend_num = data.data.length;
	document.getElementById('lendtotal').value=(lend_num);
}

function getUserBorrow(){
	layui.use(['layer'],function () {
	    layer = layui.layer;
	    LoadIndex = layer.load(2);
		$.ajax({
            type:"post",
            url:currentPath +"/limit",
            data:{
            	"sessionID":sessionID,
                "user_id":user.user_id,
                "method":'searchLimit',
                "role_id":user.role_id,
            },
            success:function (data) {
                if (data.code == 1000){ //登录成功
                	setUserBorrow(data);
                }
                layer.close(LoadIndex);
            },
            error:function () {
                layer.close(LoadIndex);
                layer.msg("请求超时", {icon: 2,time: 1000});
            }
        });
	})
}

function setUserBorrow(data){
	var use_num = data.data[0].max_number;
	var lendtotal = document.getElementById('lendtotal').value
	document.getElementById('uselend').value=(use_num-lendtotal);
}

//验证密码
function validatePwd(pwd){
	$.ajax({
        type:"post",
        url:currentPath +"/user",
        data:{
        	"sessionID":sessionID,
            "user_id":user.user_id,
            "method":'searchUser',
            "parmName":'usr;pwd',
            "parmValue":user.usr+";"+pwd.val(),
        },
        success:function (data) {
            if (data.count == 0){ //验证密码失败
            	layer.msg("密码错误", {icon: 2,time: 1000});
            	pwd.val('');
            	pwd.focus();
            }
            layer.close(LoadIndex);
        },
        error:function () {
            layer.close(LoadIndex);
            layer.msg("请求超时", {icon: 2,time: 1000});
        }
    });
}

//提交保存
function saveChange(){
	$.ajax({
        type:"post",
        url:currentPath +"/user",
        data:{
        	"sessionID":sessionID,
            "user_id":user.user_id,
            "up_user_id":user.user_id,
            "role_id":user.role_id,
            "method":'updateUser',
            "usr":document.getElementById('usr').value,
            "name":document.getElementById('name').value,
            "email":document.getElementById('email').value,
            "sex":document.getElementById('sex').value,
            "pwd":document.getElementById('new_password').value,
            "auth":user.auth,
        },
        success:function (data) {
            if (data.count == 1){ //验证密码失败
            	layer.msg("修改成功", {icon: 1,time: 1000});
            	document.getElementById('new_password').value='';
            	document.getElementById('old_password').value='';
            	document.getElementById('two_password').value='';
            }else{
            	layer.msg("修改失败", {icon: 2,time: 1000});
            }
            
            layer.close(LoadIndex);
        },
        error:function () {
            layer.close(LoadIndex);
            layer.msg("请求超时", {icon: 2,time: 1000});
        }
    });
}