package org.apache.commons.io;

import java.io.*;

public class TestIOUtils {
    public static void main(String[] args) throws IOException {
        System.out.println("------ 开始模块单元质量检查 ------");

        // 运行 contentEquals 缺陷测试
        System.out.println("运行测试: contentEquals 缺陷检测");
        testContentEqualsDefect();

        // 运行 skipFully 测试
        System.out.println("运行测试: skipFully 方法");
        testSkipFully();

        // 运行 copy 方法测试
        System.out.println("运行测试: copy 方法");
        testCopy();

        System.out.println("------ 检查结束 ------");
    }

    /**
     * 测试 contentEquals 方法是否能正确检测到两个流内容不同
     * 由于缺陷，该方法在某些情况下会返回错误的结果
     */
    private static void testContentEqualsDefect() throws IOException {
        InputStream input1 = new ByteArrayInputStream(new byte[]{'a'});
        InputStream input2 = new ByteArrayInputStream(new byte[]{'a', 0});

        boolean result = IOUtils.contentEquals(input1, input2);

        System.out.println("测试输入流1内容: 'a'");
        System.out.println("测试输入流2内容: 'a' + '\\0'");

        if (result) {
            System.err.println("【错误】contentEquals 误判: 流内容不同但返回 true");
        } else {
            System.out.println("【通过】contentEquals 正确检测出流不同");
        }
    }

    /**
     * 测试 skipFully 方法是否能正确跳过指定的字节数
     */
    private static void testSkipFully() throws IOException {
        InputStream input = new ByteArrayInputStream("Hello World".getBytes());

        System.out.println("跳过前流内容: \"Hello World\"");
        try {
            IOUtils.skipFully(input, 6);
            byte[] remaining = IOUtils.toByteArray(input);
            String result = new String(remaining);

            System.out.println("跳过 6 字节后流剩余内容: " + result);

            if ("World".equals(result)) {
                System.out.println("【通过】skipFully 正确跳过字符");
            } else {
                System.err.println("【错误】skipFully 结果不符合预期");
            }
        } catch (EOFException e) {
            System.err.println("【异常】skipFully 提前到达 EOF: " + e.getMessage());
        }
    }

    /**
     * 测试 copy 方法是否正确复制输入流内容
     */
    private static void testCopy() throws IOException {
        InputStream input = new ByteArrayInputStream("TestCopy".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        System.out.println("输入流内容: \"TestCopy\"");
        IOUtils.copy(input, output);
        String result = output.toString();

        System.out.println("复制结果: " + result);

        if ("TestCopy".equals(result)) {
            System.out.println("【通过】copy 方法正确复制数据");
        } else {
            System.err.println("【错误】copy 方法结果不匹配");
        }
    }
}
