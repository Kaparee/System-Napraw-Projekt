package pl.naprawy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.naprawy.model.Client;
import pl.naprawy.model.Technician;
import pl.naprawy.util.AlertUtil;

import java.util.List;
import java.util.Optional;

public class DeleteEmployeeController extends BaseController{
    @FXML TableView<Client> tableView;
    @FXML TableColumn<Client, String> nameColumn, companyColumn;
    @FXML PasswordField passwordField;
    @FXML Button deleteButton, closeButton;

    @FXML
    public void initialize(){
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        companyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompany().getName()));
    }

    @FXML
    public void getClients(Long id){
        List<Client> clients = clientService.getAllClientInCompanies(id);
        tableView.setItems(FXCollections.observableArrayList(clients));
    }

    public Long getSelectedClient(){
        Client selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null){
            return selected.getId();
        }else {
            return null;
        }
    }

    @FXML
    public void deleteEmployee(){
        try{
            Optional<ButtonType> result = AlertUtil.confirmAlert("Usuwanie użytkownika");
            if (result.isPresent() && result.get() == ButtonType.OK){
                if (technicianService.isPasswordCorrect(getTechnician().getId(), passwordField.getText())) {
                    Long selectedEmployee = getSelectedClient();
                    if (selectedEmployee != null){
                        clientService.deleteEmployee(selectedEmployee);
                        getClients(getTechnician().getId());
                        AlertUtil.informationAlert("Użytkownik został poprawnie usunięty.");
                        tableView.getSelectionModel().clearSelection();
                        passwordField.setText(null);
                    } else {
                        AlertUtil.errorAlert("Nie wybrano użytkownika.");
                    }
                } else {
                    AlertUtil.errorAlert("Podane błędne hasło");
                }
            }
        } catch (Exception e){
            AlertUtil.errorAlert("Wystąpił błąd podczas usuwania użytkownika");
            e.printStackTrace();
        }
    }

    @FXML
    public void closeButtonClicked(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
