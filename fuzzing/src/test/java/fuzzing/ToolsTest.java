/**
 *
 */
package fuzzing;

import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Test;
import esir3.vv.Tools;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Maiga
 */
public class ToolsTest {

	@Test
	public void veryComplexTest() {
		assertTrue(true);
	}

	@Test
	public void testSendGet1() throws Exception {
		String res = Tools.sendGet("http://localhost:8080/en").get(0);
		assertTrue(res.equals("404"));
	}

	@Test
	public void testSendGet2() throws Exception {
		String res = Tools.sendGet("http://localhost:8080/").get(0);
		assertTrue(res.equals("200"));
	}

	@Test
	public void testSendGet3() throws Exception {
		String ret = Tools.sendGet("http://localhost:8080/api/v1/").get(0);
		System.out.println(ret);
		assertTrue(ret.equals("405"));
	}

	@Test
	public void testSendGet4() throws Exception {
		String url = "http://localhost:8080/api/v1/getStatus";
		String ret = Tools.sendGet(url).get(0);
		assertTrue(ret.equals("200")&&"{status:ok,msg:api works!}".equals(Tools.sendGet(url).get(1)));
	}

	@Test
	public void testSendGet5() throws Exception {
		List<String> codes = new ArrayList<>();
		codes.add("200");
		codes.add("400");
		codes.add("404");
		codes.add("405");

		List<String> resp = new ArrayList<>();
		for (String code : codes) {
			String tmp = Tools.sendGet("http://localhost:8080/api/v1/testCode?code=" + code).get(0);
			resp.add(tmp);
		}
		assertTrue(codes.equals(resp));
	}


	@Test
	public void testSendPost1() throws Exception {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("name","coucou");
		jsonObject.put("age","20");

		Map<String,String> map=new TreeMap<>();
		map.put("name","coucou");
		map.put("age","20");

		List<String> resp = new ArrayList<>();
		resp= Tools.sendPost("http://localhost:8080/api/v1/testPost",map);
		System.out.print(resp);
		assertTrue(resp.get(0).equals("200"));
	}
}
