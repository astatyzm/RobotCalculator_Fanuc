
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import Application.Builder;

public class CalcTest {
	@Test
	public void TestOKFile() throws Exception {
		System.out.print(this.getClass());
		String lsOK = IOUtils.toString(this.getClass().getResourceAsStream("testOK.LS"), "UTF-8");
		assertTrue(Builder.CheckConfig(lsOK).isEmpty());
		assertTrue(Builder.CheckSpeed(lsOK).isEmpty());
		assertTrue(Builder.ChceckZone(lsOK).isEmpty());
	}

	@Test
	public void TestNOKFile() throws Exception {
		String lsNOK = IOUtils.toString(this.getClass().getResourceAsStream("testNOK.LS"), "UTF-8");
		assertFalse(Builder.CheckConfig(lsNOK).isEmpty());
		assertFalse(Builder.CheckSpeed(lsNOK).isEmpty());
		assertFalse(Builder.ChceckZone(lsNOK).isEmpty());
	}

	@Test
	public void TestReversePath() throws IOException {
		String readedLSFile = IOUtils.toString(this.getClass().getResourceAsStream("testOK.LS"), "UTF-8");
		String reversedLS = Builder.ReversePath(readedLSFile);
		assertNotEquals(readedLSFile, reversedLS);
	}

	
	/*
	 * @Test(expected = Exception.class) public void TestReadInputFile() { try {
	 * String readedLSFile =
	 * IOUtils.toString(this.getClass().getResourceAsStream("testOK.LS"), "UTF-8");
	 * String savedFile = Builder.ReadInputFile(); assertNotNull(savedFile);
	 * Assert.fail();
	 * 
	 * } catch (IOException e) { Assert.fail("Exception " + e); e.printStackTrace();
	 * }
	 * 
	 * }
	 * 
	 * @Test(expected = Exception.class) public void TestSaveOutputFile() throws
	 * IOException { String readedLSFile =
	 * IOUtils.toString(this.getClass().getResourceAsStream("testOK.LS"), "UTF-8");
	 * File savedFile = Builder.SaveOutputFile(readedLSFile);
	 * assertNotNull(savedFile); }
	 */

}
