package com.wechat.exchange;

import com.wechat.httpCli.HttpCliCreater;

import net.sf.json.JSONObject;

public class WechatAccess {
	//微信服务|订阅号appId
	private final static String appId = "wx8f9c11cb6ad7ea54";
	//微信服务|订阅号appSecret
	private final static String appSecret = "e6388bca0815249d7e03fb571f7b9232";
	//微信接口地址
	private final static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token"
			+ "?grant_type=client_credential"
			+ "&appid=" + appId 
			+ "&secret=" + appSecret;
	//微信接口访问accessToken
	private static JSONObject accessToken;
	private static long accessTime = System.currentTimeMillis();//获取token时间
	
	/**
	 * 启动刷新服务
	 */
	public static void startRefresh(){
		new RefreshAccessToken().start();
	}	
	
	/**
	 * 查询微信接口调用凭据
	 * @return
	 */
	public static String getAccessToken(){
		if(accessToken == null){
			fetchAccessToken();
		}		
		if(accessToken != null){
			
			return accessToken.getString("access_token");
		}
		
		return null;
	}	
	
	/**
	 * 检查微信接口调用凭据剩余有效时长
	 * 单位：分钟
	 * @return
	 * true:过期、false：不过期
	 */
	private static boolean checkExpiresIn(){
		if(accessToken != null){
			long curr = System.currentTimeMillis();
			
			return (curr - accessTime)/(60*1000)
					>= (accessToken.getInt("expires_in")/60 - 5);
		}
		
		return true;
	}
	/**
	 * 获取微信接口调用凭据
	 */
	private synchronized static void fetchAccessToken(){
		try {
			accessTime = System.currentTimeMillis();
			String rlt = HttpCliCreater.requestGet(accessTokenUrl);
			if(rlt != null){
				accessToken = JSONObject.fromObject(rlt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @author huaxb
	 * 定时刷新微信接口调用凭据
	 */
	static class RefreshAccessToken extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try {
					sleep(60 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{}
				
				if(checkExpiresIn()){
					fetchAccessToken();
				}
			}
		}
		
	}
	
}
