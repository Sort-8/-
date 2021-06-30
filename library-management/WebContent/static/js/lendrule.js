/**
 * 借阅规则
 */

var that = this;
var projectPath = getProjectPath();
var user = getUser();
layui.use(['table', 'jquery'], function () {
	$ = layui.jquery;
	var table = layui.table;
    
	// 表格渲染
	tableRender(table);
	
	// 侧边工具栏事件
	sideToolbar(table);
	
});
//渲染表格
function tableRender(table) {
	table.render({
		elem: '#cardTable'
		, method: 'post'
		, url: projectPath + '/limit',
		where: {
			"method": "getAllLimit",
			"sessionID": localStorage.sessionID,
			"user_id": user.user_id,
		}
		,cellMinWidth: 80
		 ,defaultToolbar: false
		, cols: [[
			  {type:'checkbox'}
			, { field: 'role_name', width: 230, title: '角色名', }
			, { field: 'max_number', width: 230, title: '最大借阅数' }
			, { field: 'max_time', width: 180, title: '最长借阅时间'}
			, { fixed: 'right', title: '操作', toolbar: '#barDemo', width: 200 },
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
				confirmUpdateLimit(table,data);
				break;
		}
	})
}

//修改
function confirmUpdateLimit(table,data){
	layer.open({
		type: 2,
		title: '修改用户',
		area: ['440px', '400px'],
		btn: ['修改', '取消'],
		maxmin: true,
		shadeClose: true,
		content: '../public/editRole.jsp',
		success: function(layero, index) {
			var body=layer.getChildFrame('body',index);
			valuation(body,data);
		},
		yes : function(index,layero){
			var body=layer.getChildFrame('body',index);
			updateLimit(body,data);
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
	    body.contents().find("#role_name").val(data.role_name);
		body.contents().find("#max_number").val(data.max_number);
		body.contents().find("#max_time").val(data.max_time);
		form.render('select','selFilter');
	});
}

//发送修改请求
function updateLimit(body,data){
	var max_number = body.contents().find("#max_number").val();
	var max_time = body.contents().find("#max_time").val();
	$.ajax({
		type:'post',
		url:projectPath+'/limit',
		data:{
			"method":"updateLimit",
			"user_id":user.user_id,
			"sessionID":localStorage.sessionID,
			"limit_id":data.limit_id,
			"role_id":data.role_id,
			"max_number":max_number,
			"max_time":max_time,
		}
	})
}
