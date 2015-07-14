package com.dewei.chat.utilities;
import java.util.HashMap;
import java.util.Map;
public class MessageResponstUtil {

	public static Map<String ,Object> responstStatus(String operateCode, Object memo) {
		Map<String ,Object> responst = new HashMap<String, Object>();
		responst.put("operateCode", operateCode);
		responst.put("memo", memo);
		return responst;
	}
}
