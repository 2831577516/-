package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * 数学题目生成器类
 * 功能：根据用户选择的题型生成指定数量的算术题目，并输出到CSV文件。
 * 支持纯加法、减法、乘法、除法及混合运算，确保结果在合理范围内（1-99的整数）。
 */
public class MathProblemGenerator2 {
    // 随机数生成器，用于生成操作数和运算符
    public static final Random random = new Random();

    //程序主入口，处理用户输入并触发题目生成逻辑
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 显示题型菜单供用户选择
        System.out.println("1. 纯加法");
        System.out.println("2. 纯减法");
        System.out.println("3. 纯乘法");
        System.out.println("4. 纯除法");
        System.out.println("5. 加减法混合运算");
        System.out.println("6. 乘除法混合运算");
        System.out.println("7. 三个数加减乘除混合运算");
        System.out.println("请选择题类型：");
        int type;
        while (true) {
            try {
                type = scanner.nextInt();
                if (type < 1 || type > 7) {
                    System.out.println("输入无效，请选择1-7之间的数字：");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("输入无效，请输入数字：");
                scanner.next(); // 清除无效输入
            }
        }

        System.out.println("请输入题目数量：");
        int count;
        while (true) {
            try {
                count = scanner.nextInt();
                if (count <= 0) {
                    System.out.println("题目数量必须大于0，请重新输入：");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("输入无效，请输入数字：");
                scanner.next(); // 清除无效输入
            }
        }

        System.out.println("请输入每行显示的题目数量：");
        int columns;
        while (true) {
            try {
                columns = scanner.nextInt();
                if (columns <= 0) {
                    System.out.println("每行题目数量必须大于0，请重新输入：");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("输入无效，请输入数字：");
                scanner.next(); // 清除无效输入
            }
        }

        System.out.println("请输入输出文件名（如：problems.csv）：");
        // 文件默认保存到src/end/目录下
        String fileName = "src/end/" + scanner.next().trim();

        generateProblems(type, count, columns, fileName);
        scanner.close();
    }

    /*
     * 核心方法：生成题目并写入文件
     * 根据题型循环生成有效题目（确保结果非负且小于100，除法需为整数），并按格式输出到CSV文件。
     *
     * @param type    题型编号（1-7）
     * @param count   需生成的题目数量
     * @param columns 每行显示的题目数（控制CSV格式换行）
     * @param fileName 输出文件路径
     */
    public static void generateProblems(int type, int count, int columns, String fileName) {
        try {
            // 验证文件路径是否合法
            if (fileName == null || fileName.trim().isEmpty()) {
                throw new IOException("文件名不能为空");
            }
            // 确保目录存在
            java.io.File parentDir = new java.io.File(fileName).getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            try (FileWriter writer = new FileWriter(fileName)) {
                for (int i = 0; i < count; i++) {
                    int num1, num2, num3;
                    String operator1, operator2;
                    String problem;
                    boolean valid; // 标记当前生成的题目是否有效

                    // 循环生成题目，直到得到有效结果（1-99的整数）
                    do {
                        num1 = generateRandomNumber();
                        num2 = generateRandomNumber();
                        // 混合运算需第三个操作数
                        num3 = (type == 7) ? generateRandomNumber() : 0;
                        operator1 = getOperator(type);
                        operator2 = (type == 7) ? getOperator(type) : "";

                        if (type == 7) {
                            // 生成三个数的题目格式
                            problem = String.format("%d %s %d %s %d = ", num1, operator1, num2, operator2, num3);
                            try {
                                // 先计算前两个数的中间结果，验证是否合法
                                int intermediate = calculate(num1, operator1, num2);
                                if (intermediate <= 0 || intermediate >= 100) {
                                    valid = false;
                                    continue;
                                }
                                // 用中间结果与第三个数计算最终结果
                                int result = calculate(intermediate, operator2, num3);
                                if (result <= 0 || result >= 100) {
                                    valid = false;
                                    continue;
                                }
                            } catch (ArithmeticException e) {
                                valid = false;
                                continue;
                            }
                        } else {
                            // 生成两个数的题目格式
                            problem = String.format("%d %s %d = ", num1, operator1, num2);
                            try {
                                int result = calculate(num1, operator1, num2);
                                // 检查结果是否在有效范围内
                                if (result <= 0 || result >= 100) {
                                    valid = false;
                                    continue;
                                }
                            } catch (ArithmeticException e) {
                                valid = false;
                                continue;
                            }
                        }
                        valid = true;
                    } while (!valid);

                    // 写入题目，并根据columns控制换行
                    writer.write(problem);
                    if ((i + 1) % columns == 0) {
                        writer.write("\n"); // 换行
                    } else {
                        writer.write(", "); // 同一行内用逗号分隔
                    }
                }
                System.out.println("题目已生成并保存到 " + fileName);
            }
        } catch (IOException e) {
            System.err.println("文件写入错误：" + e.getMessage());
        }
    }

    /*
     * 生成1-99的随机整数
     * @return 1至99之间的随机数
     */
    public static int generateRandomNumber() {
        return random.nextInt(99) + 1; // 1-99
    }

    /*
     * 根据题型返回对应的运算符
     * @param type 题型编号（1-7）
     * @return 运算符字符串（如"+", "-"等）
     */
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
                // 随机返回加号或减号
                return random.nextBoolean() ? "+" : "-";
            case 6:
                // 随机返回乘号或除号
                return random.nextBoolean() ? "*" : "/";
            case 7:
                // 随机返回加减乘除中的一种
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
                throw new IllegalArgumentException("无效的题型编号：" + type);
        }
    }

    /**
     * 执行算术运算，并验证合法性
     * @param num1     第一个操作数
     * @param operator 运算符（支持+、-、*、/）
     * @param num2     第二个操作数
     * @return 运算结果
     * @throws ArithmeticException 当减法结果为负、除数为零或除法结果非整数时抛出
     * @throws IllegalArgumentException 当运算符无效时抛出
     */
    public static int calculate(int num1, String operator, int num2) {
        // 验证运算符是否合法
        if (!operator.matches("[+\\-*/]")) {
            throw new IllegalArgumentException("无效的运算符：" + operator);
        }
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                // 确保减法结果不为负数
                if (num1 < num2) {
                    throw new ArithmeticException("减法结果不能为负数");
                }
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("除数不能为零");
                }
                // 确保除法结果为整数
                if (num1 % num2 != 0) {
                    throw new ArithmeticException("除法结果必须为整数");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("无效的运算符: " + operator);
        }
    }
}