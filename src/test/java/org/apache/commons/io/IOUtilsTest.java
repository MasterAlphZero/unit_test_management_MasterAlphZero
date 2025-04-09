package org.apache.commons.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class IOUtilsTest {

    public InputStream input1;
    public InputStream input2;
    public ByteArrayOutputStream output;

    @Before
    public void setUp() {
        System.out.println("\n[Setup] 初始化测试环境...");
        output = new ByteArrayOutputStream();
    }

    @After
    public void tearDown() throws IOException {
        System.out.println("[Teardown] 关闭流...");
        if (input1 != null) input1.close();
        if (input2 != null) input2.close();
        output.close();
    }

    /**
     * 测试 contentEquals 方法的逻辑缺陷
     * 由于该方法存在缺陷，在某些情况下会误判不同的流为相同
     */
    @Test
    public void testContentEqualsDefect() throws IOException {
        System.out.println("运行测试: contentEquals 缺陷检测");

        input1 = new ByteArrayInputStream(new byte[]{'a'});
        input2 = new ByteArrayInputStream(new byte[]{'a', 0});

        boolean result = IOUtils.contentEquals(input1, input2);

        System.out.println("输入流1: 'a'");
        System.out.println("输入流2: 'a' + '\\0'");

        assertFalse("【错误】contentEquals 误判: 流内容不同但返回 true", result);
        System.out.println("【通过】contentEquals 检测到流内容不同");
    }

    /**
     * 测试 skipFully 方法是否能正确跳过指定的字节数
     */
    @Test
    public void testSkipFully() throws IOException {
        System.out.println("运行测试: skipFully 方法");

        input1 = new ByteArrayInputStream("Hello World".getBytes());

        System.out.println("原始流内容: \"Hello World\"");
        IOUtils.skipFully(input1, 6);

        byte[] remaining = IOUtils.toByteArray(input1);
        String result = new String(remaining);

        System.out.println("跳过 6 字节后剩余内容: " + result);

        assertEquals("【错误】skipFully 未正确跳过字符", "World",result);
        System.out.println("【通过】skipFully 方法正确跳过字符");
    }

    /**
     * 测试 copy 方法是否正确复制输入流内容
     */
    @Test
    public void testCopy() throws IOException {
        System.out.println("运行测试: copy 方法");

        input1 = new ByteArrayInputStream("TestCopy".getBytes());

        System.out.println("输入流内容: \"TestCopy\"");
        IOUtils.copy(input1, output);

        String result = output.toString();
        System.out.println("复制结果: " + result);

        assertEquals("【错误】copy 方法未正确复制内容", "TestCopy", result);
        System.out.println("【通过】copy 方法正确复制数据");
    }
}
