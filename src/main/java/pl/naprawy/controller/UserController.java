package pl.naprawy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.naprawy.model.*;
import pl.naprawy.util.ServiceInjector;

import java.io.IOException;
import java.sql.Timestamp;

public class UserController extends BaseController {

    @FXML private Label nameLabel, companyLabel, surnameLabel, phoneLabel, emailLabel, companyAddressLabel;
    @FXML private TextArea descriptionArea, serialnumberArea;
    @FXML private Button sendButton, clearButton, statusButton, exportButton, logoutButton;

    public void setClientInfo(String username) {
        Client client = getClient();
        Company company = getCompany(client);

        if (client != null && company != null) {
            String[] nameParts = client.getName().split(" ");
            nameLabel.setText(nameParts[0]);
            surnameLabel.setText(nameParts.length > 1 ? nameParts[1] : "");
            companyLabel.setText(company.getName());
            phoneLabel.setText(client.getPhone());
            emailLabel.setText(client.getEmail());
            companyAddressLabel.setText(company.getAddress());
        }
    }

    @FXML
    private void sendForm(ActionEvent event) {
        Client client = getClient();
        if (client == null) {
            System.out.println("Brak klienta");
            return;
        }

        Company company = getCompany(client);
        if (company == null) {
            System.out.println("Brak firmy");
            return;
        }

        String serial = serialnumberArea.getText();
        Device device = deviceService.getDeviceInfo(serial);
        if (device == null) {
            System.out.println("Brak urządzenia");
            return;
        }

        String description = descriptionArea.getText();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        RepairOrder order = new RepairOrder();
        order.setClient(client);
        order.setCompany(company);
        order.setDevice(device);
        order.setDescription(description);
        order.setCreated_at(now);
        order.setUpdated_at(now);
        order.setStatus("Nowy");

        repairOrderService.sendRepairOrder(order);
        System.out.println("Zgłoszenie wysłane");
        descriptionArea.setText("");
        serialnumberArea.setText("");
    }

    @FXML
    private void clearForm(ActionEvent event) {
        descriptionArea.setText("");
        serialnumberArea.setText("");
    }

    @FXML
    private void onStatusButtonClicked(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Status-scene-user.fxml"));
        try {
            Parent root = fxmlLoader.load();
            UserController userController = fxmlLoader.getController();

            ServiceInjector.injectAllServices(userController);

            userController.setUsername(username);
            userController.setClientInfo(username);

            Stage stage = (Stage) statusButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onExportClicked(){

    }

    @FXML
    private void onLogoutClicked(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Login-scene.fxml"));
        try {
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
