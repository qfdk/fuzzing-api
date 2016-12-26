package esir3.vv;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

/**
 * Created by qfdk on 2016/11/16.
 */
public class Tools {

	public enum type {Integer,String};
	public enum OperationType {POST, GET, PUT, DELETE};
	static Logger logger = LoggerFactory.getLogger(Tools.class);

	// HTTP GET request
	public static String sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		logger.debug("Sending 'GET' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);

		//        BufferedReader in = new BufferedReader(
		//                new InputStreamReader(con.getInputStream()));
		//        String inputLine;
		//        StringBuffer response = new StringBuffer();
		//        while ((inputLine = in.readLine()) != null) {
		//            response.append(inputLine);
		//        }
		//        in.close();
		//print result
		//       response.toString()
		return String.valueOf(responseCode);
	}

	public static String randomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(62);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}
	/*
	 * strategy
	 */
	public static String generateTestData(){
		return randomString(10);
	}



	public static void saveConf(Map<String, String> map, String file_name) {
		Properties prop = new Properties();
		for (String s : map.keySet()) {
			prop.setProperty(s, map.get(s));
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file_name);
		} catch (FileNotFoundException e) {
			logger.error("saveConf FileNotFoundException",e);
		}
		try {
			prop.storeToXML(fos, file_name);
		} catch (IOException e) {
			logger.error("saveConf IOException",e);

		}
		try {
			fos.close();
		} catch (IOException e) {
			logger.error("saveConf IOException",e);
		}
	}

	/**
	 * get conf from conf xml
	 */

	public static Properties readConf(String file_name) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file_name);
		} catch (FileNotFoundException e) {
			logger.error("readConf FileNotFoundException",e);
		}
		Properties prop = new Properties();
		try {
			prop.loadFromXML(fis);
		} catch (IOException e) {
			logger.error("readConf IOException",e);
		}
		try {
			fis.close();
		} catch (IOException e) {
			logger.error("readConf IOException",e);
		}
		return prop;
	}

	public static String sendPost(String url,String parameters){
		
		String result = "";
		
		try {
			HttpURLConnection httpCon = (HttpURLConnection) new URL(url).openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Content-Type", "application/json; charset=utf8");
			httpCon.setRequestMethod("POST");
			
			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			
			out.write(parameters);
			out.flush();
			out.close();
			
			result = String.valueOf(httpCon.getResponseCode());
			
			logger.debug("post response "+ httpCon.getResponseCode()+ " "+ url);
//			logger.debug("post response message"+httpCon.getResponseMessage());
		} catch (MalformedURLException e) {
			logger.error("SendPost MalformedURLException",e);
		} catch (ProtocolException  e) {
			logger.error("SendPost ProtocolException",e);
		} catch (IOException e) {
			logger.error("SendPost IOException",e);
		}		
		return String.valueOf(result);
	}
}
