package org.example;

import java.util.Scanner;
import java.util.Random;

public class MathProblemGenerator {
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请选择运算类型：");
        System.out.println("1. 2个数纯加法");
        System.out.println("2. 2个数纯减法");
        System.out.println("3. 3个数混合加减法");
        int operationType = scanner.nextInt();

        System.out.println("请输入题目数量：");
        int count = scanner.nextInt();

        boolean isThreeDigit = operationType == 3;
        int adjustedType = isThreeDigit ? 3 : operationType;

        generateProblems(adjustedType, count, isThreeDigit);
    }

    private static void generateProblems(int type, int count, boolean isThreeDigit) {
        for (int i = 0; i < count; i++) {
            int num1 = generateRandomNumber(isThreeDigit);
            int num2 = generateRandomNumber(isThreeDigit);
            int num3 = isThreeDigit ? generateRandomNumber(isThreeDigit) : 0;
            String operator1 = getOperator(type);
            String operator2 = isThreeDigit ? getOperator(type) : "";

            if (isThreeDigit) {
                int result = calculateResult(num1, operator1, num2, operator2, num3);
                if (result > 0 && result < 100) {
                    System.out.printf("%d %s %d %s %d = \n", num1, operator1, num2, operator2, num3);
                } else {
                    i--;
                }
            } else {
                int result = calculateResult(num1, operator1, num2, "", 0);
                if (result > 0 && result < 100) {
                    System.out.printf("%d %s %d = \n", num1, operator1, num2);
                } else {
                    i--;
                }
            }
        }
    }

    private static int generateRandomNumber(boolean isThreeDigit) {
        return random.nextInt(98) + 1; // 1-98 to ensure result < 100
    } 

    private static String getOperator(int type) {
        switch (type) {
            case 1:
                return "+";
            case 2:
                return "-";
            case 3:
                return random.nextBoolean() ? "+" : "-";
            default:
                return "+";
        }
    }

    private static int calculateResult(int num1, String op1, int num2, String op2, int num3) {
        int result;
        if (op2.isEmpty()) {
            result = op1.equals("+") ? num1 + num2 : num1 - num2;
        } else {
            int intermediate = op1.equals("+") ? num1 + num2 : num1 - num2;
            result = op2.equals("+") ? intermediate + num3 : intermediate - num3;
        }
        return result;
    }
}