package pl.naprawy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.naprawy.model.Employee;
import pl.naprawy.util.AlertUtil;

import java.util.List;
import java.util.Optional;

public class DeleteEmployeeController extends BaseController{
    @FXML TableView<Employee> tableView;
    @FXML TableColumn<Employee, String> nameColumn, companyColumn;
    @FXML PasswordField passwordField;
    @FXML Button deleteButton, closeButton;

    @FXML
    public void initialize(){
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        companyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompany().getName()));
    }

    @FXML
    public void getEmployees(Long id){
        List<Employee> employees = employeeService.getAllEmployeesInCompanies(id);
        tableView.setItems(FXCollections.observableArrayList(employees));
    }

    public Long getSelectedEmployee(){
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null){
            return selected.getId();
        }else {
            return null;
        }
    }

    @FXML
    public void deleteEmployee(){
        try{
            Optional<ButtonType> result = AlertUtil.confirmAlert("Usuwanie pracownika");
            if (result.isPresent() && result.get() == ButtonType.OK){
                if (technicianService.isPasswordCorrect(getTechnician().getId(), passwordField.getText())) {
                    Long selectedEmployee = getSelectedEmployee();
                    if (selectedEmployee != null){
                        employeeService.deleteEmployee(selectedEmployee);
                        getEmployees(getTechnician().getId());
                        AlertUtil.informationAlert("Pracownik został poprawnie usunięty.");
                        tableView.getSelectionModel().clearSelection();
                        passwordField.setText(null);
                    } else {
                        AlertUtil.errorAlert("Nie wybrano pracownika.");
                    }
                } else {
                    AlertUtil.errorAlert("Podane błędne hasło");
                }
            }
        } catch (Exception e){
            AlertUtil.errorAlert("Wystąpił błąd podczas usuwania pracownika");
            e.printStackTrace();
        }
    }

    @FXML
    public void closeButtonClicked(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
