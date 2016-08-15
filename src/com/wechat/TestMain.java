package com.wechat;

import com.wechat.exchange.WechatAccess;
import com.wechat.exchange.WechatMess;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestMain {

	private static String token 
	= "61IU0PD_c2mA0SofX6galssTvcj9iUFA7yj5LBnl6WkIq_x-Qg44QVVPLuXJD2UyF_vM_mJ38kndcld7uKZJfAgsFqlduR96SKuSxge5FXIYd6WFRsVg4y4j8rC_lO8vKZKaAIAUJU";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*String accessToken = WechatAccess.getAccessToken();
		System.out.println("accessToken::" + accessToken);*/
		
//		WechatAccess.startRefresh();
		
//		WechatMess.sendMassMess("微信订阅号发布信息测试！",token);
		
//		WechatMess.getTags(token);
		
//		WechatMess.getUsers(token);
		
//		WechatMess.getAutoBackCfg(token);
		
//		WechatMess.getModules(token);
		
		/*WechatMess.sendTemplateMess("uat0y54ZurZDtUVBI0-sEGHto142LF0CgJqPWyCryMs", 
				"oC69Iw_au6ND9mzV4R5dI2iEq7lg",
				"荆州微信信息发布测试信息12343hello！荆州微信信息发布测试信息12343hello！荆州微信信息发布测试信息12343hello！荆州微信信息发布测试信息12343hello！荆州微信信息发布测试信息12343hello！荆州微信信息发布测试信息12343hello！"
				,token);*/
		
		/*WechatMess.sendTemplateMessAll("uat0y54ZurZDtUVBI0-sEGHto142LF0CgJqPWyCryMs",
		"荆州微信模板方式发送消息：hello every one，I can send notify message through the wechat,"
		+ "congratulations！"
		,token);*/

		
//		WechatMess.setUserRemark("oC69Iw_au6ND9mzV4R5dI2iEq7lg", "巫山威视",token);
		
		WechatMess.sendServiceMess("oC69Iw_au6ND9mzV4R5dI2iEq7lg", 
					"荆州微信信息发布测试信息12343hello！"
					,token);
		
//		WechatMess.sendServiceMessAll("荆州微信信息发布测试信息12343hello！",token);
		
		/*String jstr = "{\"total\":1,"
				+ "\"count\":1,"
				+ "\"data\":{\"openid\":[\"oC69Iw_au6ND9mzV4R5dI2iEq7lg\",\"oC69Iw_au6ND9mzV4R5dI2iEq7l2\"]},"
				+ "\"next_openid\":\"oC69Iw_au6ND9mzV4R5dI2iEq7lg\"}";
		JSONObject jobj = JSONObject.fromObject(jstr);
		JSONObject jdata = jobj.getJSONObject("data");
		if(!jdata.isNullObject()){
			if(jdata.containsKey("openid")){
				JSONArray jarr = jdata.getJSONArray("openid");
				for(int i=0;i<jarr.size();i++){
					System.out.println(jarr.getString(i));			
				}
			}
		}*/
		
		
	}
}
