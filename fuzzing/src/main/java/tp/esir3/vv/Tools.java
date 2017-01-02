package tp.esir3.vv;

import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;

/**
 * Created by qfdk on 2016/11/16.
 */
public class Tools {

	public enum type {Integer, String};

	public enum OperationType {POST, GET, PUT, DELETE};

	static Logger logger = LoggerFactory.getLogger(Tools.class);

	// HTTP GET request
	public static List<String> sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		logger.debug("Sending 'GET' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);
		String body = printUrlContents(obj,"GET");
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(responseCode));
		list.add(body);
		return list;
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
	 * @param json json
	 * @return code 200/404 etc...
	 * @throws Exception
	 */
	public static List<String> sendPost(String url, JSONObject json) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=utf8");

		con.setDoInput(true);
		con.setDoOutput(true);

		OutputStream os = con.getOutputStream();
		os.write(json.toString().getBytes("UTF-8"));
		os.close();

		int responseCode = con.getResponseCode();
		logger.debug("Sending 'POST' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);
		String body = printUrlContents(obj,"POST");
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(responseCode));
		list.add(body);
		return list;
	}



	/**
	 * sendPOST
	 *
	 * @param url url
	 * @param map map key-value
	 * @return code 200/404 etc...
	 * @throws Exception
	 */
	public static List<String> sendPost(String url, Map<String, String> map) throws Exception {

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

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		if (!map.keySet().isEmpty()) {
			String urlParameters = sb.toString().substring(0, sb.length() - 1);
			logger.debug(urlParameters);

			// Send post request
			wr.writeBytes(urlParameters);
		}
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		logger.debug("Sending 'POST' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);
		String body = printUrlContents(obj,"POST");

		List<String> list = new ArrayList<>();
		list.add(String.valueOf(responseCode));
		list.add(body);
		return list;
	}
	/**
	 * send Delete request
	 *
	 * @param url url
	 * @return responseCode
	 * @throws Exception e
	 */
	public static List<String> sendDel(String url) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(new HttpDelete(url));
		int responseCode = response.getStatusLine().getStatusCode();
		logger.debug("\nSending 'Delete' request to URL : " + url);
		logger.debug("Response Code : " + responseCode);
		String body = printUrlContents(new URL(url),"DELETE");
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(responseCode));
		list.add(body);
		return list;
	}

	/**
	 * send PUT request
	 *
	 * @param url url
	 * @param map key value
	 * @return responseCode
	 * @throws Exception e
	 */
	public static List<String> sendPut(String url, Map<String, String> map) throws Exception {

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
		String body = printUrlContents(obj,"PUT");
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(responseCode));
		list.add(body);
		return list;
	}

	/**
	 * print Contents
	 *
	 * @param url url
	 * @throws IOException
	 */
	private static String printUrlContents(URL url,String type) {
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(type);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder ss = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				ss.append(inputLine);
			}
			in.close();
			return ss.toString();
		}catch (Exception e)
		{
			return "Something was wrong!";
		}
	}

	/**
	 * generate String
	 *
	 * @param length length
	 * @return generate text
	 */
	private static String randomString(int length) {
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
	 * generate boolean
	 *
	 * @return
	 */
	private static String randomBoolean() {
		return Math.random() > 0.5 ? "true" : "false";
	}


	/**
	 * generate integer betwen 0 and max
	 *
	 * @param max : max value
	 * @return
	 */
	private static String randomInteger(int max) {

		return String.valueOf((int) (Math.random() * max));
	}


	/**
	 * generate Test
	 *
	 * @return text
	 */
	public static String generateTestData(Parameter param) {

		String generatedData = "";
		if (param instanceof io.swagger.models.parameters.PathParameter) 
		{
			String type = ((io.swagger.models.parameters.PathParameter) param).getType();

			switch (type) {

			case "integer":
			case "number":
				generatedData =  randomInteger(100);
				break;

			case "boolean":
				generatedData = randomBoolean();
				break;

			case "string":
				generatedData =  randomString(10);
				break;
			}
		} else if (param instanceof io.swagger.models.parameters.BodyParameter) {
			generatedData = generateBodyParameter((BodyParameter)param);
		}
		return generatedData;
	}

	private static String generateBodyParameter(BodyParameter param) {

		if (param.getSchema() != null &&  param.getSchema().getReference() !=null)
		{
			String type = param.getSchema().getReference().replace("#/definitions/", "");

			switch (type) {
			case "Pet":
				JSONObject r = new JSONObject("{ \"id\": 0, \"category\": { \"id\": 0, \"name\": \"string\" }, \"name\": \"doggie\", \"photoUrls\": [ \"string\" ], \"tags\": [ { \"id\": 0, \"name\": \"string\" } ], \"status\": \"string\" }");
				return r.toString();

			default:
				break;
			}
			return "default";
		}
		else
		{
			return new JSONObject().put("body", "default").toString();
		}
	}

	public static JSONObject generatePostTestData(Parameter param) {

		String type ="";
		if (param instanceof io.swagger.models.parameters.PathParameter) 
		{
			type = ((PathParameter) param).getType();
		}
		JSONObject r = new JSONObject();

		if(type!=null)
		{
			switch (type) {
			case "Pet":
				r = new JSONObject("{ \"id\": 0, \"category\": { \"id\": 0, \"name\": \"string\" }, \"name\": \"doggie\", \"photoUrls\": [ \"string\" ], \"tags\": [ { \"id\": 0, \"name\": \"string\" } ], \"status\": \"string\" }");
				break;
			case "User":
				r = new JSONObject("{ \"id\": 0, \"username\": \"string\", \"firstName\": \"string\", \"lastName\": \"string\", \"email\": \"string\", \"password\": \"string\", \"phone\": \"string\", \"userStatus\": 0 }");
				break;

			default:
				r = new JSONObject("{ \"id\": 0, \"category\": { \"id\": 0, \"name\": \"string\" }, \"name\": \"doggie\", \"photoUrls\": [ \"string\" ], \"tags\": [ { \"id\": 0, \"name\": \"string\" } ], \"status\": \"string\" }");
				break;
			}
		}
		return r; 
	}


}