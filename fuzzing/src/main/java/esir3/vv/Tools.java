package esir3.vv;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

/**
 * Created by qfdk on 2016/11/16.
 */
class Tools {

    public enum type {Integer,String};

    // HTTP GET request
    static String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print result
        return responseCode+"#"+response.toString();
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
            System.out.println(e);
        }
        try {
            prop.storeToXML(fos, file_name);
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
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
            System.out.println(e);
        }
        Properties prop = new Properties();
        try {
            prop.loadFromXML(fis);
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            fis.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return prop;
    }
}
