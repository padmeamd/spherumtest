package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        loadProperTies(args);
         SpringApplication.run(DemoApplication.class, args);
    }

    private static void loadProperTies(String[] args) {
        if (args.length == 0) {
            printInfo();
            System.exit(1);
        }
        StringBuilder sb = new StringBuilder("arg");
        for (int i = 0; i < args.length; i++) {
            System.setProperty(sb.append(i).toString(), args[i]);
            sb.setLength(3);
        }
    }

    private static void printInfo() {
        System.out.println("Application startup example: java -jar application.jar /path/to/file/input-data.json /path/to/file/log-file.log");
        System.out.println("Application arguments:\n" +
                "Input data (Required): [/path/to/file/filename].json\n" +
                "Log file: [/path/to/file/filename]"
        );
    }
}
