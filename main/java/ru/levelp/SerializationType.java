package ru.levelp;

/**
 * Created by asolodkaya on 05.03.17.
 */
public enum SerializationType {
    BINARY("ser"), JSON("json"), OTHER("txt");

    private String extension;

    SerializationType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
