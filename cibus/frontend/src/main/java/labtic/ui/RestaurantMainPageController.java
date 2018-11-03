package labtic.ui;


import entities.Reservation;
import entities.Restaurant;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class RestaurantMainPageController {

    @FXML
    private Label nombreRestaurant;

    @FXML
    private ImageView settings;

        private class CustomListCell extends ListCell<Reservation> {
        private HBox content;
        private Text nombreComensal;
        private Text cantPersonas;
        private ImageView confirmarReserva;

        File file = new File("labtic/resources/labtic/ui/confirm.png");
        Image image = new Image(file.toURI().toString());



        public CustomListCell() {
            super();
            nombreComensal = new Text();
            cantPersonas = new Text();
            confirmarReserva = new ImageView(image);
            VBox vBoxName = new VBox();
            vBoxName.setAlignment(Pos.TOP_CENTER);
            vBoxName.getChildren().addAll(nombreComensal);
            VBox.setVgrow(nombreComensal, Priority.ALWAYS);
            VBox vBoxData = new VBox();
            vBoxData.getChildren().addAll(cantPersonas);
            vBoxData.setSpacing(10);
            vBoxData.setAlignment(Pos.TOP_CENTER);
            content = new HBox(vBoxName, vBoxData, confirmarReserva);
            HBox.setHgrow(vBoxName, Priority.ALWAYS);
            content.setSpacing(10);




        }
    }
}
