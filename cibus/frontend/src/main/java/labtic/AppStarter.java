package labtic;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/Login.fxml"));

        loader.setControllerFactory(AppStarter.getContext()::getBean);
        primaryStage.getIcons().add(new Image("labtic/ui/logo cibus.png"));
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
