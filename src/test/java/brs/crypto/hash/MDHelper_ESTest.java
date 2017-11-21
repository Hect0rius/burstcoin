/*
 * This file was automatically generated by EvoSuite
 * Tue Nov 21 21:16:23 GMT 2017
 */

package brs.crypto.hash;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import brs.crypto.hash.RIPEMD160;
import brs.crypto.hash.SHA256;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class MDHelper_ESTest extends MDHelper_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      RIPEMD160 rIPEMD160_0 = new RIPEMD160();
      assertEquals(20, rIPEMD160_0.getDigestLength());
      assertEquals(64, rIPEMD160_0.getBlockLength());
      assertEquals("RIPEMD-160", rIPEMD160_0.toString());
      assertNotNull(rIPEMD160_0);
      
      rIPEMD160_0.update((byte)0);
      assertEquals(20, rIPEMD160_0.getDigestLength());
      assertEquals(64, rIPEMD160_0.getBlockLength());
      assertEquals("RIPEMD-160", rIPEMD160_0.toString());
      
      rIPEMD160_0.makeMDPadding();
      assertEquals(20, rIPEMD160_0.getDigestLength());
      assertEquals(64, rIPEMD160_0.getBlockLength());
      assertEquals("RIPEMD-160", rIPEMD160_0.toString());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      SHA256 sHA256_0 = new SHA256();
      assertEquals(32, sHA256_0.getDigestLength());
      assertNotNull(sHA256_0);
      
      byte[] byteArray0 = new byte[2];
      sHA256_0.update(byteArray0);
      assertArrayEquals(new byte[] {(byte)0, (byte)0}, byteArray0);
      assertEquals(32, sHA256_0.getDigestLength());
      
      // Undeclared exception!
      try { 
        sHA256_0.doPadding(byteArray0, 3573);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // 3573
         //
         verifyException("brs.crypto.hash.SHA2Core", e);
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      RIPEMD160 rIPEMD160_0 = new RIPEMD160();
      rIPEMD160_0.makeMDPadding();
      rIPEMD160_0.makeMDPadding();
      assertEquals(64, rIPEMD160_0.getBlockLength());
  }
}