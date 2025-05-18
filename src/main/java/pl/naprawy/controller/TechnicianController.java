package pl.naprawy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.naprawy.model.*;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.DateFormatterUtil;
import pl.naprawy.util.ServiceInjector;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class TechnicianController extends BaseController{
    @FXML private Label nameLabel, surnameLabel, phoneLabel, emailLabel, descriptionLabel, createdLabel, updatedLabel, companyLabel, deviceLabel;
    @FXML private Button exportButton, logoutButton, claimButton;
    @FXML private TableView<Company> tableView;
    @FXML private TableColumn<Company, String> companyNameColumn, companyAddressColumn;
    @FXML private TableView<RepairOrder> tableViewInfo;
    @FXML private TableColumn<RepairOrder, String> descriptionColumn, createdColumn, updatedColumn, companyColumn, clientColumn, companyAdressNameColumn, deviceColumn;

    @FXML
    public void initialize(){
        companyNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        companyAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        createdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateFormatterUtil.format(cellData.getValue().getCreated_at())));
        updatedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateFormatterUtil.format(cellData.getValue().getUpdated_at())));
        clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getName()));
        companyAdressNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompany().getName() +"\n"+ cellData.getValue().getCompany().getAddress()));
        deviceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDevice().getBrand() +"\n"+ cellData.getValue().getDevice().getModel()));
    }

    public void setTechnicianInfo(String username) {
        Technician technician = getTechnician();

        if (technician != null) {
            String[] nameParts = technician.getName().split(" ");
            nameLabel.setText(nameParts[0]);
            surnameLabel.setText(nameParts.length > 1 ? nameParts[1] : "");
            phoneLabel.setText(technician.getPhone());
            emailLabel.setText(technician.getEmail());
        }
        showAccountInformation();
        showFreeReport();
        showInformationInSquare();
    }


    @FXML
    public void showAccountInformation(){
        List<Company> companyList = technicianCompanyService.getTechnicianCompanies(getTechnician().getId());
        tableView.setItems(FXCollections.observableArrayList(companyList));
    }

    @FXML
    public void showFreeReport(){
        List<RepairOrder> repairOrderList = repairOrderService.getFreeRepairOrder(getTechnician().getId());
        tableViewInfo.setItems(FXCollections.observableArrayList(repairOrderList));
    }

    public void showInformationInSquare() {
        tableViewInfo.setOnMouseClicked(event -> {
            RepairOrder selected = tableViewInfo.getSelectionModel().getSelectedItem();
            if (selected != null) {
                descriptionLabel.setText(selected.getDescription());
                createdLabel.setText(DateFormatterUtil.format(selected.getCreated_at()));
                updatedLabel.setText(DateFormatterUtil.format(selected.getUpdated_at()));
                companyLabel.setText("Pracownik: "+selected.getClient().getName()+"\nFirma: "+selected.getCompany().getName()+" "+selected.getCompany().getAddress());
                deviceLabel.setText(selected.getDevice().getBrand() +" "+ selected.getDevice().getModel());
            }
        });
    }

    @FXML
    private void onStatusButtonClicked(){
    }

    @FXML
    private void onExportClicked() {
    }

    @FXML
    private void onClaimButtonClicked(){

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
                    } catch (IOException e) {
                        AlertUtil.errorAlert("Wystąpił błąd podczas wylogowywania.\nSpróbuj ponownie później.");
                    }
                }
        );
    }
}
