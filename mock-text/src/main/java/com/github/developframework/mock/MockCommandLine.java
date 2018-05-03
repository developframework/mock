package com.github.developframework.mock;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qiuzhenhao
 */
public class MockCommandLine {

    public static void main(String[] args) {
        MockClient mockClient = new MockClient();
        Map<String, String> parameters = readCommandLineParameters(args);
        String template = parameters.get("template");
        int size = Integer.parseInt(parameters.getOrDefault("size", "1"));
        for (int i = 0; i < size; i++) {
            String data = mockClient.mock(template);
            System.out.println(data);
        }
    }

    private static Map<String, String> readCommandLineParameters(String[] args) {
        if(args.length == 0) {
            System.out.println("please input template string.");
            System.exit(0);
        }

        Map<String, String> parameters = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-f":
                case "--file": {
                    try {
                        parameters.put("template", args[i+1]);
                    } catch(IndexOutOfBoundsException e) {
                        System.out.printf("%s argument error\n", args[i]);
                    }
                    i++;
                }break;
                case "-s":
                case "--size": {
                    try {
                        Integer.parseInt(args[i+1]);
                        parameters.put("size", args[i+1]);
                    } catch(IndexOutOfBoundsException | NumberFormatException e) {
                        System.out.printf("%s argument error\n", args[i]);
                    }
                    i++;
                }break;
            }

        }
        if (!parameters.containsKey("template")) {
            parameters.put("template", args[0]);
        }
        return parameters;
    }
}
