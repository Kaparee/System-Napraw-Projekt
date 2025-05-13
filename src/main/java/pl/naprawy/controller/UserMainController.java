package pl.naprawy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import pl.naprawy.model.Client;
import pl.naprawy.model.Company;
import pl.naprawy.service.ClientService;
import pl.naprawy.service.CompanyService;

public class UserMainController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label companyLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextArea serialnumberArea;
    @FXML
    private Button sendButton;
    @FXML
    private Button clearButton;

    private ClientService clientService;
    private CompanyService companyService;

    @FXML
    public void initialize() {
        if (nameLabel != null) {
            nameLabel.setText("Witaj!");
        } else {
            System.out.println("nameLabel jest null!");
        }
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public UserMainController(ClientService clientService, CompanyService companyService) {
        this.clientService = clientService;
        this.companyService = companyService;
    }


    public UserMainController() {

    }
    public void setClientInfo(String username) {
        Client client = clientService.getClientByLogin(username);
        Company company = companyService.getCompanyById(client.getCompany_id());

        if (client != null) {
            System.out.println("Znaleziono klienta: " + client.getName() +" "+ client.getEmail() +" "+ client.getPhone() +" "+ client.getCompany_id() +" "+company.getName());
            String fullName = client.getName();
            String[] nameParts = fullName.split(" ");

            nameLabel.setText(nameParts[0]);
            companyLabel.setText(company.getName());
            surnameLabel.setText(nameParts[1]);
            phoneLabel.setText(client.getPhone());
            emailLabel.setText(client.getEmail());
        } else {
            System.out.println("Nie znaleziono klienta dla tego loginu.");
        }
    }


    @FXML
    public void sendForm() {
        // do zrobienia
    }

    @FXML
    public void clearForm() {
        // do zrobienia
    }
}