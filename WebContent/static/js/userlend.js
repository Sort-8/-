/**
 * 读者借阅记录
 */

var that = this;
var projectPath = getProjectPath();
var user = getUser(); 
window.user_id = user.user_id
layui.use(['table','jquery'], function(){
	$ = layui.jquery;
	var table = layui.table;
	  
	tableRender(table);
	 
	// 头部工具栏事件
	headerToolbar(table);
	
	// 侧边工具栏事件
	sideToolbar(table);
})

function tableRender(table){
	table.render({
	    elem: '#cardTable'
	    ,method:'post'
	    ,url:projectPath+'/record',
	    where:{
	    	"method":"getAllLend",
	    	"sessionID":localStorage.sessionID,
	    	"user_id":user.user_id,
	    }
	    ,toolbar: '#headBar'
	    ,cols: [[
	      {type:'checkbox'}
	      ,{field:'user_id', width:180, title: '帐号',  }
	      ,{field:'record_type', width:100, title: '类型',templet: function (d){
	    	  if(d.record_type==1)
	    		  return '<div style="color:red;">借阅</div>';
	    	  else if(d.record_type==2)
	    		  return '<div style="color:green;">归还</div>'
	    	  else
	    		  return '未知';
	      } }
	      ,{field:'book_name', title: '图书名称', width: 250 }
	      ,{field:'create_time', title: '借阅时间', width: 180, templet: function (d){
	    	  return datetimeFormat(d.create_time);
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

//侧边工具栏事件
function sideToolbar(table){
	table.on(('tool(formFilter)'), function (obj) {
		var data = obj.data;
		var layEvent = obj.event;
		var tr = obj.tr;
		switch (obj.event) {
			case 'return_book':
				returnBook(data,table);
				break;
		}
	})
}

//归还图书
function returnBook(data,table){
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
				"record_id":data.record_id,
			},
			success: function(res) {
				if (res.code == 1000) {
					layer.msg("归还成功", {icon: 1,time: 1800});
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
						where:{
							"method":"getAllLend",
					    	"sessionID":localStorage.sessionID,
					    	"user_id":user.user_id,
						}
					});
				}else{
					searchBook(table);
				}
				break;
		}	
	})
}

// 进行搜索，重新渲染
function searchBook(table){
	var isFuzzyQuery = $('#isFuzzyQuery').val();
	var value = $('#value').val();
	table.reload('cardTable',{
		where: { //设定异步数据接口的额外参数，任意设
			"method": "searchLend",
			"sessionID": localStorage.sessionID,
			"user_id": user.user_id,
			"isFuzzyQuery": isFuzzyQuery,
			"book_name": value,
		}
		, page: {
			curr: 1 //重新从第 1 页开始
		}
	});
}