/**
 * 用户管理
 */

var that = this;
var projectPath = getProjectPath();
var user = getUser();
layui.use(['table', 'jquery'], function () {
	$ = layui.jquery;
	var table = layui.table;
    
	// 表格渲染
	tableRender(table);
	
	// 头部工具栏事件
	headerToolbar(table);
	
	// 侧边工具栏事件
	sideToolbar(table);
	
});

//渲染表格
function tableRender(table) {
	table.render({
		elem: '#cardTable'
		, method: 'post'
		, url: projectPath + '/user',
		where: {
			"method": "getAllUser",
			"sessionID": localStorage.sessionID,
			"user_id": user.user_id,
		}
		, toolbar: '#headBar'
		, cols: [[
			{ type: 'checkbox' }
			, { field: 'usr', width: 160, title: '登录名', }
			, { field: 'name', width: 230, title: '姓名' }
			, { field: 'email', width: 150, title: '邮箱'}
			, { field: 'sex', title: '性别', width: 250 }
			, { fixed: 'right', title: '操作', toolbar: '#barDemo', width: 180 },
		]]
		, page: true
		, parseData: function (res) { //res 即为原始返回的数据
			that.curr = this.page.curr;
			if (this.page.curr) {
				result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
			} else {
				result = res.data.slice(0, this.limit);
			}
			return {
				"code": 0, //解析接口状态
				"msg": res.msg, //解析提示文本
				"count": res.count, //解析数据长度
				"data": result //解析数据列表
			};
		}
		, limit: 10
	});
	return table;
}

//侧边工具栏事件
function sideToolbar(table) {
	table.on(('tool(formFilter)'), function (obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		switch (obj.event) {
			case 'update':
				confirmupdateBook(table,data);
				break;
			case 'del':
				confirmdelBook(table,data);
			break;
		}
	})
}

//修改
function confirmupdateBook(table,data){
	layer.open({
		type: 2,
		title: '修改用户',
		area: ['460px', '530px'],
		btn: ['修改', '取消'],
		maxmin: true,
		shadeClose: true,
		content: '../public/editUser.jsp?t=0',
		success: function(layero, index) {
			var body=layer.getChildFrame('body',index);
			valuation(body,data);
		},
		yes : function(index,layero){
			var body=layer.getChildFrame('body',index);
			updateUser(body,data);
			layer.msg("修改成功", {icon: 1,time: 2000});
			layer.close(index);
			table.reload('cardTable', {})
		}
	});
}

//赋值
function valuation(body,data){
	layui.use('form', function () {
		var form = layui.form;
	    form.render();
	    body.contents().find("#user_id").val(data.user_id);
		body.contents().find("#usr").val(data.usr);
		body.contents().find("#pwd").val(data.pwd);
		body.contents().find("#auth").val(data.auth);
		body.contents().find("#name").val(data.name);
		body.contents().find("#email").val(data.email);
		body.contents().find("#sex").val(data.sex);
		body.contents().find("#role_id").val(data.role_id);
		form.render('select');
	});
}

//发送修改请求
function updateUser(body,data){
	var usr = body.contents().find("#usr").val();
	var pwd = body.contents().find("#pwd").val();
	var auth = body.contents().find("#auth").val();
	var name = body.contents().find("#name").val();
	var email = body.contents().find("#email").val();
	var sex = body.contents().find("#sex").val();
	var role_id = body.contents().find("#role_id").val();
	$.ajax({
		type:'post',
		url:projectPath+'/user',
		data:{
			"method":"updateUser",
			"user_id":user.user_id,
			"sessionID":localStorage.sessionID,
			"up_user_id":data.user_id,
			"usr":usr,
			"pwd":pwd,
			"auth":auth,
			"name":name,
			"email":email,
			"sex":sex,
			"role_id":role_id,
		},
	})
}

//删除询问
function confirmdelBook(table,data){
	layer.confirm('确定删除吗？', {
		title: '删除'
	}, function(index) {
		$.ajax({
			type: 'post',
			url:projectPath+'/user',
			data: {
				"method":"delUser",
				"user_id":user.user_id,
				"del_user_id":data.user_id,
				"sessionID":localStorage.sessionID,
			},
			success: function(res) {
				if (res.code == 1000) {
					layer.msg("删除成功", {icon: 1,time: 1000});
				} else {
					
				}
				table.reload('cardTable', {})
			}
		})
		layer.close(index);
	});
}

//头部工具栏事件
function headerToolbar(table){
	table.on('toolbar(formFilter)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		var list = checkStatus.data;
		switch (obj.event) {
			case 'search':
				if($('#value').val()==''){
					table.reload('cardTable',{
						where: {
							url:projectPath+'/user',
							method:'getAllUser',
						}
					});
				}else{
					searchUser(table);
				}
				break;
			case 'addUser':
				var addCardLayer = layer.open({
					type: 2,
					title: '添加用户',
					btn: ['添加', '取消'],
					area: ['460px', '530px'],
					maxmin: true,
					shadeClose: true,
					content: '../public/editUser.jsp?t=1',
					yes : function(index,layero){
						var body=layer.getChildFrame('body',index);
						addUser(body);
						layer.msg("添加成功", {icon: 1,time: 2000});
						layer.close(index);
						table.reload('cardTable', {})
					}
				});
				break;
			case 'importUser':
				layer.open({
					type: 2,
					title: '导入用户',
					area: ['430px', '280px'],
					maxmin: true,
					shadeClose: true,
					content: 'import.jsp',
					success: function(layero, index) {
						
					},
				});
				break;
			case 'exportUser':
				$.ajax({
					type:'post',
					url:projectPath+'/user',
					data:{
						"method":"exportUser",
						"user_id":user.user_id,
						"sessionID":localStorage.sessionID,
					},
					success:function(res){
						layer.msg("导出成功", {icon: 1,time: 2000});
					}
				})
				break;
		};
	});
}

// 进行搜索，重新渲染
function searchUser(table){
	var isFuzzyQuery = $('#isFuzzyQuery').val();
	var condition = $('#condition').val();
	var value = $('#value').val();
	table.reload('cardTable',{
		where: { //设定异步数据接口的额外参数，任意设
			"method": "searchUser",
			"sessionID": localStorage.sessionID,
			"user_id": user.user_id,
			"isFuzzyQuery": isFuzzyQuery,
			"parmName": condition,
			"parmValue": value
		}
		, page: {
			curr: 1 //重新从第 1 页开始
		}
	});
}

//发送添加用户请求
function addUser(body){
	var usr = body.contents().find("#usr").val();
	var pwd = body.contents().find("#pwd").val();
	var auth = body.contents().find("#auth").val();
	var name = body.contents().find("#name").val();
	var email = body.contents().find("#email").val();
	var sex = body.contents().find("#sex").val();
	var role_id = body.contents().find("#role_id").val();
	$.ajax({
		type:'post',
		url:projectPath+'/user',
		data:{
			"method":"addUser",
			"user_id":user.user_id,
			"sessionID":localStorage.sessionID,
			"usr":usr,
			"pwd":pwd,
			"auth":auth,
			"name":name,
			"email":email,
			"sex":sex,
			"role_id":role_id,
		},
	})
}