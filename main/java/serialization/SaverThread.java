package serialization;

import ru.levelp.Contact;
import ru.levelp.ContactBook;

import java.util.List;

/**
 * Created by asolodkaya on 05.03.17.
 */
public class SaverThread extends Thread {

    private ContactBook book;
    private String fileName;
    private List<Contact> contactList;

    public SaverThread(ContactBook book, String fileName, List<Contact> contactList) {
        super("saver-" + fileName);
        this.book = book;
        this.fileName = fileName;
        this.contactList = contactList;
    }

    @Override
    public void run() {
        System.out.println("Thread " + this.getName() + " is running... ");
        book.save(fileName, contactList);
        System.out.println("Thread " + this.getName() + " is finished!");
    }
}
