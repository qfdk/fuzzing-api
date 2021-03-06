package fuzzing;

import org.json.JSONObject;
import org.junit.Test;
import tp.esir3.vv.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

/**
 * Created by qfdk on 2017/1/2.
 */
public class HttpTest {
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
        assertTrue(ret.equals("200") && "OK".equals(Tools.sendGet(url).get(1)));
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "coucou");
        jsonObject.put("age", "20");

        Map<String, String> map = new TreeMap<>();
        map.put("name", "coucou");
        map.put("age", "20");
        List<String> resp = Tools.sendPost("http://localhost:8080/api/v1/testPost", map);
        assertTrue(resp.get(0).equals("200"));
    }

    @Test
    public void testSendPost2() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "coucou");
        jsonObject.put("age", "20");

        Map<String, String> map = new TreeMap<>();
        map.put("name", "coucou");
        map.put("age", "20");
        List<String> resp = Tools.sendPost("http://localhost:8080/api/v1/testPost", map);
        assertTrue(resp.get(1).equals(jsonObject.toString()));
    }

    @Test
    public void testSendPut1() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "coucou");
        jsonObject.put("age", "20");
        List<String> resp = Tools.sendPut("http://localhost:8080/api/v1/testPut", jsonObject);
        System.out.println(resp);
        assertTrue("OK".equals(resp.get(1)));
    }

    @Test
    public void testSendPut2() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "coucou");
        jsonObject.put("age", "20");
        List<String> resp = Tools.sendPut("http://localhost:8080/api/v1/testPut", jsonObject);
        System.out.println(resp);
        assertTrue("200".equals(resp.get(0)));
    }

    @Test
    public void tetSendDel1() throws Exception {
        List<String> resp = Tools.sendDel("http://localhost:8080/api/v1/testDel");
        System.out.println(resp);
        assertTrue("200".equals(resp.get(0)));
    }

    @Test
    public void tetSendDel2() throws Exception {
        List<String> resp = Tools.sendDel("http://localhost:8080/api/v1/testDel");
        System.out.println(resp);
        assertTrue("OK".equals(resp.get(1)));
    }
}
