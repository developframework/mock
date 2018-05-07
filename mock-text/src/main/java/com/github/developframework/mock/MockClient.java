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
 * @author qiuzhenhao
 */
public class MockClient {

    @Getter
    private RandomGeneratorRegistry randomGeneratorRegistry = new RandomGeneratorRegistry();

    public String mock(String template) {
        MockTask mockTask = new MockTask(randomGeneratorRegistry, template);
        return mockTask.run();
    }

    public List<String> mock(String template, int size) {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(mock(template));
        }
        return list;
    }
    /**
     * 从输入流构建
     * @param inputStream
     * @param charset
     * @return
     * @throws IOException
     */
    public String mock(InputStream inputStream, Charset charset) throws IOException {
        StringBuffer templateBuffer = new StringBuffer();
        IOUtils.readLines(inputStream, charset).forEach(line -> templateBuffer.append(line).append('\n'));
        return mock(templateBuffer.toString());
    }

    /**
     * 从输入流批量构建
     * @param inputStream
     * @param charset
     * @param quantity
     * @return
     * @throws IOException
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
