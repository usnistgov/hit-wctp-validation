package gov.nist.hit.core.service.wctp.test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import gov.nist.hit.wctp.WCTPReport;
import gov.nist.hit.wctp.WCTPValidation;

//@Ignore
public class Testvalidation {

	@Test
	public void test() {
		WCTPValidation wtcpVal = new WCTPValidation();
		File f = new File("src/test/resources/TestCase2.xml");
		String absolutePath = f.getAbsolutePath();
		System.out.println(absolutePath);
		System.out.println(f.exists());
		WCTPReport report = null;
		try {
			report = WCTPValidation.generic(f);
		} catch (IOException e) {
			fail();
		}	
		System.out.println(report.toText());
		assertNotNull(report);
		
	}

}
