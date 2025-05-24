package pl.naprawy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.naprawy.model.Employee;
import pl.naprawy.model.Device;
import pl.naprawy.util.AlertUtil;

import java.util.List;

public class NewDeviceController extends BaseController{
    @FXML Label nameLabel, brandLabel, modelLabel, serialNumberLabel;
    @FXML TextField companyField, modelField, serialNumberField;
    @FXML ComboBox<String> comboBox;
    @FXML TableView<Employee> tableView;
    @FXML TableColumn<Employee, String> nameColumn, companyColumn;
    @FXML Button newDevice, closeButton;

    @FXML
    public void initialize(){
        comboBox.getItems().addAll("Laptop","Smartphone","Tablet","Telewizor");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        companyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompany().getName()));
    }

    public String getSelectedComboBox(){
        String selected = comboBox.getValue();
        if (selected != null){
            return selected;
        }else {
            return null;
        }
    }

    @FXML
    public void getEmployees(Long id){
        List<Employee> employees = employeeService.getAllEmployeesInCompanies(id);
        tableView.setItems(FXCollections.observableArrayList(employees));
    }

    public Employee getSelectedEmployee(){
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null){
            return selected;
        }else {
            return null;
        }
    }

    @FXML
    public void createNewDevice(){
        if (comboBox.getValue()==null || companyField.getText().isBlank() || modelField.getText().isBlank() || serialNumberField.getText().isBlank() || !isValidSerialNumber(serialNumberField.getText()) || tableView.getSelectionModel().getSelectedItem() == null){
            AlertUtil.errorAlert("Proszę poprawnie wprowadzić wszystkie dane");
            return;
        }

        if (deviceService.isSerialNumberTaken(serialNumberField.getText())) {
            AlertUtil.errorAlert("Wystąpił błąd podczas tworzenia urządzenia\nNumer seryjny '" + serialNumberField.getText() + "' jest już używany. Proszę podać inny.");
            return;
        }
        try {
            Device device = new Device();
            device.setType(getSelectedComboBox());
            device.setBrand(companyField.getText());
            device.setModel(modelField.getText());
            device.setSerial_number(serialNumberField.getText());
            device.setEmployee(getSelectedEmployee());
            AlertUtil.informationAlert("Prawidłowo dodano nowe urządzenie");
            deviceService.createNewDevice(device);
            companyField.setText(null);
            modelField.setText(null);
            serialNumberField.setText(null);
            comboBox.getSelectionModel().clearSelection();
            tableView.getSelectionModel().clearSelection();
        } catch (Exception e){
            AlertUtil.errorAlert("Dodawanie urządzenia nie powiodło się.");
        }
    }

    private boolean isValidSerialNumber(String serial_number){
        String regex = "^SN\\d{6}$";
        if (serial_number != null && serial_number.matches(regex)){
            return true;
        } else {
            return false;
        }
    }

    @FXML
    public void closeButtonClicked(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
