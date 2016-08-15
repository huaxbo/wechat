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
	 * @param settings������Ϊ�ա��ǿ�ʱ��settings[0]����ʾ����
	 * @return
	 * @throws Exception
	 */
	public static String requestGet(String url,String ...settings) throws Exception{
		//��������
		CloseableHttpClient chc =  hcBuilder.build();
		HttpGet hg = new HttpGet(url);
		String encoding = encoding_def;
		if(settings != null && settings.length > 0){
			encoding = settings[0];
		}
		hg.setHeader("Content-type","charset=" + encoding);		
		//ִ������
		HttpResponse hr = chc.execute(hg);
		//���ؽ��
		HttpEntity he = hr.getEntity();
		String rlt =  EntityUtils.toString(he,encoding);	
		//�ر�����		
		chc.close();
		
		//���ؽ��
		return rlt;
	}
	
	
	/**
	 * request through post
	 * @param url
	 * @param settings������Ϊ�ա��ǿ�ʱ��settings[0]����ʾ����hashMap��settings[1]������
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static String requestPost(String url,Object ...settings) throws Exception{
		//��������
		CloseableHttpClient chc =  hcBuilder.build();
		HttpPost hp = new HttpPost(url);
		hp.setConfig(RequestConfig.DEFAULT);
		hp.addHeader("Content-Type", "text/xml");  
		//�趨����
		String encoding = encoding_def;
		//��������
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
				//�󶨲���				
				hp.setEntity(new UrlEncodedFormEntity(pms, encoding));
			}
		}
		//ִ������
		HttpResponse hr = chc.execute(hp);
		//���ؽ��
		HttpEntity he = hr.getEntity();
		String rlt = EntityUtils.toString(he,encoding);		
		//�ر�����		
		chc.close();
		
		return rlt;
	}	
	
	/**
	 * request through post
	 * @param url
	 * @param settings������Ϊ�ա��ǿ�ʱ��settings[0]����ʾ����json�ַ�����settings[1]������
	 * @return
	 * @throws Exception
	 */
	public static String requestJsonPost(String url,Object ...settings) throws Exception{
		//��������
		SSLContextBuilder builder = new SSLContextBuilder();  
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());  
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
                builder.build(),SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
        CloseableHttpClient chc = HttpClients.custom().setSSLSocketFactory(  
                sslsf).build();  
		HttpPost hp = new HttpPost(url);
		hp.setConfig(RequestConfig.DEFAULT);
		//�趨����
		String encoding = encoding_def;
		//��������
		if(settings != null && settings.length > 0){
			if(settings.length > 1){
				encoding = (String)settings[1];
			}
			//�󶨲���				
			hp.setEntity(new StringEntity((String)settings[0], encoding));
		}
		//ִ������
		HttpResponse hr = chc.execute(hp);
		//���ؽ��
		HttpEntity he = hr.getEntity();
		String rlt = EntityUtils.toString(he,encoding);		
		//�ر�����		
		chc.close();
		
		return rlt;
	}
	
}
