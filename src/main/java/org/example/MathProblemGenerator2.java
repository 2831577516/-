package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MathProblemGenerator2 {
    public static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请选择题类型：");
        System.out.println("1. 纯加法");
        System.out.println("2. 纯减法");
        System.out.println("3. 纯乘法");
        System.out.println("4. 纯除法");
        System.out.println("5. 加减法混合运算");
        System.out.println("6. 乘除法混合运算");
        System.out.println("7. 三个数加减乘除混合运算");
        int type = scanner.nextInt();

        System.out.println("请输入题目数量：");
        int count = scanner.nextInt();

        System.out.println("请输入每行显示的题目数量：");
        int columns = scanner.nextInt();

        System.out.println("请输入输出文件名（如：problems.csv）：");
        String fileName = scanner.next();

        generateProblems(type, count, columns, fileName);
    }

    public static void generateProblems(int type, int count, int columns, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < count; i++) {
                int num1 = generateRandomNumber();
                int num2 = generateRandomNumber();
                int num3 = (type == 7) ? generateRandomNumber() : 0;
                String operator1 = getOperator(type);
                String operator2 = (type == 7) ? getOperator(type) : "";

                String problem;
                if (type == 7) {
                    problem = String.format("%d %s %d %s %d = ", num1, operator1, num2, operator2, num3);
                } else {
                    problem = String.format("%d %s %d = ", num1, operator1, num2);
                }

                writer.write(problem);
                if ((i + 1) % columns == 0) {
                    writer.write("\n");
                } else {
                    writer.write(", ");
                }
            }
            System.out.println("题目已生成并保存到 " + fileName);
        } catch (IOException e) {
            System.err.println("文件写入错误：" + e.getMessage());
        }
    }

    public static int generateRandomNumber() {
        return random.nextInt(99) + 1; // 1-99
    }

    public static String getOperator(int type) {
        switch (type) {
            case 1:
                return "+";
            case 2:
                return "-";
            case 3:
                return "*";
            case 4:
                return "/";
            case 5:
                return random.nextBoolean() ? "+" : "-";
            case 6:
                return random.nextBoolean() ? "*" : "/";
            case 7:
                int op = random.nextInt(4);
                switch (op) {
                    case 0:
                        return "+";
                    case 1:
                        return "-";
                    case 2:
                        return "*";
                    case 3:
                        return "/";
                    default:
                        return "+";
                }
            default:
                return "+";
        }
    }

    public static int calculate(int num1, String operator, int num2) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("除数不能为零");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("无效的运算符: " + operator);
        }
    }
}