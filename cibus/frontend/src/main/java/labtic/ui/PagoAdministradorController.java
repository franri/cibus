package labtic.ui;

import entities.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PagoAdministradorController {

    @FXML
    private ListView<Restaurant> listaRestaurantesQuePagan;

    @FXML
    private ImageView backArrow;

    @FXML
    void goBack(MouseEvent event) {

    }

}

