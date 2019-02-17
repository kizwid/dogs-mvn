package sandkev.dogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;

public class ApplicationMain {

    public static void main(String[] args) {

        AppConfigFactory appConfigFactory = new AppConfigFactory();
        AppConfig appConfig = appConfigFactory.create();

        Logger logger = LoggerFactory.getLogger(ApplicationMain.class);
        SparkUtils.createServerWithRequestLog(logger);

        get("/hello", (request, response) -> "world");
        logger.info("hello");

        MockDogProvider dogProvider = new MockDogProvider();
        DogsController dogsController = DogsController.of(dogProvider);
        dogsController.register();


    }

}