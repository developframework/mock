package com.github.developframework.mock;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author qiuzhenhao
 */
public class MockData {

    @Getter
    private List<String> data = new LinkedList<>();

    public void addData(String value) {
        data.add(value);
    }

    public void print() {
        data.forEach(System.out::println);
    }

}
