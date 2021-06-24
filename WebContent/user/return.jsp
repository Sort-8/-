<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://www.layuicdn.com/layui-v2.5.6/css/layui.css">
	<link rel="stylesheet" href="../static/css/table.css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/js/router.js" type="text/javascript"></script>
	<script src="../static/js/time.js" type="text/javascript"></script>
	<title>我的借阅</title>
</head>
<body>
	<table class="layui-hide" id="cardTable" lay-filter="formFilter"></table>
	<script type="text/html" id="headBar">
 		搜索方式：
		<div class="layui-inline" style="width:120px">
	  	  <select id="isFuzzyQuery" name="condition" lay-verify="required">
        	<option value=""></option>
			<option value="0">精确搜索</option>
        	<option value="1">模糊搜索</option>
		  </select>
	 	 </div>
		&nbsp;
		图书名称：
  		<div class="layui-inline">
    		<input class="layui-input" id="conditionValue" name="conditionValue" id="demoReload" autocomplete="off">
  		</div>
  		<button class="layui-btn" name="condition" data-type="reload"  lay-event="search">搜索</button>
		<div id=""></div>
	</script>
	
	<script type="text/html" id="barDemo">
        {{#  if(d.record_type == 1){ }}
        	<a class="layui-btn layui-btn-xs" lay-event="return_book">还书</a>
        {{#  } }}
	</script>
	
	<script type="text/javascript">
	  var that = this;
	  var projectPath = getProjectPath();
	  var user = getUser(); 
	  layui.use(['table','jquery'], function(){
		  $ = layui.jquery;
		  var table = layui.table;
		  // 进行渲染
		  var tableIns = tableRender(table);
		  
		  // 头部工具栏事件
		  headerToolbar(table);
		
		  // 侧边工具栏事件
		  sideToolbar(table);
	  })
	</script>
	
	<script type="text/javascript">
		//渲染表格
		function tableRender(table){
			table.render({
			    elem: '#cardTable'
			    ,method:'post'
			    ,url:projectPath+'/record',
			    where:{
			    	"method":"getMyLend",
			    	"sessionID":localStorage.sessionID,
			    	"user_id":user.user_id,
			    }
			    ,toolbar: '#headBar'
			    ,cols: [[
			      {type:'checkbox'}
			      ,{field:'user_id', width:130, title: '帐号',  }
			      ,{field:'record_type', width:100, title: '类型',templet: function (d){
			    	  if(d.record_type==1)
			    		  return '<div style="color:red;">借阅</div>';
			    	  else if(d.record_type==2)
			    		  return '<div style="color:green;">归还</div>'
			    	  else
			    		  return '未知';
			      } }
			      ,{field:'book_name', title: '图书名称', width: 200 }
			      ,{field:'record_time', title: '借阅时间', width: 180, templet: function (d){
			    	  return datetimeFormat(d.record_time);
			      }}
			      ,{fixed: 'right', title:'操作', toolbar: '#barDemo',width:150,templet: function (d) {
			    	  return '<div style="text-align:center">' + d.Result + '</div>'
			      }},
			    ]]
			    ,page: true
			    ,parseData: function(res){ //res 即为原始返回的数据
			    	if(res.data==null){
			    		return{
			    			"code": 0, //解析接口状态
				            "msg": "数据为空", //解析提示文本
				            "count": 0, //解析数据长度
				            "data": [] //解析数据列表
			    		}
			    	}
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
			    ,limit: 10
	  		});
		}
	</script>
	
	<script type="text/javascript">
		//侧边工具栏事件
		function sideToolbar(table){
			table.on(('tool(formFilter)'), function (obj) {
				var data = obj.data;
				var layEvent = obj.event;
				var tr = obj.tr;
				switch (obj.event) {
					case 'return_book':
						returnBook(data);
						break;
				}
			})
		}
		
		//归还图书
		function returnBook(data){
			layer.confirm('确定归还吗？', {
				title: '归还'
			}, function(index) {
				$.ajax({
					type: 'post',
					url:projectPath+'/book',
					data: {
						"method":"returnBook",
						"user_id":user.user_id,
						"sessionID":localStorage.sessionID,
						"book_id":data.book_id,
					},
					success: function(res) {
						console.log(res)
						if (res.code == 1000) {
							layer.msg("归还成功");
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
			
		}
	</script>
	
</body>
</html>