<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>私信demo</title>
<link type="text/css" rel="stylesheet" href="static/css/style.css" />
<link type="text/css" rel="stylesheet" href="static/css/chat.css" />
<link type="text/css" rel="stylesheet" href="static/css/jquery-ui-1.9.2.custom.min.css" />
</head>
<body>

<h1>聊天demo</h1>

<form id="joinChatForm" action="${ctx}/visitMes" onsubmit="return joinChat();">
	<p>
		<label for="user">登录用户: </label>
		<input id="user" name="requestId" type="text"/>
		<label for="user">接收者: </label>
		<input id="receiver" name="mesReceiverTarget" type="text"/>
		<input id="start" type="submit" value="登录"/>
	</p>
</form>

<div id="msg-panel" style="display: none;">
  <form action="${ctx}/chat/submitMes" id="postMessageForm">
	<div class="msg-title clearfix">
    	<div class="fl msg-name fs_24" id="msg-title">David</div>
        <div class="fr msg-close"><a href="javascript:;" id="msg-close" onclick="msgPanelClose();">X</a></div>
    </div>
	<div id="convo">  
        <ul class="chat-thread" id="record">
        </ul>
	</div>
    <div class="msg-bottom">
        <input type="hidden" name="producUserID" value=""/>
        <input type="hidden" name="mesReceiverTarget" value=""/>
        <input type="hidden" name="messageCode" value="chatMessage"/>
    	<input type="text" name="content" class="msg-input"/>
        <a class="a_btn btnBg wd_08 ml_10" href="javascript:;" onclick="postMessage()">发送</a>
    </div>	
  </form>	
</div>
<script type="text/javascript" src="static/js/jquery.min.js"></script>
<script type="text/javascript" src="static/js/mspush.js"></script>
<script type="text/javascript" src="static/js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript">
    function msgPanelClose(){
    	$("#convo").find("ul.chat-thread").empty();
    }
    
    function joinChat (){
    	if ($("#user").val().trim() != '') {
			pollForMessages();
		};
    	
		if ($("#receiver").val().trim() != '') {
			$("#postMessageForm input[name=producUserID]").val($("#user").val().trim());
			$("#postMessageForm input[name=mesReceiverTarget]").val($("#receiver").val().trim());
			$("#msg-title").text("与"+$("#receiver").val()+"正在进行聊天 ");
			$("#msg-panel").show();
		}
		
	   function pollForMessages() {
			var form = $("#joinChatForm");
			form.hide();
			$.ajax({url : form.attr("action"),dataType : "json", type : "POST", data : form.serialize(),cache: false,
				success : function(messages) {
					if(messages.operateCode=='000000'){
						$("#record").append("<li class='reception'>"+messages.content
				    			+"<p class=\"time\">"+messages.operateTime+"</p></li>");
					};
					
				},
				error : function(xhr) {
					if (xhr.statusText != "abort" && xhr.status != 503) {
						console.error("无法检索聊天信息. 结束.");
					}
				},
				complete : function (xhr, ts) {
					if(xhr.status == 200 && xhr.statusText == "OK"){
						pollForMessages () ; 
					};
				}
			});
			
	    };
       return false ;
    }
    
    function postMessage (){
    	if ($("#postMessageForm input[name=content]").val().trim() != '') {
			var form = $("#postMessageForm");
			$.ajax({url : form.attr("action"),dataType : "json", type : "POST",
			  data : form.serialize(),
			    success : function (messages) {
			    	$("#record").find("li.send").last().find(".time").text(messages.operateTime);
			    },
				error : function(xhr) {
					console.error("系统错误  message: status=" + xhr.status + ", statusText=" + xhr.statusText);
				}
			});
			$("#record").append("<li class='send'>"+$("#postMessageForm input[name=content]").val()
	    			+"<p class=\"time\"></p></li>");
	    	$("#postMessageForm input[name=content]").val("");
		}
    	
       return false;
    };
</script>
</body>
</html>