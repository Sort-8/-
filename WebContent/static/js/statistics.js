/**
 * 图书统计
 */

var projectPath = getProjectPath();
var user = getUser();
//图书类别统计 
var option_book_type = {
	 title: {
	        left: 'center'
	    },
	    tooltip: {
	        trigger: 'item'
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	    },
	    series: [
	        {
	            name: '图书分类',
	            type: 'pie',
	            radius: '50%',
	            data: [],
	            emphasis: {
	                itemStyle: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
};

$.ajax({
	type:'post',
	url:projectPath+'/statistics',
	data:{
		"method":"getTypesNumber",
		"user_id":user.user_id,
		"sessionID":localStorage.sessionID,
	},
	success: function(res) {
		option_book_type.series[0].data = res.data
		var myChart_time = echarts.init(document.getElementById('type'));
		myChart_time.setOption(option_book_type, true);
	}
})

// 图书出版社统计
option_press = {
		title: {
	        left: 'center'
	    },
	    tooltip: {
	        trigger: 'item'
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	    },
	    series: [
	        {
	            name: '图书分类',
	            type: 'pie',
	            radius: '50%',
	            data: [],
	            emphasis: {
	                itemStyle: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
};
$.ajax({
	type:'post',
	url:projectPath+'/statistics',
	data:{
		"method":"getPressNumber",
		"user_id":user.user_id,
		"sessionID":localStorage.sessionID,
	},
	success: function(res) {
		option_press.series[0].data = res.data
		var myChart_time = echarts.init(document.getElementById('press'));
		myChart_time.setOption(option_press, true);
	}
})