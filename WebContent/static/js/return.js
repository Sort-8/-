/**
 * 我的借阅
 */

var that = this;
var projectPath = getProjectPath();
var user = getUser(); 
layui.use(['table','jquery','laydate'], function(){
	$ = layui.jquery;
	var table = layui.table;
	var laydate = layui.laydate;
	  
	//日期时间范围
	laydate.render({
	    elem: '#time'
	    ,type: 'datetime'
	    ,range: true
  	}); 
	  // 进行渲染
	  var tableIns = tableRender(table);
	  
	  headerToolbar(table);
})

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
	    ,cellMinWidth: 80
		 ,defaultToolbar: false
	    ,cols: [[
	      {type:'checkbox'}
	      ,{field:'user_id', width:180, title: '帐号',  }
	      ,{field:'record_type', width:150, title: '类型',templet: function (d){
	    	  if(d.record_type==1)
	    		  return '<div style="color:red;">借阅</div>';
	    	  else if(d.record_type==2)
	    		  return '<div style="color:green;">归还</div>'
	    	  else
	    		  return '未知';
	      } }
	      ,{field:'book_name', title: '图书名称', width: 250 }
	      ,{field:'record_time', title: '借阅时间', width: 250, templet: function (d){
	    	  return datetimeFormat(d.create_time);
	      }}
	      ,
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