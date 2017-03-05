import ru.serialization.MyAsyncLoader;
import ru.levelp.*;

/**
 * Created by asolodkaya on 05.03.17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyAsyncLoader loader = new MyAsyncLoader("test", SerializationType.JSON, 100);
        LoaderTest generator = new LoaderTest(1000, 243, 1000, loader);
        generator.start();
    }
}
