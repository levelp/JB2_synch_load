package ru.serialization;

import ru.levelp.*;

import java.util.List;

/**
 * Created by asolodkaya on 05.03.17.
 */
public class SaverTask implements Runnable {

    private String name;
    private ContactBook book;
    private String fileName;
    private List<Contact> contactList;

    public SaverTask(ContactBook book, String fileName, List<Contact> contactList) {
        this.name = "saver-" + fileName;
        this.book = book;
        this.fileName = fileName;
        this.contactList = contactList;
    }

    @Override
    public void run() {
        System.out.println("Task " + name + " is running... ");
        book.save(fileName, contactList);
        System.out.println("Task " + name + " is finished!");
    }
}
