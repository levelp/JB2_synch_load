package ru.serialization;

import ru.levelp.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by asolodkaya on 05.03.17.
 */
public class MyAsyncLoader extends AsyncLoader {


    private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    public MyAsyncLoader(String filePrefix, SerializationType serializationType, int limit) {
        super(filePrefix, serializationType, limit);
    }

    protected ContactBook initializeContactBook(SerializationType serializationType) {
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
        service.submit(new SaverTask(contactBook, fileName, toSave));
    }

    public void join() throws InterruptedException {
        service.shutdown();
        while(!service.awaitTermination(1, TimeUnit.SECONDS)){
            System.out.println("Waiting for termination....");
        }
    }


}
