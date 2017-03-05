package ru.levelp;

import java.util.ArrayList;
import java.util.List;

public class FileNameGenerator {
    private int index;
    private String fileName;
    private SerializationType type;
    private List<String> names;

    public FileNameGenerator(String fileName, SerializationType type) {
        this.fileName = fileName;
        this.type = type;
        this.names = new ArrayList<>();
    }

    public String nextFileName(){
        String format = String.format("%s_%d.%s", fileName, index++, type.getExtension());
        names.add(format);
        return format;
    }

    public String[] getAllNames(){
        return names.stream().toArray(String[]::new);
    }
}
