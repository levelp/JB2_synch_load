package ru.serialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.levelp.Contact;
import ru.levelp.ContactBook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asolodkaya on 05.03.17.
 */
public class JsonContactBook implements ContactBook {
    @Override
    public void save(String fileName, List<Contact> list) {
        Gson gson = new Gson();
        try (PrintWriter writer = new PrintWriter(fileName)) {
            String s = gson.toJson(list);
            writer.write(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> load(String fileName) {
        try {
            List<String> strings = Files.readAllLines(Paths.get(fileName));
            String json = strings.get(0);
            Gson gson = new Gson();
            List<Contact> data = gson.fromJson(json, new TypeToken<List<Contact>>() {
            }.getType());
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
