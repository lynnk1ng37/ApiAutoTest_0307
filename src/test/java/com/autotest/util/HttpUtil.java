package com.autotest.util;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {
	//创建日志记录器logger
	public static Logger logger = Logger.getLogger(HttpUtil.class);
	//创建一个保存鉴权信息的集合
	public static Map<String,String> cookies = new HashMap<String, String>();
	
    /**
     * 通用Post请求
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String,String> params){
    	
        //创建客户端、http响应、响应结果对象
        HttpClient client = HttpClients.createDefault();
        HttpResponse httpResponse;
        String result = null;
        //根据url创建一个post请求
        logger.info("接口请求url【"+url+"】");
        HttpPost httpPost = new HttpPost(url);
        //创建一个保存请求参数的集合
        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        //判空
        if(!(params.size()<=0)) {
            //从Map中将测试数据遍历并保存到请求参数集合中
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue();
                parameters.add(new BasicNameValuePair(key, value));
            }
        }
        try {
            //以表单形式提交请求Entity
            httpPost.setEntity(new UrlEncodedFormEntity(parameters,"utf-8"));
            /*
             * 在访问请求前，因为有一些接口需要用到鉴权信息
             * 因此在执行请求前先把鉴权信息加进去
             */
            addCookieToRequestHeader(httpPost);
            //发起请求，获取响应信息
            httpResponse = client.execute(httpPost);
            /*
             * 请求执行完以后，如果是登录接口并且登录成功，会返回一个响应头：
             * 		Set-Cookie : JSESSIONID=F457D8640FBA92EF96B...
             * 那么只要判断响应头中存在这样一组信息，则保存到集合中
             */
            storeCookieFromResponseHeader(httpResponse);
            //获取响应码和响应结果
            int code = httpResponse.getStatusLine().getStatusCode();
            result = EntityUtils.toString(httpResponse.getEntity());
            logger.info("接口响应报文：code【"+code+"】，result【"+result+"】");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        //返回响应信息给调用者
        return result;
    }

    //将cookie信息添加到请求头中
    private static void addCookieToRequestHeader(HttpRequest httpRequest) {
    	//从保存鉴权信息的集合中取出JSESSIONID
    	String jSessionId = cookies.get("JSESSIONID");
    	//判断是否为空
    	if (jSessionId!=null&&jSessionId.trim().length()>0) {
			//将JSESSIONID的值添加到请求头Cookie中
    		httpRequest.addHeader("Cookie", jSessionId);
		}
	}

    //从响应头中保存cookie信息
	private static void storeCookieFromResponseHeader(HttpResponse httpResponse) {
    	//从响应头中取出指定的响应头信息
        Header setCookieheaders = httpResponse.getFirstHeader("Set-Cookie");
        //判空
        if(setCookieheaders!=null){
        	//获取该响应头的值
        	String cookiePairsString = setCookieheaders.getValue();
        	//判空
        	if(cookiePairsString!=null && cookiePairsString.trim().length()>0){
        		//按照cookie分割符;来切割成字符串数组
        		String[] cookiePairs = cookiePairsString.split(";");
        		//遍历数组判断是否包含JSESSIONID
        		for(String cookiePair:cookiePairs){
        			if(cookiePair.contains("JSESSIONID")){
        				//保存到map集合中
        				cookies.put("JSESSIONID", cookiePair);
        			}
        		}
        	}
		}
	}

	/**
     * 通用Get请求
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String,String> params){
        //创建客户端、http响应、响应结果对象
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse;
        String result = null;

        //判空
        if (!(params.size()<=0)){
            //从Map中拿到参数，准备拼接url
            Set<Map.Entry<String,String>> entrySet = params.entrySet();
            //因为第一个参数前面加的是?号，因此定义一个标记记录是第一次取值时加上?，否则加上&
            int mark = 1;
            for (Map.Entry<String,String> entry:entrySet){
                if (mark==1){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    url+="?"+key+"="+value;
                    mark++;
                }else {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    url+="&"+key+"="+value;
                }
            }
        }
        //根据url创建一个get请求
        logger.info("接口请求url【"+url+"】");
        HttpGet httpGet = new HttpGet(url);
        try {
        	//同理post
        	addCookieToRequestHeader(httpGet); 
            //发起请求，获取响应信息
            httpResponse = httpClient.execute(httpGet);
            //同理post
            storeCookieFromResponseHeader(httpResponse);
            //获取响应结果
            int code = httpResponse.getStatusLine().getStatusCode();
            result = EntityUtils.toString(httpResponse.getEntity());
            logger.info("接口响应报文：code【"+code+"】，result【"+result+"】");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 由传进来的url、params、methodType进行调用对应的请求方法
     * @param url
     * @param params
     * @param methodType
     * @return
     */
    public static String doRequest(String url,String methodType,Map<String, String> params){
    	String result;
        //判断请求方式
        if ("post".equals(methodType)){//常量""写在前面可以防止变量出现空指针异常
        	logger.info("接口请求方式【POST】");
            result = HttpUtil.doPost(url,params);
        }
        else{
        	logger.info("接口请求方式【GET】");
            result = HttpUtil.doPost(url,params);
        }
        return result;
    }
}
