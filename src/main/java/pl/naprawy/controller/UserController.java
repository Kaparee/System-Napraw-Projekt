package pl.naprawy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.naprawy.model.*;
import java.sql.Timestamp;

public class UserController extends BaseController {

    @FXML private Label nameLabel, companyLabel, surnameLabel, phoneLabel, emailLabel, companyAddressLabel;
    @FXML private TextArea descriptionArea, serialnumberArea;
    @FXML private Button sendButton, clearButton;

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
        order.setClient_id(client.getId());
        order.setCompany_id(company.getId());
        order.setDevice_id(device.getId());
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

}
