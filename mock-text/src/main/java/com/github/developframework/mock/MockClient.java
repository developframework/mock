package com.github.developframework.mock;

import lombok.Getter;
import lombok.NonNull;
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
    protected RandomGeneratorRegistry randomGeneratorRegistry = new RandomGeneratorRegistry();

    protected MockCache mockCache = new MockCache();

    /**
     * 清空缓存
     */
    public void clearCache() {
        mockCache.clear();
    }

    /**
     * 依据模板填充随机值
     * @param template 模板
     * @return 随机值字符串
     */
    public String mock(@NonNull String template) {
        MockTask mockTask = new MockTask(randomGeneratorRegistry, mockCache, template);
        return mockTask.run();
    }

    /**
     * 依据模板批量填充随机值
     * @param template 模板
     * @param quantity 数量
     * @return 随机值字符串列表
     */
    public List<String> mock(@NonNull String template, int quantity) {
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
