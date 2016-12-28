package esir3.vv;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

/**
 *
 * Created by qfdk on 2016/11/16.
 *
 */
public class Tools {

	public enum type {Integer, String};

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
		return String.valueOf(responseCode);
	}



	/**
	 * Save configure file
	 *
	 * @param map      map key-value
	 * @param fileName path
	 */
	public static void saveConf(Map<String, String> map, String fileName) {
		Properties prop = new Properties();
		for (String s : map.keySet()) {
			prop.setProperty(s, map.get(s));
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			logger.error("saveConf FileNotFoundException", e);
		}
		try {
			prop.storeToXML(fos, fileName);
		} catch (IOException e) {
			logger.error("saveConf IOException", e);

		}
		try {
			fos.close();
		} catch (IOException e) {
			logger.error("saveConf IOException", e);
		}
	}

	/**
	 * Read Configuration
	 *
	 * @param fileName conf path
	 * @return Properties
	 */

	public static Properties readConf(String fileName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			logger.error("readConf FileNotFoundException", e);
		}
		Properties prop = new Properties();
		try {
			prop.loadFromXML(fis);
		} catch (IOException e) {
			logger.error("readConf IOException", e);
		}
		try {
			fis.close();
		} catch (IOException e) {
			logger.error("readConf IOException", e);
		}
		return prop;
	}


	/**
	 * sendPOST
	 *
	 * @param url url
	 * @param map map key-value
	 * @return code 200/404 etc...
	 * @throws Exception
	 */
	public static String sendPost(String url, Map<String, String> map) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=utf8");
		StringBuilder sb = new StringBuilder();

		for (String s : map.keySet()) {
			sb.append(s).append("=").append(map.get(s)).append("&");
		}
		String urlParameters = sb.toString().substring(0, sb.length() - 1);
		logger.debug(urlParameters);
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		logger.debug("Sending 'POST' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);
		return String.valueOf(responseCode);
	}

	/**
	 * send Delete request
	 * @param url url
	 * @return responseCode
	 * @throws Exception e
	 */
	public static String sendDel(String url) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(new HttpDelete(url));
		int reponseCode = response.getStatusLine().getStatusCode();
		logger.debug("\nSending 'Delete' request to URL : " + url);
		logger.debug("Response Code : " + reponseCode);
		return String.valueOf(reponseCode);
	}

	/**
	 * send PUT request
	 * @param url url
	 * @param map key value
	 * @return responseCode
	 * @throws Exception e
	 */
	public static String sendPut(String url, Map<String, String> map) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("PUT");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=utf8");

		JSONObject data = new JSONObject(map);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(data.toString());
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		logger.debug("Sending 'PUT' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);
		return String.valueOf(responseCode);
	}



	/**
	 * generate String
	 * @param length length
	 * @return generate text
	 */
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

	/**
	 * generate Test
	 * @return text
	 */
	public static String generateTestData() {
		return randomString(10);
	}

}
