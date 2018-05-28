package com.github.developframework.mock;

import com.github.developframework.mock.random.RandomGeneratorRegistry;
import lombok.Getter;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * 客户端
 *
 * @author qiuzhenhao
 *
 * @since 0.1
 */
public class MockClient {

    @Getter
    private RandomGeneratorRegistry randomGeneratorRegistry = new RandomGeneratorRegistry();

    public String mock(String template) {
        MockTask mockTask = new MockTask(randomGeneratorRegistry, template);
        return mockTask.run();
    }

    public List<String> mock(String template, int quantity) {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < quantity; i++) {
            list.add(mock(template));
        }
        return list;
    }
    /**
     * 从输入流构建
     * @param inputStream 输入流
     * @param charset 编码
     * @return 生成的随机值
     * @throws IOException IO异常
     */
    public String mock(InputStream inputStream, Charset charset) throws IOException {
        StringBuffer templateBuffer = new StringBuffer();
        IOUtils.readLines(inputStream, charset).forEach(line -> templateBuffer.append(line).append('\n'));
        return mock(templateBuffer.toString());
    }

    /**
     * 从输入流批量构建
     * @param inputStream 输入流
     * @param charset 编码
     * @param quantity 数量
     * @return 生成的随机值
     * @throws IOException IO异常
     */
    public List<String> mock(InputStream inputStream, Charset charset, int quantity) throws IOException {
        StringBuffer templateBuffer = new StringBuffer();
        IOUtils.readLines(inputStream, charset).forEach(line -> templateBuffer.append(line).append('\n'));
        List<String> list = new LinkedList<>();
        for (int i = 0; i < quantity; i++) {
            list.add(mock(templateBuffer.toString()));
        }
        return list;
    }
}
