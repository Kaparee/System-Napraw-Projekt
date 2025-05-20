package pl.naprawy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.ServiceInjector;

public class MainController extends BaseController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton, newAccount;

    @FXML
    private void initialize() {
        ServiceInjector.injectAllServices(this);
    }

    @FXML
    private void onLoginClicked() {
        String username = usernameField.getText().toLowerCase();
        String password = passwordField.getText();
        try {
            int role = loginService.verifyLogin(username, password);
            switch (role) {
                case 1 -> openUserPanel(username);
                case 2 -> openTechnicianPanel(username);
                default -> AlertUtil.errorAlert("Wystąpił błąd podczas logowania.\nPodano błędne dane lub konto nie istnieje!");
            }
        } catch (Exception e){
            AlertUtil.errorAlert("Wystąpił błąd podczas łączenia z bazą danych.\nSpróbuj ponownie później.");
            e.printStackTrace();
        }
    }

    @FXML
    private void createNewAccount(javafx.event.ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/New-account-scene.fxml"));
        try {
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas ładowania strony.\nSpróbuj ponownie później.");
            e.printStackTrace();
        }
    }

    private void openUserPanel(String username){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Main-scene-user.fxml"));
        try {
            Parent root = fxmlLoader.load();
            UserController userController = fxmlLoader.getController();
            ServiceInjector.injectAllServices(userController);
            userController.setUsername(username);
            userController.setClientInfo(username);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas ładowania strony.\nSpróbuj ponownie później.");
            e.printStackTrace();
        }
    }

    private void openTechnicianPanel(String username){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Main-scene-technician.fxml"));
        try {
            Parent root = fxmlLoader.load();
            TechnicianController technicianController = fxmlLoader.getController();
            ServiceInjector.injectAllServices(technicianController);
            technicianController.setUsername(username);
            technicianController.setTechnicianInfo(username);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas ładowania strony.\nSpróbuj ponownie później.");
            e.printStackTrace();
        }
    }
}
