package com.aluracursos.screenmatch.main;

import java.util.Arrays;
import java.util.List;

public class ExampleStreams {
    public void showExample() {
        List<String> names = Arrays.asList("Brenda", "Luis", "Maria Fernanda", "Eric", "Genesis");

        names.stream()
                .sorted()
                .limit(4)
                .filter(n -> n.startsWith("E"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }
}
