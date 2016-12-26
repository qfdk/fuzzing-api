/**
 * 
 */
package fuzzing;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import esir3.vv.Tools;

/**
 * @author Maiga
 *
 */
public class ToolsTests {

	@Test
	public void veryComplexTest() {
		assertTrue(true);
	}
	
	@Test(expected=java.net.UnknownHostException.class)
	public void testGetBadUrl() throws Exception {
		Tools.sendGet("https://www.youssli.fr/en");	
	}
	
	@Test
	public void testGetGoodUrl() throws Exception {
		String ret = Tools.sendGet("https://www.google.fr/");
		assertTrue(ret.split("#")[0].equals("200"));
		System.out.println(ret);
	}
	
	@Test
	public void testGetGoodUrl2() throws Exception {
		String ret = Tools.sendGet("https://www.google.fr/");
		System.out.println(ret);
		assertTrue(ret.split("#")[0].equals("200"));
	}
}
