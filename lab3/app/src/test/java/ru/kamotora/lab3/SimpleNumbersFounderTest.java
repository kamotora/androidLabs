package ru.kamotora.lab3;

import org.junit.Assert;
import org.junit.Test;


public class SimpleNumbersFounderTest {

    @Test
    public void simpleCorrectTest() {
        Assert.assertEquals(new Long(1), SimpleNumbersFounder.init(1).get());
        for (int i = 1; i < 10; i++)
            Assert.assertEquals(new Long(i), SimpleNumbersFounder.init(1, i).get());
    }

    @Test
    public void correctTests() {
        Assert.assertEquals(new Long(11), SimpleNumbersFounder.init(2).get());
        Assert.assertEquals(new Long(223), SimpleNumbersFounder.init(2, 2).get());
        Assert.assertEquals(new Long(1117), SimpleNumbersFounder.init(3).get());
    }

    @Test
    public void veryLongTest() {
        Assert.assertEquals(new Long(-1), SimpleNumbersFounder.init(20).get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failTest() {
        SimpleNumbersFounder.init(-1).get();

        SimpleNumbersFounder.init(2, 0).get();

        SimpleNumbersFounder.init(0, 2).get();

        SimpleNumbersFounder.init(3, 11).get();

        SimpleNumbersFounder.init(-1, 20).get();
    }
}