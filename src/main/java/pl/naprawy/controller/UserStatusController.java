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
import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.DateFormatterUtil;
import pl.naprawy.util.ServiceInjector;
import java.util.List;
import java.util.Optional;

public class UserStatusController extends UserController {
    @FXML private Button reportButton, deleteButton;
    @FXML private TableView<RepairOrder> tableView;
    @FXML private TableColumn<RepairOrder, String> createdColumn, updatedColumn, descriptionColumn, technicianColumn, deviceColumn, statusColumn;
    @FXML private Label createdLabel, updatedLabel, descriptionLabel, technicianLabel, deviceLabel, statusLabel;

    @Override
    @FXML
    public void setClientInfo(String username) {
        super.setClientInfo(username);
        showInformation();
    }

    @FXML
    public void initialize(){
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
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
        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas ładowania strony.\nSpróbuj ponownie później.");
        }
    }

    @FXML
    public void showInformation(){
        List<RepairOrder> orders = repairOrderService.getUserOrderStatus(getClient().getId());
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
        RepairOrder selected = tableView.getSelectionModel().getSelectedItem();
        if (selected.getStatus().equals("Nowy")) {
            Optional<ButtonType> result = AlertUtil.confirmAlert("Usunięcie zgłoszenia");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                repairOrderService.deleteUserOrder(selected.getId());
                showInformation();
                createdLabel.setText("");
                updatedLabel.setText("");
                descriptionLabel.setText("");
                technicianLabel.setText("");
                deviceLabel.setText("");
                statusLabel.setText("");
            }
        } else {
            AlertUtil.informationAlert("Nie można usunąć zgłoszenia, które jest już rozpatrywane lub zakończone.");
        }
    }
    @FXML
    private void onExportClicked() {
        try {
            userExportService.exportFile(clientService.getClientByLogin(username), repairOrderService.getUserOrderStatus(getClient().getId()));
            AlertUtil.informationAlert("Pomyślnie pobrano dane\nKliknij OK aby wyłączyć okno");
        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych.\nSpróbuj ponownie później.");
        }
    }
}
