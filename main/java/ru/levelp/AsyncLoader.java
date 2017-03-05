package ru.levelp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AsyncLoader {

    protected final FileNameGenerator fileNameGenerator;
    protected final ContactBook contactBook;
    protected final int limit;
    protected final List<Contact> contacts;

    public AsyncLoader(String filePrefix, SerializationType serializationType, int limit) {
        this.fileNameGenerator = new FileNameGenerator(filePrefix, serializationType);
        contactBook = initializeContactBook(serializationType);
        this.limit = limit;
        contacts = new ArrayList<>();
    }

    protected abstract ContactBook initializeContactBook(SerializationType serializationType);

    /**
     * Этот метод должен добавить в список контактов данные (и начать выгрузку, если необходимо)
     */
    public abstract void add(Contact... contacts);

    /**
     * Этот метод должен добавить в список контактов данные (и начать выгрузку, если необходимо)
     */
    public abstract void add(List<Contact> contacts);


    /**
     * Этот метод должен сохранить все данные в соответствующие файлы
     */
    public abstract void saveAll();

    /**
     * Этот метод необходимо реализовать и использовать в своем алгоритме. На вход ему отдается имя
     * файла, в который производится запись. Он же должен создать отдельный записывающий поток, в котором
     * записать все данные в файл.
     */
    protected abstract void save(String fileName, List<Contact> toSave);

    /**
     * Этот метод должен позволить нам дождаться, когда наконец все потоки записи завершатся.
     * Подсказка: для этого можно, например, хранить массив всех запущенных потоков.
     */
    public abstract void join() throws Exception;


    public List<Contact> getAllOrdered(){
        List<Contact> all = new ArrayList<>();
        String[] allNames = fileNameGenerator.getAllNames();
        for (int i = 0; i < allNames.length; i++) {
            String name = allNames[i];
            List<Contact> load = contactBook.load(name);
            if (load.size() > limit) {
                System.out.println("[FAIL]: too much elements for one file: " + limit + " at file " + name);
            }else if(load.size() < limit && i < allNames.length - 1){
                System.out.println("[FAIL]: too few elements for one file: " + limit + " at file " + name);
            }
            all.addAll(load);
        }
        return all;
    }

}
