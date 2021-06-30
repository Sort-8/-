/**
 * 图书信息
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
		, url: projectPath + '/book',
		where: {
			"method": "getAllBook",
			"sessionID": localStorage.sessionID,
			"user_id": user.user_id,
		}
		,cellMinWidth: 80
		 ,defaultToolbar: false
		, toolbar: '#headBar'
		, cols: [[
			{ type: 'checkbox' }
			, { field: 'code', width: 160, title: 'IBSN', }
			, { field: 'name', width: 230, title: '图书名称' }
			, {
				field: 'url', width: 150, title: '图片',
				templet: '<div><img src="{{d.url}}" style=" height:100px;" onclick="showBigImage(this)""></div>'
			}
			, { field: 'author', title: '作者', width: 250 }
			, { field: 'press', width: 250, title: '出版社' }
			, { fixed: 'right', title: '操作', toolbar: '#barDemo', width: 180, minWidth: 80 },
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

//显示大图片
function showBigImage(e) {
	layer.open({
		type: 1,
		title: false,
		closeBtn: 0,
		shadeClose: true, //点击阴影关闭
		area: [$(e).width - 100 + 'px', $(e).height - 100 + 'px'], //宽高
		content: "<img src=" + $(e).attr('src') + " />"
	});
}

//侧边工具栏事件
function sideToolbar(table) {
	table.on(('tool(formFilter)'), function (obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		switch (obj.event) {
			case 'details':
				getDetails(data);
				break;
			case 'lend':
				isLendLimit(table,data);
				break;
			case 'del':
				confirmdelBook(table,data);
				break;
			case 'update':
				confirmupdateBook(table,data);
				break;
		}
	})
}

//获取图书详情
function getDetails(data){
	layer.open({
		type: 2,
		title: '详情',
		area: ['300px', '350px'],
		btn: ['我知道啦'],
		maxmin: true,
		shadeClose: true,
		content: 'details.jsp',
		success: function (layero, index) {
			var body=layer.getChildFrame('body',index);
			body.contents().find("#lend_num").val(data.lend_num)
			body.contents().find("#number").val(data.number)
			body.contents().find("#borrow_num").val(data.borrow_num)
			$.ajax({
				type:'post',
				url:projectPath+'/type',
				data:{
					"method":"searchBookType",
					"user_id":user.user_id,
					"sessionID":localStorage.sessionID,
					"type_id":data.type_id,
				},
				success:function(data){
					body.contents().find("#book_type").val(data.data[0].name) 
				}
			})
		},
		end: function (index, layero) { //取消按钮
			layer.close(index);
			return false;
		},
	})
}

//是否有借阅限制
function isLendLimit(table,data){
	$.ajax({
		type:'post',
		url:projectPath+'/limit',
		data:{
			"method":"getIsLimit",
			"user_id":user.user_id,
			"sessionID":localStorage.sessionID,
		},
		success:function(res){
			if(res.code==1000&&res.data==0){
				confirmLendQuery(table,data);
			}else{
				layer.msg("已达借阅限制，请还书后再进行借阅", {icon: 2,time: 1000});
			}
		}
	})
}

//提交借阅询问
function confirmLendQuery(table,data){
	layer.confirm('确定借阅吗？', {
		title: '借阅'
	}, function(index) {
		$.ajax({
			type: 'post',
			url:projectPath+'/book',
			data: {
				"method":"lendBook",
				"user_id":user.user_id,
				"sessionID":localStorage.sessionID,
				"book_id":data.book_id,
			},
			success: function(res) {
				console.log(res)
				if (res.code == 1000) {
					layer.msg("借阅成功", {icon: 1,time: 1000});
				} else {
					
				}
				table.reload('cardTable', {})
				document.getElementById('return.jsp').contentWindow.location.reload();
				document.getElementById('baseInfo.jsp').contentWindow.location.reload();
			}
		})
		layer.close(index);
	});
}

//删除询问
function confirmdelBook(table,data){
	layer.confirm('确定删除吗？', {
		title: '删除'
	}, function(index) {
		$.ajax({
			type: 'post',
			url:projectPath+'/book',
			data: {
				"method":"delBook",
				"user_id":user.user_id,
				"sessionID":localStorage.sessionID,
				"book_id":data.book_id,
			},
			success: function(res) {
				console.log(res)
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

//修改图书
function confirmupdateBook(table,data){
	layer.open({
		type: 2,
		title: '修改图书',
		area: ['530px', '580px'],
		btn: ['修改', '取消'],
		maxmin: true,
		shadeClose: true,
		content: 'editBook.jsp?t=0',
		success: function(layero, index) {
			var body=layer.getChildFrame('body',index);
			valuation(body,data);
		},
		yes : function(index,layero){
			var body=layer.getChildFrame('body',index);
			valuation(body,data)
			confirmUpdateBook(body);
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
		body.contents().find("#name").val(data.name);
		body.contents().find("#preview_img").attr("src",data.url);
		body.contents().find("#preview_img").css("display", "none");
		body.contents().find("#code").val(data.code);
		body.contents().find("#sel").val(data.type_id);
		form.render('select','selFilter');
		body.contents().find("#"+data.type_id).checked;
		body.contents().find("#author").val(data.author);
		body.contents().find("#press").val(data.press);
		body.contents().find("#number").val(data.number);
		body.contents().find("#book_id").val(data.book_id);
		form.render();
	});
}

function confirmUpdateBook(body){
	var book_id = body.contents().find("#book_id").val();
	var name = body.contents().find("#name").val();
	var url = body.contents().find("#url").val();
	var code = body.contents().find("#code").val();
	var type_id = body.contents().find("#sel").val();
	var author = body.contents().find("#author").val();
	var press = body.contents().find("#press").val();
	var number = body.contents().find("#number").val();
	$.ajax({
		type:'post',
		url:projectPath+'/book',
		data:{
			"method":"updateBook",
			"user_id":user.user_id,
			"sessionID":localStorage.sessionID,
			"book_id":book_id,
			"name":name,
			"url":url,
			"code":code,
			"type_id":type_id,
			"author":author,
			"press":press,
			"number":number,
		},
		success:function(res){
		}
	})
}

//头部工具栏事件
function headerToolbar(table){
	table.on('toolbar(formFilter)', function (obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		var data = checkStatus.data;
		switch (obj.event) {
			case 'search':
				if($('#value').val()==''){
					table.reload('cardTable',{
						where: {
							url:projectPath+'/book',
							method:'getAllBook',
						}
					});
				}else{
					searchBook(table);
				}
				break;
			case 'addBook':
				var addCardLayer = layer.open({
					type: 2,
					title: '添加图书',
					area: ['530px', '580px'],
					btn: ['添加', '取消'],
					maxmin: true,
					shadeClose: true,
					content: 'editBook.jsp?t=1',
					yes : function(index,layero){
						var body=layer.getChildFrame('body',index);
						confirmAddBook(body);
						layer.msg("添加成功", {icon: 1,time: 2000});
						layer.close(index);
						table.reload('cardTable', {})
					}
				});
				
				break;
			case 'importBook':
				layer.open({
					type: 2,
					title: '导入图书',
					area: ['430px', '280px'],
					maxmin: true,
					shadeClose: true,
					content: 'import.jsp?entity=book',
				});
				break;
			case 'exportBook':
				exportBook();
				layer.msg("导出成功", {icon: 1,time: 2000});
				break;
		};
	});
}

// 进行搜索，重新渲染
function searchBook(table){
	var isFuzzyQuery = $('#isFuzzyQuery').val();
	var condition = $('#condition').val();
	var value = $('#value').val();
	table.reload('cardTable',{
		where: { //设定异步数据接口的额外参数，任意设
			"method": "searchBook",
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

function confirmAddBook(body){
	var book_id = body.contents().find("#book_id").val();
	var name = body.contents().find("#name").val();
	var url = body.contents().find("#url").val();
	var code = body.contents().find("#code").val();
	var type_id = body.contents().find("#sel").val();
	var author = body.contents().find("#author").val();
	var press = body.contents().find("#press").val();
	var number = body.contents().find("#number").val();
	$.ajax({
		type:'post',
		url:projectPath+'/book',
		data:{
			"method":"addBook",
			"user_id":user.user_id,
			"sessionID":localStorage.sessionID,
			"book_id":book_id,
			"name":name,
			"url":url,
			"code":code,
			"type_id":type_id,
			"author":author,
			"press":press,
			"number":number,
		},
		success:function(res){
		}
	})
}

//导出文件
function exportBook(){
	var url= projectPath+'/excel?'+"method=export&user_id="+user.user_id+"&sessionID="+localStorage.sessionID+"&entity=book"　//URL根据自己项目中实际情况而定
	var down = document.createElement('a');
	down.href = url;
	// document.body.appendChild(down);　　//此步骤可省略
	down.click();
	down.remove();　　//此步骤可省略
}