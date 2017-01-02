package tp.esir3.vv;

import java.io.IOException;

import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Form;

public class HttpClient {

	public static void main(String[] args) {
		try {
			String resp =
					org.apache.http.client.fluent.Request.Post("http://localhost:3000/echo/")
					.useExpectContinue()
					.setHeader("Accept","application/json")
					.setHeader("Content-Type","application/x-www-form-urlencoded")
					.version(HttpVersion.HTTP_1_1)
					.bodyForm(Form.form().add("year", "1993").add("name", "secret").build())
					.execute().returnResponse().toString();

			System.out.println(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		try {
//			String resp =
//					org.apache.http.client.fluent.Request.Post("http://localhost:4000/v2/pets/12")
//					.useExpectContinue()
//					.setHeader("Accept","application/json")
//					.setHeader("Content-Type","application/x-www-form-urlencoded")
//					.version(HttpVersion.HTTP_1_1)
//					.bodyForm(Form.form().add("name", "ben").add("status", "100").build())
//					.execute().returnResponse().toString();
//
//			System.out.println(resp);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}


//		try {
//			String resp =
//					org.apache.http.client.fluent.Request.Post("http://localhost:4000/v2/pets")
//					.useExpectContinue()
//					.setHeader("Accept","application/json")
//					.setHeader("Content-Type","application/json")
//					.version(HttpVersion.HTTP_1_1)
//					.bodyForm(Form.form().add("body", "ben").build())
//					.execute().returnResponse().toString();
//
//			System.out.println(resp);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
