package ru.levelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LoaderTest {

    private long period;
    private long amount;
    private long limit;
    private long alreadyGenerated;
    private AsyncLoader loader;
    private Timer timer;
    private MockData data;
    private List<Contact> history;

    public LoaderTest(long period, long amount, long limit, AsyncLoader loader) {
        this.period = period;
        this.amount = amount;
        this.limit = limit;
        this.loader = loader;
        this.alreadyGenerated = 0;
        this.timer = new Timer(false);
        this.data = new MockData();
        this.history = new ArrayList<>();
    }

    public LoaderTest(AsyncLoader loader) {
        this(1000, 243, 1000, loader);
    }


    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<Contact> generate = generate(amount);
                alreadyGenerated += generate.size();
                loader.add(generate);
                if (alreadyGenerated >= limit) {
                    loader.saveAll();
                    timer.cancel();
                    timer.purge();
                    try {
                        loader.join();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    check();
                }
            }
        };
        this.timer.schedule(task, 0, period);
    }

    private List<Contact> generate(long amount) {
        List<Contact> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Contact contact = new Contact(data.generateName(), data.generatePhones());
            list.add(contact);
            history.add(contact);
        }
        return list;
    }

    public void check(){
        List<Contact> contacts = new ArrayList<>(loader.getAllOrdered());
        System.out.println("Checking resulting files...");
        if(history.equals(contacts)){
            System.out.println("[OK] All files are correctly ordered.");
        }else{
            System.out.println("[FAIL] (see differences above): ");
            for (int i = 0; i < history.size(); i++) {
                if(!history.get(i).equals(contacts.get(i))) {
                    System.out.printf("at index #%d:%n", i);
                    System.out.println(history.get(i));
                    System.out.println(contacts.get(i));
                    System.out.println("--------------------------");
                }
            }
        }
    }

}
