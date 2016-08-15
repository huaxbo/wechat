package com.wechat.exchange;

import java.util.HashMap;

import com.wechat.httpCli.HttpCliCreater;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WechatMess {

	/**
	 * 标签方式，发送群发消息
	 */
	public static void sendTagMassMess(String mess,String token){
		JSONObject jobj = new JSONObject();
		//filter
		JSONObject jfilter = new JSONObject();
		jfilter.put("is_to_all", true);
		jobj.put("filter", jfilter.toString());
		//text
		JSONObject jtext = new JSONObject();
		jtext.put("content", mess);
		jobj.put("text", jtext.toString());
		//msgtype
		jobj.put("msgtype", "text");
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("body", jobj.toString());
		
		Object[] settings = new Object[2];
		settings[0] = hm;
		settings[1] = "utf-8";
		String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=";
		url += token;
		try {
			String rlt = HttpCliCreater.requestPost(url, settings);
			System.out.println(rlt);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
	}
	
	/**
	 * 获取标签
	 */
	public static void getTags(String token){
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=";
		url += token;
		try {
			String rlt = HttpCliCreater.requestPost(url, new Object[0]);
			System.out.println(rlt);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
		
	}
	
	/**
	 * 获取用户列表
	 */
	public static String getUsers(String token){
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";
		url += token;
		try {
			String rlt = HttpCliCreater.requestGet(url);
			
			return rlt;			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
		
		return null;
	}
	
	/**
	 * 获取自动回复配置
	 */
	public static void getAutoBackCfg(String token){
		String url = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=";
		url += token;
		try {
			String rlt = HttpCliCreater.requestGet(url);
			System.out.println(rlt);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
	}
	
	/**
	 * 获取模板列表
	 */
	public static void getModules(String token){
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=";
		url += token;
		try {
			String rlt = HttpCliCreater.requestGet(url);
			System.out.println(rlt);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
		
	}
	
	/**
	 * 发送模板信息
	 * @param moduleId
	 */
	public static void sendTemplateMess(String template_id,String openId,
			String mess,String token){
		JSONObject jobj = new JSONObject();
		jobj.put("touser",openId);
		jobj.put("template_id", template_id);
		
		//data
		JSONObject jdata = new JSONObject();
		JSONObject jmess = new JSONObject();
		jmess.put("value", mess);
		jdata.put("mess", jmess.toString());
		jobj.put("data", jdata.toString());
		
		Object[] settings = new Object[2];
		settings[0] = jobj.toString();
		settings[1] = "utf-8";
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
		url += token;
		try {
			String rlt = HttpCliCreater.requestJsonPost(url, settings);
			System.out.println(rlt);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
	}
	/**
	 * 发送客服消息至全部人员
	 * @param cnt
	 */
	public static void sendTemplateMessAll(String templateId,String cnt,String token){
		String users = getUsers(token);
		if(users != null){
			JSONObject jobj = JSONObject.fromObject(users);
			JSONObject jdata = jobj.getJSONObject("data");
			if(!jdata.isNullObject()){
				if(jdata.containsKey("openid")){
					JSONArray jarr = jdata.getJSONArray("openid");
					for(int i=0;i<jarr.size();i++){
						sendTemplateMess(templateId,jarr.getString(i),cnt,token);
					}
				}
			}
		}
		
	}
	/**
	 * 设置用户备注
	 * @param openId
	 * @param remark
	 */
	public static void setUserRemark(String openId,String remark,String token){
		JSONObject jobj = new JSONObject();
		jobj.put("openid",openId);
		jobj.put("remark", remark);
		
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("body", jobj.toString());
		
		Object[] settings = new Object[2];
		settings[0] = hm;
		settings[1] = "utf-8";
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=";
		url += token;
		try {
			String rlt = HttpCliCreater.requestPost(url, settings);
			System.out.println(rlt);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
		
	}
	
	/**
	 * 发送客服消息
	 * @param openId
	 * @param cnt
	 */
	public static void sendServiceMess(String openId,String cnt,String token){
		JSONObject jobj = new JSONObject();
		jobj.put("touser",openId);
		jobj.put("msgtype", "text");
		
		JSONObject jtext = new JSONObject();
		jtext.put("content", cnt);
		jobj.put("text", jtext.toString());
		
		Object[] settings = new Object[2];
		settings[0] = jobj.toString();
		settings[1] = "utf-8";
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
		url += token;
		try {
			String rlt = HttpCliCreater.requestJsonPost(url, settings);
			System.out.println(rlt);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
	}
	
	/**
	 * 发送客服消息至全部人员
	 * @param cnt
	 */
	public static void sendServiceMessAll(String cnt,String token){
		String users = getUsers(token);
		if(users != null){
			JSONObject jobj = JSONObject.fromObject(users);
			JSONObject jdata = jobj.getJSONObject("data");
			if(!jdata.isNullObject()){
				if(jdata.containsKey("openid")){
					JSONArray jarr = jdata.getJSONArray("openid");
					for(int i=0;i<jarr.size();i++){
						sendServiceMess(jarr.getString(i),cnt,token);
					}
				}
			}
		}
		
	}
}
