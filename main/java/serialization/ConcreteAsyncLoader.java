package serialization;

import ru.levelp.AsyncLoader;
import ru.levelp.Contact;
import ru.levelp.ContactBook;
import ru.levelp.SerializationType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by asolodkaya on 05.03.17.
 */
public class ConcreteAsyncLoader extends AsyncLoader {


    private final List<Thread> pool = new ArrayList<>();

    public ConcreteAsyncLoader(String filePrefix, SerializationType serializationType, int limit) {
        super(filePrefix, serializationType, limit);
    }

    protected ContactBook initializeContactBook(SerializationType serializationType){
        switch (serializationType) {
            case JSON:
                return new JsonContactBook();
            case BINARY:
            case OTHER:
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void add(Contact... contacts) {
        add(Arrays.asList(contacts));
    }

    public void add(List<Contact> contacts) {
        synchronized (this.contacts) {
            this.contacts.addAll(contacts);
        }
        trySave();
    }

    public void saveAll() {
        trySave();
        save(this.contacts);
    }

    protected void trySave() {
        List<List<Contact>> partsToSave;
        synchronized (this.contacts) {
            partsToSave = new ArrayList<>();
            int parts = this.contacts.size() / limit;
            for (int i = 0; i < parts; i++) {
                List<Contact> toSave;
                toSave = new ArrayList<>(contacts.subList(0, limit));
                partsToSave.add(toSave);
                contacts.removeAll(toSave);
            }
        }

        for (List<Contact> toSave : partsToSave) {
            save(toSave);
        }
    }

    private void save(List<Contact> toSave) {
        if (toSave.size() == 0) {
            return;
        }
        toSave = new ArrayList<>(toSave);
        String nextFileName;
        synchronized (fileNameGenerator) {
            nextFileName = fileNameGenerator.nextFileName();
        }
        save(nextFileName, toSave);
    }

    protected void save(String fileName, List<Contact> toSave) {
        Thread thread = new SaverThread(contactBook, fileName, toSave);
        pool.add(thread);
        thread.start();
    }

    public void join() throws InterruptedException {
        for (Thread thread : pool) {
            if(thread.isAlive()){
                thread.join();
            }
        }
    }


}
