package pl.naprawy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.naprawy.model.Device;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;
import pl.naprawy.service.UserStatusService;
import pl.naprawy.util.DateFormatterUtil;
import pl.naprawy.util.ServiceInjector;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserStatusController extends UserController {
    @FXML
    private Button reportButton, deleteButton;
    @FXML
    private TableView<RepairOrder> tableView;
    @FXML
    private TableColumn<RepairOrder, String> createdColumn, updatedColumn, descriptionColumn, technicianColumn, deviceColumn, statusColumn;
    @FXML
    private Label createdLabel, updatedLabel, descriptionLabel, technicianLabel, deviceLabel, statusLabel;

    @Override
    @FXML
    public void setClientInfo(String username) {
        super.setClientInfo(username);
        showInformation();
    }

    @FXML
    public void initialize(){
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        createdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateFormatterUtil.format(cellData.getValue().getCreated_at())));
        updatedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateFormatterUtil.format(cellData.getValue().getUpdated_at())));
        technicianColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getTechnicianInfo(cellData.getValue())));
        deviceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDevice().getModel()));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        showInformationInSquare();
    }

    private String getTechnicianInfo(RepairOrder order) {
        Technician technician = order.getTechnician();
        if (technician == null) {
            return "Brak przypisanego technika";
        }
        return technician.getName()+"\n "+technician.getEmail();
    }

    @FXML
    public void onReportButtonClicked(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Main-scene-user.fxml"));
        try {
            Parent root = fxmlLoader.load();
            UserController userController = fxmlLoader.getController();

            ServiceInjector.injectAllServices(userController);

            userController.setUsername(username);
            userController.setClientInfo(username);

            Stage stage = (Stage) reportButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showInformation(){
        List<RepairOrder> orders = userStatusService.getUserOrderStatus(getClient().getId());
        tableView.setItems(FXCollections.observableArrayList(orders));
    }

    public void showInformationInSquare() {
        tableView.setOnMouseClicked(event -> {
            RepairOrder selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String infoTechnician = getTechnicianInfo(selected);

                createdLabel.setText(DateFormatterUtil.format(selected.getCreated_at()));
                updatedLabel.setText(DateFormatterUtil.format(selected.getUpdated_at()));
                descriptionLabel.setText(selected.getDescription());
                technicianLabel.setText(infoTechnician);
                deviceLabel.setText(selected.getDevice().getBrand() +" "+ selected.getDevice().getModel());
                statusLabel.setText(selected.getStatus());
            }
        });
    }

    @FXML
    public void deleteRaport(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy na pewno chcesz usunąć swoje zgłoszenie?");
        alert.setContentText("Tej operacji nie można cofnąć!.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.OK) {
            RepairOrder selected = tableView.getSelectionModel().getSelectedItem();
            userStatusService.deleteUserOrder(selected.getId());
            showInformation();
            createdLabel.setText("");
            updatedLabel.setText("");
            descriptionLabel.setText("");
            technicianLabel.setText("");
            deviceLabel.setText("");
            statusLabel.setText("");
        }
    }
}
