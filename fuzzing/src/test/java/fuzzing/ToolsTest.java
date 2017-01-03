package fuzzing;

import io.swagger.models.parameters.PathParameter;
import org.junit.Test;
import tp.esir3.vv.Tools;

import static org.junit.Assert.assertTrue;


/**
 * @author qfdk,Maiga
 */
public class ToolsTest {

	
	/**
	 * Should  generate always integer 
	 */
	@Test
	public void testRandomInteger(){

		io.swagger.models.parameters.PathParameter pp = new PathParameter();
		pp.setType("integer");

		boolean ret = false;

		try { 
			Integer.parseInt(Tools.generateTestData(pp)); 
			Integer.parseInt(Tools.generateTestData(pp)); 
			Integer.parseInt(Tools.generateTestData(pp)); 
			Integer.parseInt(Tools.generateTestData(pp)); 
			
			ret = true;
		} catch(NumberFormatException e) { 
			e.printStackTrace();
			ret = false; 
		} catch(NullPointerException e) {
			e.printStackTrace();
			ret =  false;
		}
		finally {
			assertTrue(ret);
		}
	}
	
	/**
	 * Should  generate always number 
	 */
	@Test
	public void testRandomNumber(){

		io.swagger.models.parameters.PathParameter pp = new PathParameter();
		pp.setType("number");

		boolean ret = false;

		try { 
			// 4 times gest data
			Integer.parseInt(Tools.generateTestData(pp)); 
			Integer.parseInt(Tools.generateTestData(pp)); 
			Integer.parseInt(Tools.generateTestData(pp)); 
			Integer.parseInt(Tools.generateTestData(pp)); 
			Integer.parseInt(Tools.generateTestData(pp)); 

			ret = true;
		} catch(NumberFormatException e) { 
			e.printStackTrace();
			ret = false; 
		} catch(NullPointerException e) {
			e.printStackTrace();
			ret =  false;
		}
		finally {
			assertTrue(ret);
		}
	}


	/**
	 * Should not generate always the same data 
	 */
	@Test
	public void testRandomString(){

		io.swagger.models.parameters.PathParameter pp = new PathParameter();
		
		pp.setType("string");
		String s1 = Tools.generateTestData(pp);
		String s2 = Tools.generateTestData(pp);
		
		assertTrue("not always same date generated, case string", !s1.equals(s2));
		
		pp.setType("integer");
		s1  = Tools.generateTestData(pp);
		s2  = Tools.generateTestData(pp);
		
		assertTrue("not always data generated, case integer", !s1.equals(s2));
		
		pp.setType("number");
		s1  = Tools.generateTestData(pp);
		s2  = Tools.generateTestData(pp);
		
		assertTrue("not always data generated, case number", !s1.equals(s2));
		
	}
	
	/**
	 * Should generate a non null data even if type is null in spec 
	 */
	@Test
	public void testRandomDataForNullType(){

		io.swagger.models.parameters.PathParameter pp = new PathParameter();
		
		String s1 = Tools.generateTestData(pp);
		
		assertTrue("shoud generate non empty data", !s1.isEmpty());
		
	}

}
