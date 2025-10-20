package org.example;

import java.util.Scanner;
import java.util.Random;

public class MathProblemGenerator {
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请选择题类型：");
        System.out.println("1. 纯加法");
        System.out.println("2. 纯减法");
        System.out.println("3. 混合加减法");
        int type = scanner.nextInt();

        System.out.println("请输入题目数量：");
        int count = scanner.nextInt();

        System.out.println("是否生成3个数混合加减法？（1: 是，0: 否）");
        int isThreeDigit = scanner.nextInt();

        generateProblems(type, count, isThreeDigit == 1);
    }

    private static void generateProblems(int type, int count, boolean isThreeDigit) {
        for (int i = 0; i < count; i++) {
            int num1 = generateRandomNumber(isThreeDigit);
            int num2 = generateRandomNumber(isThreeDigit);
            int num3 = isThreeDigit ? generateRandomNumber(isThreeDigit) : 0;
            String operator1 = getOperator(type);
            String operator2 = isThreeDigit ? getOperator(type) : "";

            if (isThreeDigit) {
                System.out.printf("%d %s %d %s %d = \n", num1, operator1, num2, operator2, num3);
            } else {
                System.out.printf("%d %s %d = \n", num1, operator1, num2);
            }
        }
    }

    private static int generateRandomNumber(boolean isThreeDigit) {
        return random.nextInt(99) + 1; // 1-99
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
}