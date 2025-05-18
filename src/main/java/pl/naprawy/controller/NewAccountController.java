package pl.naprawy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.naprawy.model.Company;

public class NewAccountController extends BaseController{
    @FXML
    private Label nameLabel, usernameLabel, emailLabel, phoneLabel, passwordLabel;
    @FXML
    private TextField nameField, usernameField, emailField, phoneField;
    @FXML
    private PasswordField passwordField;
    @FXML private TableView<Company> tableView;
    @FXML private TableColumn<Company, String> companyNameColumn, companyAddressColumn;
}
