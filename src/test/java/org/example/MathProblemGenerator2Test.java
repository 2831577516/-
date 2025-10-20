package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.*;

public class MathProblemGenerator2Test {
    private MathProblemGenerator2 generator;

    @Before
    public void setUp() {
        generator = new MathProblemGenerator2();
    }

    @After
    public void tearDown() {
        // Clean up any generated files
        File file = new File("test_problems.csv");
        if (file.exists()) {
            // Ensure the file is writable
            if (!file.canWrite()) {
                file.setWritable(true);
            }
            // Attempt to delete the file
            if (!file.delete()) {
                System.err.println("Failed to delete file: " + file.getAbsolutePath());
            }
        }
    }

    @Test
    public void testGenerateRandomNumber() {
        int num = generator.generateRandomNumber();
        assertTrue(num >= 1 && num <= 99);
    }

    @Test
    public void testGetOperator() {
        String op = generator.getOperator(1);
        assertEquals("+", op);

        op = generator.getOperator(2);
        assertEquals("-", op);

        op = generator.getOperator(3);
        assertEquals("*", op);

        op = generator.getOperator(4);
        assertEquals("/", op);
    }

    @Test
    public void testCalculate() {
        int result = generator.calculate(10, "+", 20);
        assertEquals(30, result);

        result = generator.calculate(20, "-", 10);
        assertEquals(10, result);

        result = generator.calculate(5, "*", 4);
        assertEquals(20, result);

        result = generator.calculate(20, "/", 5);
        assertEquals(4, result);
    }

    @Test(expected = ArithmeticException.class)
    public void testCalculateDivisionByZero() {
        generator.calculate(10, "/", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateInvalidOperator() {
        generator.calculate(10, "invalid", 20);
    }
}