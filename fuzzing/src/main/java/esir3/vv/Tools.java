package esir3.vv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

/**
 * Created by qfdk on 2016/11/16.
 */
class Tools {

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
    
	 /*** Pas beau !!
	 * @param type
	 * @return
	 */
	public static  String generateTestData(String type){
		switch (type) {
		case "integer":
		case "Integer":
		case "int":
			return "10";
		case "STRING":
		case "string":
		case "str":
			return "generatedByYousLI";
		default :
			return "generatedByYousLI";
		}
	}
}
