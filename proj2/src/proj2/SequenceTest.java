package proj2;

import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import proj2.Sequence;

public class SequenceTest {

    @Rule // a test will fail if it takes longer than 1/10 of a second to run
    public Timeout timeout = Timeout.millis(100);

    @Test // toString of an empty list (10 capacity) should result in {[]} (capacity = 10)
    public void testToString1(){
        Sequence s = new Sequence();
        String expected = "{} (capacity = 10)";
        assertEquals(expected,s.toString());
    }

    @Test //Adds one element, using addBefore
    public void testAddBefore1(){
        Sequence s = new Sequence();
        s.addBefore("A");
        String expected = "{>A} (capacity = 10)";
        assertEquals(expected, s.toString());
    }

    @Test //Adds one element using addAfter. This test is to debug the 70+ "index - 1" errors in my code
    public void testAddAfter1(){
        Sequence s = new Sequence();
        s.addAfter("A");
        s.addAfter("B");
        s.addAfter("C");
        String expected = "{A, B, >C} (capacity = 10)";
        assertEquals(expected, s.toString());
    }

    @Test //Adds a few elements using addBefore
    public void testAddBefore2(){
        Sequence s = new Sequence();
        s.addBefore("A");
        s.addBefore("B");
        s.addBefore("C");
        String expected = "{>C, B, A} (capacity = 10)";
        assertEquals(expected, s.toString());
    }

    @Test // Tests the equality of 2 Sequences
    public void testEquals(){
        Sequence s1 = new Sequence(5);
        Sequence s2 = new Sequence();
        s1.addBefore("A");
        s1.addBefore("B");
        s1.addBefore("C");
        s2.addBefore("A");
        s2.addBefore("B");
        s2.addBefore("C");
        assertTrue(s1.equals(s2));
    }

    @Test // Tests addAll, by adding s2 to s1
    public void testAddAll(){
        Sequence s1 = new Sequence(3);
        Sequence s2 = new Sequence();

        s1.addBefore("A");
        s1.addBefore("B");
        s1.addBefore("C");

        s2.addBefore("X");
        s2.addBefore("Y");
        s2.addBefore("Z");

        s1.addAll(s2);

        System.out.println(s1);
    }

    @Test //tests a full sequence with addBefore
    //still is backwards, but has no more index errors
    public void testAddBeforeFull(){
        Sequence s = new Sequence(3);
        s.addBefore("A");
        s.addBefore("B");
        s.addBefore("C");
        s.addBefore("D");
        System.out.println(s);
    }

    @Test //tests a full sequence with addAfter
    public void testAddAfterFull(){
        Sequence s = new Sequence(3);
        s.addAfter("A");
        s.addAfter("B");
        s.addAfter("C");
        s.addAfter("D");
        System.out.println(s);
    }

    @Test // tests clone to see if changing the clone changes the og
    public void testCloneCopy(){
        Sequence s1 = new Sequence(5);
        Sequence s2;

        s1.addAfter("A");
        s1.addAfter("B");
        s1.addAfter("C");

        s2 = s1.clone();
        s2.addAfter("D");
        s2.addAfter("E");

        System.out.println(s1);
        System.out.println(s2);
    }

    @Test // tests clone to see if changing the og changes the clone
    public void testCloneCopy2(){
        Sequence s1 = new Sequence(5);
        Sequence s2;

        s1.addAfter("A");
        s1.addAfter("B");
        s1.addAfter("C");

        s2 = s1.clone();
        s1.addAfter("D");
        s1.addAfter("E");

        System.out.println(s1);
        System.out.println(s2);
    }

    @Test // Tests addAll, by adding s2 to s1
    public void testAddAll2(){
        Sequence s1 = new Sequence(2);
        Sequence s2 = new Sequence(3);

        s1.addBefore("D");
        s1.addBefore("E");

        s2.addBefore("X");
        s2.addBefore("Y");
        s2.addBefore("Z");

        s1.addAll(s2);

        System.out.println(s1);
    }

    @Test //tests addBefore on an already established set of elements
    public void testAddAfter3(){
        Sequence s = new Sequence();

        s.addAfter("A");
        s.addAfter("B");
        s.addAfter("C");
        s.addAfter("D");
        s.addAfter("E");

        System.out.println(s);
    }

        @Test //Adds several elements using addBefore
    public void testAddBefore3(){
        Sequence s = new Sequence();
        s.addBefore("A");
        s.addBefore("B");
        s.addBefore("C");
        s.addBefore("D");
        s.addBefore("E");
        String expected = "{>E, D, C, B, A} (capacity = 10)";
        assertEquals(expected, s.toString());
    }

    @Test //adds an element using adeBefore in the middle
    public void testAddBefore4() {
        Sequence s = new Sequence();
        s.addAfter("A");
        s.addAfter("B");
        s.addAfter("C");
        s.addAfter("E");
        s.addBefore("D");
        String expected = "{A, B, C, >D, E} (capacity = 10)";
        assertEquals(expected, s.toString());
    }

    @Test //adds a sequence(3) to a sequence(2) using addAll
    public void testAddAll3(){
        Sequence s1 = new Sequence(3);
        Sequence s2 = new Sequence(2);

        s1.addAfter("C");
        s1.addAfter("E");
        s1.addBefore("D");

        s2.addBefore("B");
        s2.addBefore("A");

        s2.addAll(s1);
        String expected = "{>A, B, C, D, E} (capacity = 5)";
        System.out.println(s2);
        assertEquals(expected, s2.toString());
    }

}
