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
import pl.naprawy.model.*;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.ServiceInjector;
import java.sql.Timestamp;
import java.util.List;

public class EmployeeController extends BaseController {

    @FXML private Label nameLabel, companyLabel, surnameLabel, phoneLabel, emailLabel, companyAddressLabel;
    @FXML private TextArea descriptionArea;
    @FXML private TableView<Device> tableView;
    @FXML private TableColumn<Device, String> typeColumn, brandColumn, modelColumn;
    @FXML private Button sendButton, clearButton, statusButton, exportButton, logoutButton;

    @FXML
    public void initialize(){
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrand()));
        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel()));
    }

    public void setEmployeeInfo(String username) {
        Employee employee = getEmployee();
        if (employee == null) {
            AlertUtil.errorAlert("Nieprawidłowy użytkownik.");
            return;
        }
        Company company = getCompany(employee);
        if (employee != null && company != null) {
            String[] nameParts = employee.getName().split(" ");
            nameLabel.setText(nameParts[0]);
            surnameLabel.setText(nameParts.length > 1 ? nameParts[1] : "");
            companyLabel.setText(company.getName());
            phoneLabel.setText(employee.getPhone());
            emailLabel.setText(employee.getEmail());
            companyAddressLabel.setText(company.getAddress());
        }
        List<Device> devicesList = deviceService.getEmployeeDevice(getEmployee().getId());
        tableView.setItems(FXCollections.observableArrayList(devicesList));
    }

    @FXML
    private void sendForm(ActionEvent event) {
        Employee employee = getEmployee();
        if (employee == null) {
            AlertUtil.errorAlert("Nieprawidłowy pracownik.");
            return;
        }
        Company company = getCompany(employee);
        if (company == null) {
            AlertUtil.errorAlert("Nie jesteś przypisany do żadnej firmy.\nSkontaktuj się bezpośrednio z twoim przełożonym.");
            return;
        }

        String description = descriptionArea.getText();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        try {
            Long device = getSelectedDevice();
            if (device == null) {
                AlertUtil.errorAlert("Nie wybrano żadnego urządzenia.");
                return;
            }
            RepairOrder order = new RepairOrder();
            order.setEmployee(employee);
            order.setCompany(company);
            order.setDevice(deviceService.getDeviceInfo(device));
            order.setDescription(description);
            order.setCreated_at(now);
            order.setUpdated_at(now);
            order.setStatus("Nowy");

            repairOrderService.sendRepairOrder(order);
            descriptionArea.setText(null);
            tableView.getSelectionModel().clearSelection();
            AlertUtil.informationAlert("Zgłoszenie zostało wysłane\nKliknij OK aby wyłączyć okno");
        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas wysyłania zgłoszenia.");
        }
    }

    private Long getSelectedDevice(){
        Device selectedDevice = tableView.getSelectionModel().getSelectedItem();
        if (selectedDevice!=null){
            return selectedDevice.getId();
        } else {
            return null;
        }
    }

    @FXML
    private void clearForm(ActionEvent event) {
        descriptionArea.setText(null);
    }

    @FXML
    private void onStatusButtonClicked(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Status-scene-employee.fxml"));
        try {
            Parent root = fxmlLoader.load();
            EmployeeController employeeController = fxmlLoader.getController();
            ServiceInjector.injectAllServices(employeeController);
            employeeController.setUsername(username);
            employeeController.setEmployeeInfo(username);
            Stage stage = (Stage) statusButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas ładowania strony.\nSpróbuj ponownie później.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onExportClicked() {
        try {
            employeeExportService.exportFile(employeeService.getEmployeeByLogin(username), repairOrderService.getEmployeeOrderStatus(getEmployee().getId()));
            AlertUtil.informationAlert("Pomyślnie pobrano dane\nKliknij OK aby wyłączyć okno");
        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych.\nSpróbuj ponownie później.");
        }
    }

    @FXML
    private void onLogoutClicked(){
        AlertUtil.showTimedAlert(
                Alert.AlertType.INFORMATION,
                "Informacja",
                null,
                "Za 5 sekund nastąpi wylogowanie",
                () -> {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Login-scene.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) logoutButton.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (Exception e) {
                        AlertUtil.errorAlert("Wystąpił błąd podczas wylogowywania.\nSpróbuj ponownie później.");
                    }
                }
        );
    }

}
