package com.renms.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * @ClassName: HttpClientUtil.java
 * @Description: http请求工具类
 * @author renms
 * @date 2015年9月25日 上午11:48:50 
 * @version 1.0
 */
public class HttpClientUtil {
	protected static Logger log = Logger.getLogger(HttpClientUtil.class);
	public static final Integer CONNECTION_TIMEOUT = 10000; //ms
	public static final Integer SOCKET_TIMEOUT = 3000; //ms
	public static final String CHARSET = "UTF-8"; //ms
	
	private static CloseableHttpClient httpClient = null;

	static {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}

	public static String doGet(String url, Map<String, String> params) {
		return doGet(url, params, CHARSET);
	}

	public static String doPost(String url, Map<String, Object> params) {
		return doPost(url, null, params, CHARSET);
	}
	
	public static String doPost(String url, Map<String, String> headerParams, Map<String, Object> params) {
		return doPost(url, headerParams, params, CHARSET);
	}

	public static String doGet(String url, Map<String, String> params, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient Error, error status code: " + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, charset); //result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	public static String doPost(String url, Map<String, String> headerParams, Map<String, Object> params, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					Object value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value.toString()));
					}
				}
			}
			HttpPost httpPost = new HttpPost(url);
			if (pairs != null && pairs.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
			}
			if(headerParams != null) {
				setHeaderParams(httpPost, headerParams);
			}
			
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient Error, error status code:" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, charset); //result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	public static void main(String[] args) {
		String getData = doGet("http://wwww.baidu.com", null);
		System.out.println(getData);
		System.out.println("----------------------分割线-----------------------");
		String postData = doPost("http://www.oschina.net/", null);
		System.out.println(postData);
	}
	
	/**
	 * 设置请求的header头信息
	 * 
	 * @param httpRequest
	 * @param headerParams
	 */
	private static void setHeaderParams(HttpRequestBase httpRequest, Map<String, String> headerParams) {
		if (headerParams != null && !headerParams.isEmpty()) {
			Set<Entry<String, String>> entrySet = headerParams.entrySet();
			for (Entry<String, String> entry : entrySet) {
				httpRequest.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}
}
