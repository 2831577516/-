package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * MathProblemGenerator2 类的单元测试
 * 验证数学题目生成器的核心功能正确性
 */
public class MathProblemGenerator2Test {

    /**
     * 在每个测试方法执行前运行
     * 用于初始化测试环境
     */
    @Before
    public void setUp() {
        // 初始化测试环境
        // 可以在这里设置测试所需的初始状态
    }

    /**
     * 在每个测试方法执行后运行
     * 用于清理测试环境，释放资源
     */
    @After
    public void tearDown() {
        // 清理测试环境
        // 可以在这里重置状态或清理临时文件
    }

    /**
     * 测试随机数生成功能
     * 验证生成的随机数在有效范围内（1-99）
     */
    @Test
    public void testGenerateRandomNumber() {
        // 生成随机数
        int num = MathProblemGenerator2.generateRandomNumber();
        // 验证随机数在有效范围内
        assertTrue("随机数应在1-99范围内", num >= 1 && num <= 99);
    }

    /**
     * 测试加法运算功能
     * 验证基本的加法计算正确性
     */
    @Test
    public void testCalculateAddition() {
        // 执行加法运算：5 + 3
        int result = MathProblemGenerator2.calculate(5, "+", 3);
        // 验证结果等于8
        assertEquals("加法运算结果不正确", 8, result);
    }

    /**
     * 测试减法运算功能
     * 验证基本的减法计算正确性
     */
    @Test
    public void testCalculateSubtraction() {
        // 执行减法运算：5 - 3
        int result = MathProblemGenerator2.calculate(5, "-", 3);
        // 验证结果等于2
        assertEquals("减法运算结果不正确", 2, result);
    }

    /**
     * 测试减法运算的边界情况：结果为负数
     * 验证当减法结果可能为负数时抛出异常
     */
    @Test(expected = ArithmeticException.class)
    public void testCalculateSubtractionNegativeResult() {
        // 执行可能导致负数的减法运算：3 - 5
        // 期望抛出 ArithmeticException 异常
        MathProblemGenerator2.calculate(3, "-", 5);
    }

    /**
     * 测试乘法运算功能
     * 验证基本的乘法计算正确性
     */
    @Test
    public void testCalculateMultiplication() {
        // 执行乘法运算：5 * 3
        int result = MathProblemGenerator2.calculate(5, "*", 3);
        // 验证结果等于15
        assertEquals("乘法运算结果不正确", 15, result);
    }

    /**
     * 测试除法运算功能
     * 验证基本的整数除法计算正确性
     */
    @Test
    public void testCalculateDivision() {
        // 执行除法运算：6 / 3
        int result = MathProblemGenerator2.calculate(6, "/", 3);
        // 验证结果等于2
        assertEquals("除法运算结果不正确", 2, result);
    }

    /**
     * 测试除法运算的边界情况：除数为零
     * 验证当除数为零时抛出异常
     */
    @Test(expected = ArithmeticException.class)
    public void testCalculateDivisionByZero() {
        // 执行除数为零的运算：5 / 0
        // 期望抛出 ArithmeticException 异常
        MathProblemGenerator2.calculate(5, "/", 0);
    }

    /**
     * 测试除法运算的边界情况：非整数结果
     * 验证当除法结果不是整数时抛出异常
     */
    @Test(expected = ArithmeticException.class)
    public void testCalculateDivisionNonIntegerResult() {
        // 执行可能导致非整数结果的除法：5 / 2
        // 期望抛出 ArithmeticException 异常
        MathProblemGenerator2.calculate(5, "/", 2);
    }
}