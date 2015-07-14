/**
 * @author duansw
 * @param w
 */
(function(w){
	if(!(w.$) || !(w.jQuery) ){
		alert(" 使用mspush.js插件前需要jquery.js! ");
		return ;
	};

	var console = (w.console && w.console.log) ? true : false;
	
	var MsCentre = function (contextPath) {
		this.contextPath = contextPath ;
	};
	
	MsCentre.fn = MsCentre.prototype = {
			constructor : 	MsCentre ,
			/**
			 * 订阅者取订阅消息
			 */
			fetchForMessages : function (argument,callback) {
				var data = argument || {};
				if(data.userName || data.userName == ''){
					alert("订阅消息需要用户名!");
				}
				
				$.ajax({url : this.contextPath+"/visitMes",dataType : "json", type : "POST", data : data,cache: false,
					success : function(messages) {
						if ($.isFunction(callback))callback(messages);
					},
					error : function(xhr) {
						if (xhr.statusText != "abort" && xhr.status != 503) {
							if(console) console.error("无法连接消息中心. 结束.");
						}
					},
					complete : function (xhr, ts) {
						if(xhr.status == 200 && xhr.statusText == "OK"){
							pollForMessages (data) ; 
						};
					}
				});
			},
			/**
			 * 推送到消息中心去匹配订阅者
			 */
			pushForMessages : function (argument,callback){
				var data = argument || {};
				$.ajax({url : this.contextPath+"/chat/submitMes",dataType : "json", type : "POST", data : data,cache: false,
					success : function(messages) {
						if ($.isFunction(callback))callback(messages);
					},
					error : function(xhr) {
						if (xhr.statusText != "abort" && xhr.status != 503) {
							if(console) console.error("发送消息失败. 结束.");
						}
					}
				});
			}
	};

	
	w.MsCentre = MsCentre;
})(window);