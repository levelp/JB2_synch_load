import ru.levelp.LoaderTest;
import ru.levelp.SerializationType;
import serialization.ConcreteAsyncLoader;

/**
 * Created by asolodkaya on 05.03.17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConcreteAsyncLoader loader = new ConcreteAsyncLoader("test", SerializationType.JSON, 100);
        LoaderTest generator = new LoaderTest(1000, 243, 1000, loader);
        generator.start();
    }
}
