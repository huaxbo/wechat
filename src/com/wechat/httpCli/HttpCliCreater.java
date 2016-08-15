package com.wechat.httpCli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpCliCreater {

	private static HttpClientBuilder hcBuilder = HttpClientBuilder.create();
	private static String encoding_def = "UTF-8";
		
	/**
	 * request through get
	 * @param url
	 * @param settings。可以为空。非空时：settings[0]，表示编码
	 * @return
	 * @throws Exception
	 */
	public static String requestGet(String url,String ...settings) throws Exception{
		//创建连接
		CloseableHttpClient chc =  hcBuilder.build();
		HttpGet hg = new HttpGet(url);
		String encoding = encoding_def;
		if(settings != null && settings.length > 0){
			encoding = settings[0];
		}
		hg.setHeader("Content-type","charset=" + encoding);		
		//执行请求
		HttpResponse hr = chc.execute(hg);
		//返回结果
		HttpEntity he = hr.getEntity();
		String rlt =  EntityUtils.toString(he,encoding);	
		//关闭连接		
		chc.close();
		
		//返回结果
		return rlt;
	}
	
	
	/**
	 * request through post
	 * @param url
	 * @param settings。可以为空。非空时：settings[0]：表示参数hashMap、settings[1]：编码
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String requestPost(String url,Object ...settings) throws Exception{
		//创建连接
		CloseableHttpClient chc =  hcBuilder.build();
		HttpPost hp = new HttpPost(url);
		hp.setConfig(RequestConfig.DEFAULT);
		hp.addHeader("Content-Type", "text/xml");  
		//设定编码
		String encoding = encoding_def;
		//参数构建
		if(settings != null && settings.length > 0){
			HashMap<String,String> pm = (HashMap<String,String>)settings[0];
			int pmsize = pm.size();
			if(pmsize>0){
				List<NameValuePair> pms = new ArrayList<NameValuePair>(pmsize);
				Iterator<Entry<String,String>> it = pm.entrySet().iterator();
				while(it.hasNext()){
					Entry<String,String> et = it.next();					
					pms.add(new BasicNameValuePair(et.getKey(), et.getValue()));					
				}
				
				if(settings.length > 1){
					encoding = (String)settings[1];
				}
				//绑定参数				
				hp.setEntity(new UrlEncodedFormEntity(pms, encoding));
			}
		}
		//执行请求
		HttpResponse hr = chc.execute(hp);
		//返回结果
		HttpEntity he = hr.getEntity();
		String rlt = EntityUtils.toString(he,encoding);		
		//关闭连接		
		chc.close();
		
		return rlt;
	}	
	
	/**
	 * request through post
	 * @param url
	 * @param settings。可以为空。非空时：settings[0]：表示参数json字符串、settings[1]：编码
	 * @return
	 * @throws Exception
	 */
	public static String requestJsonPost(String url,Object ...settings) throws Exception{
		//创建连接
		SSLContextBuilder builder = new SSLContextBuilder();  
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());  
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
                builder.build(),SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
        CloseableHttpClient chc = HttpClients.custom().setSSLSocketFactory(  
                sslsf).build();  
		HttpPost hp = new HttpPost(url);
		hp.setConfig(RequestConfig.DEFAULT);
		//设定编码
		String encoding = encoding_def;
		//参数构建
		if(settings != null && settings.length > 0){
			if(settings.length > 1){
				encoding = (String)settings[1];
			}
			//绑定参数				
			hp.setEntity(new StringEntity((String)settings[0], encoding));
		}
		//执行请求
		HttpResponse hr = chc.execute(hp);
		//返回结果
		HttpEntity he = hr.getEntity();
		String rlt = EntityUtils.toString(he,encoding);		
		//关闭连接		
		chc.close();
		
		return rlt;
	}
	
}
