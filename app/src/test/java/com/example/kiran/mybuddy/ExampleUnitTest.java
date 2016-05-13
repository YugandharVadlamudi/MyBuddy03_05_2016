package com.example.kiran.mybuddy;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
   public void done()
   {
       Assert.assertArrayEquals("demo",new String[]{"one","two"},new String[]{"One"});
//       Assert.assertTrue(1>2);

   }
}