package com.wechat.exchange;

import com.wechat.httpCli.HttpCliCreater;

import net.sf.json.JSONObject;

public class WechatAccess {
	//΢�ŷ���|���ĺ�appId
	private final static String appId = "wx8f9c11cb6ad7ea54";
	//΢�ŷ���|���ĺ�appSecret
	private final static String appSecret = "e6388bca0815249d7e03fb571f7b9232";
	//΢�Žӿڵ�ַ
	private final static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token"
			+ "?grant_type=client_credential"
			+ "&appid=" + appId 
			+ "&secret=" + appSecret;
	//΢�Žӿڷ���accessToken
	private static JSONObject accessToken;
	private static long accessTime = System.currentTimeMillis();//��ȡtokenʱ��
	
	/**
	 * ����ˢ�·���
	 */
	public static void startRefresh(){
		new RefreshAccessToken().start();
	}	
	
	/**
	 * ��ѯ΢�Žӿڵ���ƾ��
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
	 * ���΢�Žӿڵ���ƾ��ʣ����Чʱ��
	 * ��λ������
	 * @return
	 * true:���ڡ�false��������
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
	 * ��ȡ΢�Žӿڵ���ƾ��
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
	 * ��ʱˢ��΢�Žӿڵ���ƾ��
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
