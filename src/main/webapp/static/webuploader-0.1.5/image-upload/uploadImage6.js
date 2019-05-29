
    
         
        //1.Start :添加附件
		
			function uploadImage(obj){
				var imagePid   			= $($(obj).parent().find(".attachmentImagePid")[0]).val(); 		//得到上传图片的父Id
				var imageType			= $($(obj).parent().find(".attachmentImageType")[0]).val(); 	//得到上传图片的类型typd				
				var imageIds   			= $($(obj).parent().find(".attachmentImageIds")[0]).attr("id"); //得到存放上传所有图片Id文本框对象Id
				var imageOl   			= $($(obj).parent().find(".attachmentImageOl")[0]).attr("id"); 	//得到显示图片容器对象Id
				
//				if(imagePid ==null || imagePid==""){
//					alert("请先保存页面基本信息后再添加附件！");
//					return;
//				}else if(imageType ==null || imageType==""){
//					alert("请先设置添加附件的类型！");
//					return;
//				}else if(imageIds ==null || imageIds==""){
//					alert("请先设置存放附件Id文本框！");
//					return;
//				}else if(imageOl ==null || imageOl==""){
//					alert("请先设置显示图片容器对象！");
//					return;
//				}		
			
	            // 正常打开
				//增加参数,点击咨询业务添加时,form表单的返回以及确认按钮不存在.因此增加一个标记参数
	             top.$.jBox.open("iframe:"+$('#ctx').val()+"/uploadImage/openWindow6", "上传图片", 600, 500, {
	                ajaxData:{
	                	imagePid :imagePid,
	    				imageType:imageType,
	    				imageIds:imageIds,
	    				imageOl:imageOl
	                },
	                persistent:true,
	                buttons:{ "上传完毕":true}
	                , submit:function(v, h, f){
	                    if (v=="ok"){
	                    }
	                    else if (v=="clear"){
	                    }
	
	                }, loaded:function(h){
	                    $(".jbox-content", top.document).css("overflow-y","hidden");
	                }
	            }); 
	             
	        }
		
		
		function appendNewImage(imageId,imageName,imageIds,imageOl ){
			var url = $('#ctx').val()+"/uploadImage/showImage/id/"+imageId;
	
			$("#"+imageOl).prepend("<li id='"+imageId+"'> <a href='"+url+"' target='_blank'  >"+imageName+"</a>" +" &nbsp;&nbsp;<a href='javascript:' onclick='deleteImageId(\""+imageId+"\",this);'>×</a> </li>");
			appendImageId(imageId, imageIds);
		}
		
		function appendImageId(imageId, imageParentId){
			var imageParentIds = $("#"+imageParentId).val();
			if(imageParentIds!=null && imageParentIds !="" && imageParentId.length>0){
				 $("#"+imageParentId).val(imageParentIds+"|"+imageId);
			}else{
				$("#"+imageParentId).val(imageId);
			}			
		}
		
		function deleteImageId(imageId, obj){
			var imageAttachment   = $($(obj).parent().parent().parent().find(".imageAttachment")[0]); //得到隐藏的数据Id
			var imageParentIds = imageAttachment.val();  //input框中的数据
			
			var newIds = ""; //新数据
			if(imageParentIds!=null && imageParentIds !="" && imageParentIds.length>0){
				var ids = imageParentIds.split("|");
				for(i = 0; i < ids.length; i++) {
					if(imageId == ids[i] || null == ids[i] || "" == ids[i]){
						continue;
					}else{
						if(newIds.length >1 ){
							newIds = newIds+"|"+ids[i];
						}else{
							newIds = ids[i];
						}
					}
				} 
			}
			imageAttachment.val(newIds);
			//$("#"+imageId).remove();
			 url = $("#ctx").val()+'/uploadImage/deleteCounselorPicture?id='+imageId,
				$.ajax({
	                    type:'POST',
	                    url:url,
	                    dataType: 'text',
	                    success:function (){
	                        $("#"+imageId).remove();
	                    },error:function (){
	                        alert('删除失败')
	                    }
	                 }
	            )
		}