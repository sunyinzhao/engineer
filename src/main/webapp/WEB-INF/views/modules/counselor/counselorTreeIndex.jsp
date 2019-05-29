<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合规审查</title>
    <!--测试-->
	<input type="hidden" value=""/>
	<a id="changeRed" href="#" onclick="return false;" display="none";></a>
	<a id="changeBlue" href="#" onclick="return false;"></a>



	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
	<sys:message content="${message}"/>
	<input id="recordId" value="${recordId}" name="recordId" type="hidden">
	<input id="type" value="${type}" name="type" type="hidden">
	<input id="flag" value="${flag}" name="flag" type="hidden">
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle">咨询师审查</a>
		    </div>
			<div id="ztree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right">
			<iframe id="officeContent" src="" width="100%" height="91%" frameborder="0"></iframe>
		</div>

	</div>
	<script type="text/javascript">


        function updateNode(e) {
            var zTree = $.fn.zTree.getZTreeObj("ztree"),
                type = e.data.type,
                nodes = zTree.getSelectedNodes();
//             alert("asdfdsf");
                    var id = nodes[0].tId;
                    if(type=="red"){
                        $("#"+id+"_span").css({"color":"red"});
					}
                    if(type=="blue"){
                        $("#"+id+"_span").css({"color":"blue"});
                    }
        }

      /*  function ceshi(e){
            var zTree = $.fn.zTree.getZTreeObj("ztree"),
               //nodes = zTree.getSelectedNodes();
                node = zTree.getNodes(),
              nodes=  zTree.transformToArray(node);
// 				alert("asdfdsf");
               /!* alert("测试"+node);*!/
                for(var i=0  ,l=nodes.length;i<l;i++){
                    if(nodes[i].realValue=="1"){
                       j=i+1;
                        $("#ztree_"+j+"_span").css({"color":"blue"});
					}
                    if(nodes[i].realValue=="0"){
                        j=i+1;
                        $("#ztree_"+j+"_span").css({"color":"red"});
                    }

                }


        }*/



		var setting = {
            data: {
                simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}
                },

			callback: {
                    onClick:function(event, treeId, treeNode){
					var id = treeNode.pId == '0' ? '' :treeNode.pId;
					var newid = treeNode.id;
					var recordId=$("#recordId").val();
					var type = $("#type").val();
					var flag = $("#flag").val();
					$('#officeContent').attr("src","${ctx}/counselor/examine/jump?id="+newid+"&recordId="+recordId+"&type="+type+"&flag="+flag);
				}
			}
		};


			function refreshTree(){
                var recordId=$("#recordId").val();
                var url="${ctx}/counselor/examine/treeData?recordId="+recordId;
                $.ajax({
					  type: 'POST',
				      url: url,
				      dataType: 'text',
				      contentType : 'application/text;charset=UTF-8',
				      success: function(data) {
							$.fn.zTree.init($("#ztree"), setting, JSON.parse(data)).expandAll(true);
			                $("#changeRed").bind("click", {type:"red"}, updateNode);
			                $("#changeBlue").bind("click", {type:"blue"}, updateNode);
				      }
					});
// 			$.getJSON(url,function(data){

// 				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true)
//                 $("#changeRed").bind("click", {type:"red"}, updateNode);
//                 $("#changeBlue").bind("click", {type:"blue"}, updateNode);
// 			});
		}

		refreshTree();

		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
     var flag="1";
        $(window).load(function() {

				setTimeout("ceshi1()",2500);

        });

		function ceshi1(){
				while ($.isEmptyObject($.fn.zTree.getZTreeObj("ztree")) != true)
				{
	                var zTree = $.fn.zTree.getZTreeObj("ztree"),
	                node = zTree.getNodes();
	                nodes = zTree.transformToArray(node);
	                for (var i = 0, l = nodes.length; i < l; i++) {
	                    if (nodes[i].realValue == "1") {
	                        j = i + 1;
	                        $("#ztree_" + j + "_span").css({"color": "blue"});
	                    }
	                    if (nodes[i].realValue == "0") {
	                        j = i + 1;
	                        $("#ztree_" + j + "_span").css({"color": "red"});
	                    }
	                }
	                break;
				}
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>