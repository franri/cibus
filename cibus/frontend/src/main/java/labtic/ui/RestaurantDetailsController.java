package labtic.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import entities.Food;
import entities.Neighbourhood;
import entities.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import labtic.AppStarter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rmi.BackendService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
@Data
public class RestaurantDetailsController implements Initializable {

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField rutField;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private JFXTextField maxCapacity;

    @FXML
    private JFXTextArea errorLabel;

    @FXML
    private JFXTextField tableOfTwo;

    @FXML
    private JFXTextField tableOfFour;

    @FXML
    private MenuButton listaComidas;

    @FXML
    private ComboBox<Neighbourhood> listaBarrios;

    @FXML
    private JFXButton closeSession;

    @FXML
    private JFXTimePicker horarioApertura;

    @FXML
    private JFXTimePicker horarioCierre;

    private Restaurant restaurant;

    @FXML
    private Label fileLocation;

    @FXML
    private JFXButton imageLoader;

    private File profilePicture;

    @Autowired
    private BackendService bs;

    @FXML
    void goBack(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("Login.fxml"));
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Neighbourhood> barrios = null;
        List<Food> comidas = null;

        nameField.setText(restaurant.getName());
        emailField.setText(restaurant.getEmail());
        rutField.setText(restaurant.getRut());
        phone.setText((restaurant.getPhoneNumber()==null) ? null : restaurant.getPhoneNumber().toString());
        String address = (restaurant.getAddress()==null || restaurant.getAddress().isEmpty()) ? null : restaurant.getAddress();
        this.address.setText(restaurant.getAddress());

        try {
            comidas = bs.getListaComidas();
            barrios = bs.getListaBarrios();
        } catch (RemoteException e) {
            errorLabel.setText("Error en conexión al servidor");
            errorLabel.setVisible(true);
        }

        listaBarrios.setCellFactory(new Callback<ListView<Neighbourhood>,ListCell<Neighbourhood>>(){
            @Override
            public ListCell<Neighbourhood> call(ListView<Neighbourhood> l){
                return new ListCell<Neighbourhood>(){
                    @Override
                    protected void updateItem(Neighbourhood item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                } ;
            }
        });
        listaBarrios.getItems().addAll(barrios);
        listaBarrios.setValue(restaurant.getNeighbourhood());
        listaBarrios.setConverter(new StringConverter<Neighbourhood>(){

            @Override
            public String toString(Neighbourhood object) {
                return object == null ? null : object.getName();
            }

            @Override
            public Neighbourhood fromString(String string) {
                return listaBarrios.getItems().stream().filter(i -> i.getName().equals(string)).findAny().orElse(null);
            }

        });

        ObservableList<CustomMenuItem> comidasToAdd = FXCollections.observableArrayList();
        comidas.forEach(item -> {
            CheckBox checkBox = new CheckBox(item.getName());
            checkBox.setUserData(item);
            if(restaurant.getFoods().contains(item)){
            checkBox.setSelected(true);
            }
            CustomMenuItem customMenuItem = new CustomMenuItem(checkBox, false);
            comidasToAdd.add(customMenuItem);
        });
        listaComidas.getItems().addAll(comidasToAdd);

        if(restaurant.getOpeningHour()!=null) horarioApertura.setValue(restaurant.getOpeningHour());
        if(restaurant.getClosingHour()!=null) horarioCierre.setValue(restaurant.getClosingHour());
        if(restaurant.getMaxCapacity()!=null) maxCapacity.setText(restaurant.getMaxCapacity().toString());
        if(restaurant.getTableForTwo()!=null) tableOfTwo.setText(restaurant.getTableForTwo().toString());
        if(restaurant.getTableForFour()!=null) tableOfFour.setText(restaurant.getTableForFour().toString());
    }

    @FXML
    public void loadImage(){
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fc.getExtensionFilters().addAll(imageFilter);
        profilePicture = fc.showOpenDialog(AppStarter.getMainStage());
        if(profilePicture != null) {
            fileLocation.setText(profilePicture.getPath());
        }
    }


    @FXML
    public void fillDataOfRestaurant(MouseEvent event) throws IOException {
        if(address.getText() == null || address.getText().isEmpty()) {
            errorLabel.setText("Ingrese dirección");
            errorLabel.setVisible(true);
            return;
        }else{
            restaurant.setAddress(address.getText());
        }

        if(phone.getText() == null || phone.getText().isEmpty()) {
            errorLabel.setText("Ingrese numero de telefono");
            errorLabel.setVisible(true);
            return;
        }else{

            try {
                long Telefonoparce = Long.parseLong(phone.getText());
            } catch(NumberFormatException e) {
                errorLabel.setText("solo puede ingresar numeros eneteros en el numero de telefono");
                errorLabel.setVisible(true);
                return;
            }

            restaurant.setPhoneNumber(Long.parseLong(phone.getText()));
        }


        LocalTime horaAbre;
        if(horarioApertura.getValue() != null) {
            horaAbre = horarioApertura.getValue();
        }else{
            errorLabel.setText("Elija horario de apertura");
            errorLabel.setVisible(true);
            return;
        }
        restaurant.setOpeningHour(horaAbre);

        LocalTime horaCierra;
        if(horarioCierre.getValue() != null) {
            horaCierra = horarioCierre.getValue();
        }else{
            errorLabel.setText("Elija horario de cierre");
            errorLabel.setVisible(true);
            return;
        }
        restaurant.setClosingHour(horaCierra);

        List<Food> comidas = new ArrayList<>();
        for (MenuItem item : listaComidas.getItems()){
            CheckBox deItem = (CheckBox) ((CustomMenuItem) item).getContent();
            if(deItem.isSelected()){
                comidas.add((Food)deItem.getUserData());
            }
        }
        if(comidas.isEmpty()){
            errorLabel.setText("Seleccione por lo menos una comida");
            errorLabel.setVisible(true);
            return;
        }else{
            errorLabel.setVisible(false);
        }
        restaurant.setFoods(comidas);

        try{
            restaurant.setMaxCapacity(Long.parseLong(maxCapacity.getText()));
            restaurant.setTableForFour(Long.parseLong(tableOfFour.getText()));
            restaurant.setTableForTwo(Long.parseLong(tableOfTwo.getText()));
        }catch (NumberFormatException e){
            errorLabel.setText("Ingrese número entero");
            errorLabel.setVisible(true);
            return;
        }

        try{
            restaurant.setMaxCapacity(Long.parseLong(maxCapacity.getText()));
            restaurant.setTableForFour(Long.parseLong(tableOfFour.getText()));
            restaurant.setTableForTwo(Long.parseLong(tableOfTwo.getText()));
            if(!restaurant.isCanBeShown()){
                restaurant.setFreePlaces(Long.parseLong(maxCapacity.getText()));
            }
        }catch (NumberFormatException e){
            errorLabel.setText("Ingrese números enteros en los campos correspondientes");
            errorLabel.setVisible(true);
            return;
        }

        Neighbourhood barrio = listaBarrios.getSelectionModel().getSelectedItem();

        if(barrio==null){
            errorLabel.setText("Seleccione barrio");
            errorLabel.setVisible(true);
            return;
        }else{errorLabel.setVisible(false);}

        restaurant.setNeighbourhood(barrio);

        if(profilePicture == null) { //hay imagen seleccionada?
            if (restaurant.getProfilePicture() == null) { //NO. Ahora, hay imagen guardada?
                errorLabel.setText("Seleccione foto"); //NO. Pedir que elija
                errorLabel.setVisible(true);
                return;
            }
            //SI. Ya tiene elegida, no tiene por qué elegir otra
        } else {
            BufferedImage bufferedPic = ImageIO.read(profilePicture);
            ByteArrayOutputStream picStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedPic, "jpg", picStream);
            byte[] bytedProfPicture = picStream.toByteArray();
            restaurant.setProfilePicture(bytedProfPicture);
        }

        restaurant.setCanBeShown(true);

        bs.saveRestaurant(restaurant);

        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(LoginController.class.getResource("RestaurantMainPage.fxml"));
        RestaurantMainPageController controller = AppStarter.getContext().getBean(RestaurantMainPageController.class);
        controller.setRestaurant(restaurant);
        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }

    @FXML
    public void goToReservationsPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(AppStarter.getContext()::getBean);
        loader.setLocation(RestaurantMainPageController.class.getResource("RestaurantMainPage.fxml"));
        RestaurantMainPageController controller = AppStarter.getContext().getBean(RestaurantMainPageController.class);
        controller.setRestaurant(restaurant);

        Parent root = loader.load();
        AppStarter.getMainStage().setScene(new Scene(root));
        AppStarter.getMainStage().show();
    }

    @FXML
    public void handleEnterPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            fillDataOfRestaurant(null);
            goToReservationsPage();
        }
    }


}