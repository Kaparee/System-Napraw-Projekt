package pl.naprawy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.naprawy.model.UserAccount;
import pl.naprawy.service.LoginService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button newAccount;

    private LoginService loginService;

    public LoginController(){
        loginService = new LoginService();
    }

    @FXML
    private void onLoginClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginService.verifyLogin(username, password) == 1) {
            System.out.println("Zalogowano pomyślnie!");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Main-scene-user.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (loginService.verifyLogin(username, password) == 2) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Main-scene-technician.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Błędna nazwa użytkownika lub hasło.");
        }
    }

    @FXML
    private void requestForAccount(){

    }
}