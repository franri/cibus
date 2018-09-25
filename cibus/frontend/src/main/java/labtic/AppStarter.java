package labtic;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AppStarter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = SpringApplication.run(AppStarter.class);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/searchpage.fxml"));
        loader.setControllerFactory(context::getBean);

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

//        ApplicationContext context = new AnnotationConfigApplicationContext(labtic.AppSpringConfig.class);
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/searchpage.fxml"));
//        loader.setControllerFactory(context::getBean);
//        Parent root = loader.load();
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
