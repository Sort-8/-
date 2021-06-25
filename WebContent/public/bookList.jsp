<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css">
	<link rel="stylesheet" href="../static/css/table.css">

	<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/js/router.js" charset="utf-8" type="text/javascript"></script>
	<title>图书展示</title>
	
</head>
<body>
	<table class="layui-hide" id="cardTable" lay-filter="formFilter"></table>
	<!-- 头部工具栏 -->
	<script type="text/html" id="headBar">
		搜索方式：
		<div class="layui-inline" style="width:120px">
			<select id="isFuzzyQuery" name="isFuzzyQuery" >
			<option value=""></option>
			<option value="0">精确搜索</option>
			<option value="1">模糊搜索</option>
			</select>
			</div>
		&nbsp;
		条件搜索：
		<div class="layui-inline" style="width:120px">
			<select id="condition" name="condition" >
			<option value=""></option>
			<option value="code">ISBN</option>
			<option value="name">图书名称</option>
			<option value="author">作者</option>
			<option value="press">出版社</option>
			</select>
		</div>
		<div class="layui-inline">
    		<input class="layui-input" id="value" name="value"  style="width:200px">
  		</div>
		<button class="layui-btn" name="search" data-type="reload"  lay-event="search">搜索</button>
		<!-- 管理员页面-->
		<%if("1".equals(request.getParameter("t"))){%>
			<br>
			<br>
			<button class="layui-btn layui-btn-sm" name="addBook" data-type="reload"  lay-event="addBook">添加</button>
			<button class="layui-btn layui-btn-sm layui-btn-warm " name="updateBook" data-type="reload" lay-event="updateBook">修改</button>
			
			<button class="layui-btn layui-btn-sm layui-btn-normal" data-type="reload" id="importBook" lay-event="importBook">导入</button>
			<button class="layui-btn layui-btn-sm layui-btn-normal" data-type="reload" id="exportBook" lay-event="exportBook">导出</button>
		<%}%>

	</script>
	<!-- 操作按钮 -->
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-xs" lay-event="details">详情</a>
		{{#  if(d.borrow_num >= 1){ }}
			<!-- 用户页面-->
			<%if("0".equals(request.getParameter("t"))){%>
				<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="lend">借阅</a>
			<%}%>
		{{#  } }}
		<!-- 管理员页面-->
		<%if("1".equals(request.getParameter("t"))){%>
			<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
		<%}%>
	</script>
	<script type="text/javascript">
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
	</script>
	<script type="text/javascript">
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
				, toolbar: '#headBar'
				, cols: [[
					{ type: 'checkbox' }
					, { field: 'code', width: 160, title: 'IBSN', }
					, { field: 'name', width: 230, title: '图书名称' }
					, {
						field: 'url', width: 150, title: '图片',
						templet: '<div><img src="{{d.url}}" style="width:90px; height:90px;" onclick="showBigImage(this)""></div>'
					}
					, { field: 'author', title: '作者', width: 250 }
					, { field: 'press', width: 250, title: '出版社' }
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

	</script>
	
	<script type="text/javascript">
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
	</script>
	
	<script type="text/javascript">
		
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
					case 'updateBook':
						if(validate(checkStatus.data)==0){
							break;
						}else{
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
									valuation(body,list);
								},
								yes : function(index,layero){
									var body=layer.getChildFrame('body',index);
									confirmUpdateBook(body);
									layer.msg("修改成功", {icon: 1,time: 2000});
									layer.close(index);
									table.reload('cardTable', {})
								}
							});
						}
						break;
					case 'importBook':
						layer.open({
							type: 2,
							title: '导入图书',
							area: ['430px', '280px'],
							maxmin: true,
							shadeClose: true,
							content: 'import.jsp',
							success: function(layero, index) {
								layer.msg("导入成功", {icon: 1,time: 2000});
								layer.close(index);
								table.reload('cardTable', {})
							}
						});
						break;
					case 'exportBook':
						$.ajax({
							type:'post',
							url:projectPath+'/book',
							data:{
								"method":"exportBook",
								"user_id":user.user_id,
								"sessionID":localStorage.sessionID,
							},
							success:function(res){
								layer.msg("导出成功", {icon: 1,time: 2000});
								layer.close(index);
								table.reload('cardTable', {})
							}
						})
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
		
		//验证是否选中一行
		function validate(data){
			if(data.length==0){
				layer.msg("还未选择", {icon: 3,time: 1000});
				return 0;
			}else if(data.length>1){
				layer.msg("请选择一个", {icon: 3,time: 1000});
				return 0;
			}
			return 1;
		}
		
		//赋值
		function valuation(body,list){
			layui.use('form', function () {
				var form = layui.form;
			    form.render();
				body.contents().find("#name").val(list[0].name);
				body.contents().find("#preview_img").attr("src",list[0].url);
				body.contents().find("#preview_img").css("display", "block");
				body.contents().find("#code").val(list[0].code);
				body.contents().find("#sel").val(list[0].type_id);
				form.render('select','selFilter');
				body.contents().find("#"+list[0].type_id).checked;
				body.contents().find("#author").val(list[0].author);
				body.contents().find("#press").val(list[0].press);
				body.contents().find("#number").val(list[0].number);
				body.contents().find("#book_id").val(list[0].book_id);
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
	</script>
</body>

</html>