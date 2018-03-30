<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>编辑器</title>

<link rel="stylesheet" type="text/css" href="summernote/dist/bootstrap-3.3.4.css">
<script src="summernote/dist/jquery-1.10.2.js"></script>
<script src="summernote/dist/bootstrap-3.3.4.js"></script>

<link href="summernote/dist/summernote.css" rel="stylesheet"/>
<script src="summernote/dist/summernote.js"></script>
<script src="summernote/dist/lang/summernote-zh-CN.js"></script>    <!-- 中文-->

<style>
	.m{ width: 800px; margin-left: auto; margin-right: auto; }
</style>
<!-- <script>
$(function(){
 $('.summernote').summernote({
        height: 200,
        tabsize: 2,
        lang: 'zh-CN'
    });
});
</script> -->

<script type="text/javascript">
    //调用富文本编辑
    $(document).ready(function(){
        var $summernote = $('#a_content').summernote({
            height: 300,
            tabsize: 2,
            lang: 'zh-CN',
            minHeight: null,
            maxHeight: null,
            focus: true,
            //调用图片上传
            callbacks: {
                onImageUpload: function (files) {
                    sendFile($summernote, files[0]);
                }
            }
        });

        //ajax上传图片
        function sendFile($summernote, file) {
            var formData = new FormData();
            formData.append("file", file);
            $.ajax({
            	type: 'POST',
            	/* cache: false, */
            	contentType: false,
            	processData: false,
                url: "addimg.do",//路径是你控制器中上传图片的方法，下面controller里面我会写到
                data: formData,
                success: function(data) {
                	alert(data.a);
                    $summernote.summernote('insertImage', data.a, function ($image) {
                        $image.attr('src', data.a);
                    });
                },
                error:function(e) {
    	        	alert("连接失败"+data);
    	        }
            });
        }
    });
</script>

<script type="text/javascript">  
	$(document).ready(function() {  
	    $('#summernote').summernote({  
	        height: "500px",  
	        callbacks: { 
	            onImageUpload: function(files) { //the onImageUpload API  
	                sendFile(files[0]);
		        } 
		    }  
	    });  
	});  
	  
	function sendFile(file){
		
	    data = new FormData(); 
	    data.append("file", file);//append()插入
	    /* console.log(data);  */
	    $.ajax({  
	        data: data,  
	        type: "POST",  
	        url: "addimg.do",  
	        cache: false,  
	        contentType: false,  
	        processData: false,  
	        success: function(data) {
	        	alert(data.a);
	        	$("#summernote").summernote('insertImage', data.a, 'image name'); //插入图片
	        },
	        error:function(e) {
	        	alert("连接失败");
	        }
	    });  
	}  
</script>

</head>

<body>
	<div class="m">		
		<div id="a_content" class="summernote">summernote 1</div>
		<!-- <div class="summernote">summernote 2</div> -->
	</div>
	<form action="add.do"  method="post">
		<div id="summernote"><p>Hello Summernote</p></div>
		<input type="submit" value="提交">
	</form>
</body>
</html>
