package com.dewei.chat.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.dewei.chat.constants.MsPushConstants.SysRetCodeConstants;
import com.dewei.chat.service.VisitMessageService;
import com.dewei.chat.utilities.MessageResponstUtil;
import com.dewei.chat.vo.Visitor;
/**
 * 
 * @author duansw
 */
@Controller
public class VisitMessageController {
	@Autowired
	private VisitMessageService messageService;
	
	@RequestMapping("/visitMes")
	public @ResponseBody DeferredResult<Map<String ,Object>> visitMes (HttpServletRequest req ,final Visitor visitor) {
		if (StringUtils.isEmpty(visitor.getRequestId())) {
			Map<String ,Object> illegal = MessageResponstUtil.responstStatus(SysRetCodeConstants.REQUEST_FORMAT_ILLEGAL.getCode(), SysRetCodeConstants.REQUEST_FORMAT_ILLEGAL.getMessage());
			visitor.getDeferredResult().setResult(illegal);
		}
		
		try {
			visitor.setSessionId(req.getSession().getId());
			messageService.addAsyncResult(visitor);
		} catch (Exception e) {
			Map<String ,Object> error = MessageResponstUtil.responstStatus(
					SysRetCodeConstants.SYSTEM_ERROR.getCode(), SysRetCodeConstants.SYSTEM_ERROR.getMessage()+";"+e.getMessage());
			visitor.getDeferredResult().setResult(error);
		}
		
		visitor.getDeferredResult().onTimeout(new Runnable() {
			@Override
			public void run() {
				Map<String ,Object> timeout = MessageResponstUtil.responstStatus(
						SysRetCodeConstants.MESSAGE_CONNECTION_TIMEOUT.getCode(), SysRetCodeConstants.MESSAGE_CONNECTION_TIMEOUT.getMessage());
				visitor.getDeferredResult().setResult(timeout);
			}
		});
		
		visitor.getDeferredResult().onCompletion(new Runnable() {
			@Override
			public void run() {
				messageService.removeAsyncResult(visitor);
			}
		});
		
		return visitor.getDeferredResult() ;
	}

}
