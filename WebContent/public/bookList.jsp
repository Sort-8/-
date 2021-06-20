<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="../static/layui/css/layui.css">
	<link rel="stylesheet" href="../static/css/table.css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script src="https://www.layuicdn.com/layui-v2.5.6/layui.js" type="text/javascript"></script>
	<script src="../static/layui/layui.js" type="text/javascript"></script>
	<script src="../static/js/router.js" charset="utf-8"></script>
	<title>图书展示</title>
</head>
<body>
	<table class="layui-hide" id="cardTable" lay-filter="formFilter"></table>
	<!-- 头部工具栏 -->
	<script type="text/html" id="headBar">
 		搜索方式：
		<div class="layui-inline" style="width:120px">
	  	  <select id="isFuzzyQuery" name="condition" lay-verify="required" >
        	<option value=""></option>
			<option value="0">精确搜索</option>
        	<option value="1">模糊搜索</option>
		  </select>
	 	 </div>
		&nbsp;
		条件搜索：
		<div class="layui-inline" style="width:120px">
	  	  <select id="condition" name="condition" lay-verify="required">
        	<option value=""></option>
			<option value="code">ISBN</option>
        	<option value="name">图书名称</option>
        	<option value="author">作者</option>
			<option value="press">出版社</option>
		  </select>
	 	 </div>
  		<div class="layui-inline">
    		<input class="layui-input" id="conditionValue" name="conditionValue" id="demoReload" autocomplete="off">
  		</div>
  		<button class="layui-btn" name="condition" data-type="reload"  lay-event="search">搜索</button>
		<!-- 管理员页面-->
		<%if("1".equals(request.getParameter("t"))){%>
			<br>
			<br>
			<button class="layui-btn layui-btn-sm" name="condition" data-type="reload"  lay-event="search">添加</button>
			<button class="layui-btn layui-btn-sm layui-btn-warm" name="condition" data-type="reload"  lay-event="search">修改</button>
			<button class="layui-btn layui-btn-sm layui-btn-danger" name="condition" data-type="reload"  lay-event="search">删除</button>
			
			<button class="layui-btn layui-btn-sm layui-btn-normal"  id="import" name="import" data-type="reload"  lay-event="search">导入</button>
			<button class="layui-btn layui-btn-sm layui-btn-normal" id="export" name="export" data-type="reload"  lay-event="search">导出</button>
			
		<%}%>
	</script>
	
	<!-- 操作按钮 -->
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-xs" lay-event="edit">详情</a>
		{{#  if(d.number >= 1){ }}
   			<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="lend">借阅</a>
   		{{#  } }}
		<!-- 管理员页面-->
		<%if("1".equals(request.getParameter("t"))){%>
			<a class="layui-btn layui-btn-xs" lay-event="edit">删除</a>
		<%}%>
	</script>
	<script type="text/javascript">
		var that = this;
		var projectPath = getProjectPath();
		layui.use(['table','jquery'], function(){
		  $ = layui.jquery;
		  var table = layui.table;
		  var user = getUser();
		  // 进行渲染
		  var tableIns =  table.render({
		    elem: '#cardTable'
		    ,method:'post'
		    ,url:projectPath+'/book',
		    where:{
		    	"method":"getAllBook",
		    	"sessionID":localStorage.sessionID,
		    	"user_id":user.user_id,
		    }
		    ,toolbar: '#headBar'
		    ,cols: [[
		      {type:'checkbox'}
		      ,{field:'code', width:160, title: 'IBSN',  }
		      ,{field:'name', width:230, title: '图书名称' }
		      ,{field:'url', width:150, title: '图片', 
		    	  templet: '<div><img src="{{d.url}}" style="width:90px; height:90px;" onclick="showBigImage(this)""></div>'
		    	}
		      ,{field:'author', title: '作者', width: 250 }
		      ,{field:'press', width:250, title: '出版社' }
		      ,{fixed: 'right', title:'操作', toolbar: '#barDemo',width:180},
		    ]]
		    ,page: true
		    ,parseData: function(res){ //res 即为原始返回的数据
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
		  
		  // 头部工具栏事件
		  table.on('toolbar(formFilter)', function(obj){
			  var checkStatus = table.checkStatus(obj.config.id);
			  switch(obj.event){
			  	// 条件查找借阅记录
			    case 'search':
			      var isFuzzyQuery = $('#isFuzzyQuery');
		    	  var condition = $('#condition');
		    	  // 进行搜索，重新渲染
		    	  tableIns.reload({
					    where: { //设定异步数据接口的额外参数，任意设
					    	"method":"searchBook",
					    	"sessionID":localStorage.sessionID,
		    				"user_id":user.user_id,
		    				"isFuzzyQuery":isFuzzyQuery.val(),
					    	"parmName": "32" ,
					    	"parmValue": condition.val()
					    }
					    ,page: {
					      curr: 1 //重新从第 1 页开始
					    }
					  });
			    break;
			  
			    case 'add':
			    	var addCardLayer = layer.open({
			    		type: 2,
			    		title: '添加借书证',
			    		area: ['800px', '500px'],
			  	  	  	maxmin: true,
			  	  	  	shadeClose: true,
			  	  	  	content: 'cardadd.jsp',
			    	});
			    	//layer.full(addCardLayer);
			  };
			});
		 
		  // 侧边工具栏事件
		  table.on(('tool(formFilter)'), function(obj){
		  	var data = obj.data;
		  	var layEvent = obj.event;
		  	var tr = obj.tr;
			console.log(data)
		  	switch(obj.event){
		  	  case 'edit':
		  	  	  layer.open({
		  	  	  	type: 2,
		  	  	  	title: '详情',
		  	  	  	area: ['300px', '400px'],
		  	  	  	maxmin: true,
		  	  	  	shadeClose: true,
		  	  	  	content: 'details.jsp',
		  	  	  })
		  	  	break;
		  	}
		  })
		});
	</script>
	<script type="text/javascript">
		$(function () {
			
		})
	    //显示大图片
	    function showBigImage(e) {
	        layer.open({
	            type: 1,
	            title: false,
	            closeBtn: 0,
	            shadeClose: true, //点击阴影关闭
	            area: [$(e).width-100 + 'px', $(e).height-100 + 'px'], //宽高
	            content: "<img src=" + $(e).attr('src') + " />"
	        });
	    }
	</script>
</body>
</html>