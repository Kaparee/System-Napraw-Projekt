package pl.naprawy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.naprawy.model.Client;
import pl.naprawy.model.Company;
import pl.naprawy.service.NewAccountService;
import pl.naprawy.util.AlertUtil;

import java.util.List;

public class NewAccountController extends BaseController{
    @FXML private Label nameLabel, usernameLabel, emailLabel, phoneLabel, passwordLabel;
    @FXML private TextField nameField, usernameField, emailField, phoneField;
    @FXML private PasswordField passwordField;
    @FXML private TableView<Company> tableView;
    @FXML private TableColumn<Company, String> nameColumn, addressColumn;
    @FXML private Button returnButton, newAccount;

    @FXML
    public void initialize(){
        newAccountService = new NewAccountService();
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        setCompaniesInfo();
    }

    public void setCompaniesInfo(){
        List<Company> companiesList = newAccountService.getAllCompany();
        tableView.setItems(FXCollections.observableArrayList(companiesList));
    }

    @FXML
    private void createNewAccount(){
        if (valiAll() && getSelectedCompany()!=null){
            try {
                Client client = new Client(nameField.getText(), phoneField.getText(), emailField.getText());
                newAccountService.createNewClient(client, getSelectedCompany());
                newAccountService.createNewAccount(client, usernameField.getText(), passwordField.getText());
                nameField.setText(null);
                usernameField.setText(null);
                emailField.setText(null);
                phoneField.setText(null);
                passwordField.setText(null);
                AlertUtil.informationAlert("Konto dla "+nameField.getText()+" zostało poprawnie utworzone");
            } catch (Exception e) {
                AlertUtil.errorAlert("Wystąpił błąd podczas tworzenia konta.");
                e.printStackTrace();
            }
        }
    }

    private Long getSelectedCompany(){
        Company selectedCompany = tableView.getSelectionModel().getSelectedItem();
        if (selectedCompany!=null){
            return selectedCompany.getId();
        } else {
            return null;
        }
    }

    private boolean valiAll() {
        if (!isValidName(nameField.getText().trim())) {
            AlertUtil.errorAlert("Nieporawne imię i nazwisko");
            return false;
        }
        if (!isValidUsername(usernameField.getText().trim())) {
            AlertUtil.errorAlert("Niepoprawna nazwa użytkownika");
            return false;
        }
        if (!isValidEmail(emailField.getText().trim())) {
            AlertUtil.errorAlert("Nieporawny email");
            return false;
        }
        if (!isValidPhone(phoneField.getText().trim())) {
            AlertUtil.errorAlert("Nieporawny numer telefonu");
            return false;
        }
        if (!isValidPassword(passwordField.getText().trim())) {
            AlertUtil.errorAlert("Niepoprawne hasło");
            return false;
        }
        return true;
    }

    private boolean isValidName(String name){
        String regex = "^[A-Za-ząćęłńóśźżĄĆĘŁŃÓŚŹŻ]+ [A-Za-ząćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$";
        if (name != null && name.matches(regex)){
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidUsername(String username){
        String regex = "^[A-Za-ząćęłńóśźżĄĆĘŁŃÓŚŹŻ]+\\.[A-Za-ząćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$";
        if (username.length() >= 3 && username.matches(regex)){
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidEmail(String email){
        String regex = "^[A-Za-ząćęłńóśźżĄĆĘŁŃÓŚŹŻ]+\\.[A-Za-ząćęłńóśźżĄĆĘŁŃÓŚŹŻ]+@napraw\\.io\\.pl$";
        if (email != null && email.matches(regex)){
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidPhone(String phone){
        String regex = "^\\+48 [0-9]{3} [0-9]{3} [0-9]{3}$";
        if (phone != null && phone.matches(regex)){
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidPassword(String password){
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,}$";
        if (password!= null && password.matches(regex)){
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void returnToLoginScene(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Login-scene.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas wylogowywania.\nSpróbuj ponownie później.");
        }
    }
}

