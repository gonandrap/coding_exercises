package java;

import static org.junit.Assert.assertEquals;


import org.junit.*;


public class UnitTestingExample {
    
    public int sum(int a, int b) {
        return a+b;
    }

    public int excp_trigger() throws NullPointerException {
        System.out.println("Trowing an exception");
        throw new NullPointerException();
    }

    @Test 
    public void testSum() {
        UnitTestingExample obj = new UnitTestingExample();
        assertEquals(obj.sum(1,2), 3);
        assertEquals(obj.sum(2,2), 4);
        Assert.assertTrue(obj.sum(2,2) == 4);
    }

    @Test (expected = NullPointerException.class)
    public void testExcep() {
        UnitTestingExample obj = new UnitTestingExample();
        obj.excp_trigger();
    }

    @Test
    public void test_max() {
        assertEquals(Math.max(2,4), 4);
    }
}
