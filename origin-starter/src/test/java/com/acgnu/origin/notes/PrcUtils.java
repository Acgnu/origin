package com.acgnu.origin.notes;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;


public class PrcUtils {
	private static class MyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class MyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * 
	 * HTTP协议GET请求方法
	 */
	public static String doHttpsGet(String url) {
		StringBuilder sb = new StringBuilder();
		URL urls;
		HttpsURLConnection uc = null;
		BufferedReader in;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new MyTrustManager() },
					new java.security.SecureRandom());
			urls = new URL(url);
			uc = (HttpsURLConnection) urls.openConnection();
			uc.setSSLSocketFactory(sc.getSocketFactory());
			uc.setHostnameVerifier(new MyHostnameVerifier());
			uc.setConnectTimeout(1000); 
			//uc.setReadTimeout(1000);
			uc.setRequestMethod("GET");
			uc.connect();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8));
			String readLine;
			while ((readLine = in.readLine()) != null) {
				sb.append(readLine);
			}
			in.close();
			uc.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * HTTP协议POST请求方法
	 */
	public static String doHttpsPost(String url, String params) {
		StringBuffer sb = new StringBuffer();
		URL urls;
		HttpsURLConnection uc = null;
		BufferedReader in = null;
		doConnect(url, params, sb, uc);
		return sb.toString();
	}

	private static void doConnect(String url, String params, StringBuffer sb, HttpsURLConnection uc) {
		URL urls;
		BufferedReader in;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new MyTrustManager() },
					new java.security.SecureRandom());
			urls = new URL(url);
			uc = (HttpsURLConnection) urls.openConnection();
			uc.setSSLSocketFactory(sc.getSocketFactory());
			uc.setHostnameVerifier(new MyHostnameVerifier());
			uc.setRequestMethod("POST");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			uc.connect();
			DataOutputStream out = new DataOutputStream(uc.getOutputStream());
			out.write(params.getBytes(StandardCharsets.UTF_8));
			out.flush();
			out.close();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8));
			String readLine;
			while ((readLine = in.readLine()) != null) {
				sb.append(readLine);
			}
			in.close();
			uc.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
	}

	/**
	 * 
	 * HTTP协议POST请求方法
	 */
	public static String doHttpsPost(String url,
									 TreeMap<String, String> paramsMap) {
		String params = null;
		if (null != paramsMap) {
			params = concatQueryString(paramsMap);
		}

		StringBuffer sb = new StringBuffer();
		URL urls;
		HttpsURLConnection uc = null;
		BufferedReader in = null;
		doConnect(url, params, sb, null);
		return sb.toString();
	}

	/**
	 * 
	 * HTTP协议POST请求添加参数的封装方法
	 */
	public static String concatQueryString(TreeMap<String, String> paramsMap) {
		StringBuilder param = new StringBuilder();
		for (Map.Entry<String, String> e : paramsMap.entrySet()) {
			param.append("&").append(e.getKey()).append("=")
					.append(e.getValue());
		}
		return param.toString().substring(1);
	}
	

	/**
	 * 
	 * HTTP协议POST下载文件
	 */
	public static void doHttpsDownload(String url, String fileName, String savePath, String params) {
		URL urls;
		HttpsURLConnection uc = null;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new MyTrustManager() }, new java.security.SecureRandom());
			urls = new URL(url);
			uc = (HttpsURLConnection) urls.openConnection();
			uc.setSSLSocketFactory(sc.getSocketFactory());
			uc.setHostnameVerifier(new MyHostnameVerifier());
			uc.setRequestMethod("GET");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			uc.connect();
			writeStringArgs(params, uc);
			//得到输入流
	        InputStream inputStream = uc.getInputStream();    
	        //获取自己数组  
	        byte[] getData = readInputStream(inputStream);
	        
	        //文件保存位置  
	        File saveDir = new File(savePath);  
	        if(!saveDir.exists()){  
	            saveDir.mkdir();  
	        }  
	        File file = new File(saveDir+File.separator+fileName);      
	        FileOutputStream fos = new FileOutputStream(file);  
	        fos.write(getData);
			fos.close();
			uc.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
	}

	private static void writeStringArgs(String params, HttpsURLConnection uc) throws IOException {
		if(null != params && !"".equals(params)){
			PrintWriter out;
			out = new PrintWriter(new OutputStreamWriter(uc.getOutputStream(), StandardCharsets.UTF_8));
			out.print(params);
			out.flush();
		}
	}

	/**
	 *
	 * HTTP协议POST下载文件
	 */
	public static byte[] downToByteArray(String url, String params, byte[] dataArr) {
		URL urls;
		HttpsURLConnection uc = null;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new MyTrustManager() }, new java.security.SecureRandom());
			urls = new URL(url);
			uc = (HttpsURLConnection) urls.openConnection();
			uc.setSSLSocketFactory(sc.getSocketFactory());
			uc.setHostnameVerifier(new MyHostnameVerifier());
			uc.setRequestMethod("GET");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			uc.connect();
			writeStringArgs(params, uc);
			//得到输入流
			InputStream inputStream = uc.getInputStream();
			//获取自己数组
			dataArr = readInputStream(inputStream);

			return dataArr;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
		return null;
	}

	private static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];    
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }

	/**
	 *
	 * HTTP协议GET请求方法
	 */
	public static String doHttpGet(String url) {
		StringBuilder sb = new StringBuilder();
		URL urls;
		HttpURLConnection uc = null;
		BufferedReader in;
		try {
			urls = new URL(url);
			uc = (HttpURLConnection) urls.openConnection();
			uc.setRequestMethod("GET");
			uc.connect();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
                    StandardCharsets.UTF_8));
			String readLine;
			while ((readLine = in.readLine()) != null) {
				sb.append(readLine);
			}
			in.close();
			uc.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
		return sb.toString();
	}
}
