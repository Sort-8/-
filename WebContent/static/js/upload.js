/**
 * 上传文件
 */

//加载函数
$(function () {
	getBookType();
})

//获取图书类型
function getBookType(){
	var projectPath = getProjectPath();
	var user = getUser();
	layui.use('form', function () {
		var form = layui.form;
	    form.render();
	});
	$.ajax({
		type:'post',
		url:projectPath+'/type',
		data: {
			"method":"getAllType",
			"user_id":user.user_id,
			"sessionID":localStorage.sessionID,
		},
		success: function(res) {
			if (res.code == 1000) {
				var obj = document.getElementById('sel');
				for(var i=0;i<res.data.length;i++){
					obj.options.add(new Option(res.data[i].name,res.data[i].type_id))
				}
			} else {
				layer.msg("请重试", {icon: 2,time: 1000});
			}
		}
	})
} 

//预览图片
function changeImg(){
	$("#preview_img").css("display", "none");
	var reads = new FileReader();
	f = document.getElementById('fileSelector').files[0];
	reads.readAsDataURL(f);
	reads.onload = function(e) {
		document.getElementById('preview_img').src = this.result;
		$("#preview_img").css("display", "block");
	};
}

//上传文件
function onloadFile(){
	var selectedFile = document.getElementById('fileSelector').files[0];
	var name = getFileName(selectedFile);
	layui.use(['jquery'], function () {
		$ = layui.jquery;
	})
	var cos = new COS({
     SecretId: 'AKIDhEUTCeqlQntiHZaKnzumPowmMgkX2HFw',
     SecretKey: 'RaU7TYNwHL8etrYz92ez3rOedLlumOjn',
	 })
		cos.putObject({
	    Bucket: 'library-management-1305004688',  
	    Region: 'ap-guangzhou',     
	    Key: name,
	    StorageClass: 'STANDARD',
	    Body: selectedFile, // 上传文件对象
	    onProgress: function(progressData) {
	        //console.log(JSON.stringify(progressData));//上传文件数据
	    }
	}, function(err, data) {
	    //console.log(err || data);
	    layer.msg("上传成功", {icon: 1,time: 1000});
	    document.getElementById("url").value = "http://"+data.Location;
	    console.log(document.getElementById("url").value);//可以拿到文件地址
	});	
}

//取消上传文件
function cancelOnload(){
	$("#preview_img").css("display", "none");
}
 
//获取文件名
function getFileName(selFile){
	var min = 100;
	var max = 999;
	var num = parseInt(Math.random()*(max-min+1)+min,10);
	var time = new Date().getTime();
    var selFilename = selFile.name;
	var fileType = selFilename.toString().substring(selFilename.lastIndexOf("."));
	var fileName = time.toString().substring(time.toString.length-3) + num + fileType;
	return fileName;	
 }
