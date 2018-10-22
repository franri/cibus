package labtic;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AppStarter extends Application {

    @Getter
    private static ApplicationContext context;

    @Getter
    private static Stage mainStage;

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(AppStarter.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        mainStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/CreateOrLogin.fxml"));

        loader.setControllerFactory(AppStarter.getContext()::getBean);

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);

//        ApplicationContext context = new AnnotationConfigApplicationContext(labtic.AppSpringConfig.class);
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/SearchPage.fxml"));
//        loader.setControllerFactory(context::getBean);
//        Parent root = loader.load();
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
