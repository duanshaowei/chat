package com.dewei.chat.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dewei.chat.constants.MsPushConstants.SysRetCodeConstants;
import com.dewei.chat.service.chat.ChatMessageService;
import com.dewei.chat.utilities.MessageResponstUtil;
import com.dewei.chat.vo.NetMessage;
import com.dewei.chat.vo.NetMessageHeader;
import com.dewei.chat.vo.message.NetChatMessage;

/**
 * 
 * @author duansw
 */
@Controller
@RequestMapping("/chat")
public class ChatMessageController {
	@Autowired
	private ChatMessageService chatMessageService;

	@RequestMapping("/submitMes")
	public @ResponseBody Callable<Map<String, Object>> submitMes(
			final @Valid NetMessageHeader header, final BindingResult result,
			final NetChatMessage body, final HttpServletRequest req) {
		return new Callable<Map<String, Object>>() {
			@Override
			public Map<String, Object> call() throws Exception {
				Map<String, Object> response = new HashMap<String, Object>();
				NetMessage mes = new NetMessage();
				mes.setMessageHeader(header);
				mes.setMessageBody(body);

				if (result.hasErrors()) {
					Map<String, Object> check_failure = MessageResponstUtil
							.responstStatus(
									SysRetCodeConstants.REQUEST_CHECK_FAILURE
											.getCode(), result.getFieldError());
					response.putAll(check_failure);
					return response;
				}

				try {

					chatMessageService.addChatMessage(mes);
					Map<String, Object> success = MessageResponstUtil
							.responstStatus(
									SysRetCodeConstants.SUCCESS.getCode(),
									SysRetCodeConstants.SUCCESS.getMessage());
					response.put("operateTime", body.getOperateTime());
					response.putAll(success);
				} catch (Exception e) {
					return MessageResponstUtil.responstStatus(
							SysRetCodeConstants.SYSTEM_ERROR.getCode(),
							SysRetCodeConstants.SYSTEM_ERROR.getMessage());
				}

				return response;
			}

		};
	}

}
