package pl.naprawy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.naprawy.model.Company;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.DateFormatterUtil;
import pl.naprawy.util.ServiceInjector;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TechnicianReportController extends TechnicianController{
    @FXML private Label nameLabel, surnameLabel, phoneLabel, emailLabel, descriptionLabel, createdLabel, updatedLabel, companyLabel, deviceLabel;
    @FXML private Button exportButton, logoutButton, closeButton, advancedButton, returnButton;
    @FXML private TableView<Company> tableView;
    @FXML private TableColumn<Company, String> companyNameColumn, companyAddressColumn;
    @FXML private TableView<RepairOrder> tableViewReports;
    @FXML private TableColumn<RepairOrder, String> descriptionColumn, createdColumn, updatedColumn, companyColumn, clientColumn, companyAdressNameColumn, deviceColumn;

    public void setTechnicianInfo(String username) {
        Technician technician = getTechnician();
        if (technician != null) {
            String[] nameParts = technician.getName().split(" ");
            nameLabel.setText(nameParts[0]);
            surnameLabel.setText(nameParts.length > 1 ? nameParts[1] : "");
            phoneLabel.setText(technician.getPhone());
            emailLabel.setText(technician.getEmail());
        }
        showFreeReport();
        showInformationInSquare();
        showAccountInformation();
    }

    @FXML
    private void showAccountInformation(){
        List<Company> companyList = technicianCompanyService.getTechnicianCompanies(getTechnician().getId());
        tableView.setItems(FXCollections.observableArrayList(companyList));
        showFreeReport();
        showInformationInSquare();
    }

    private void showInformationInSquare() {
        tableViewReports.setOnMouseClicked(event -> {
            RepairOrder selected = tableViewReports.getSelectionModel().getSelectedItem();
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
    public void showFreeReport(){
        List<RepairOrder> ownedOrderList = repairOrderService.getTechnicianReports(getTechnician().getId());
        tableViewReports.setItems(FXCollections.observableArrayList(ownedOrderList));
    }

    @FXML
    public void onReturnButtonClicked(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Main-scene-technician.fxml"));
        try {
            Parent root = fxmlLoader.load();
            TechnicianController technicianController = fxmlLoader.getController();
            ServiceInjector.injectAllServices(technicianController);
            technicianController.setUsername(username);
            technicianController.setTechnicianInfo(username);
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas ładowania strony.\nSpróbuj ponownie później.");
        }
    }

    @FXML
    public void onExportClicked(){
        try {
            technicianExportService.exportFile(technicianService.getTechnicianByLogin(username), technicianCompanyService.getTechnicianCompanies(getTechnician().getId()), repairOrderService.getUserOrderStatus(getClient().getId()));
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

    private Long getSelectedReport(){
        RepairOrder selected = tableViewReports.getSelectionModel().getSelectedItem();
        if (selected!=null){
            return selected.getId();
        } else {
            return null;
        }
    }

    @FXML
    public void onCloseButtonClicked(){
        Long selected = getSelectedReport();
        Optional<ButtonType> result = AlertUtil.confirmAlert("Zamknięcie zgłoszenia");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            repairOrderService.deleteOrder(selected);
            showFreeReport();
            descriptionLabel.setText("");
            createdLabel.setText("");
            updatedLabel.setText("");
            descriptionLabel.setText("");
            deviceLabel.setText("");
        }else {
            AlertUtil.informationAlert("Nie można zamknąć zgłoszenia.\nSpróbuj ponownie później");
        }
    }

    @FXML
    public void onAddDeviceClicked(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Add-device-scene.fxml"));
            Parent root = fxmlLoader.load();
            NewDeviceController controller = fxmlLoader.getController();
            ServiceInjector.injectAllServices(controller);
            controller.getClients(getTechnician().getId());
            Stage stage = new Stage();

            stage.setTitle("NAPRAW.IO");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            AlertUtil.errorAlert("Nie udało się otworzyć okna dodawania urządzenia.");
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteClientClicked(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/naprawy/fxml/Delete-user-scene.fxml"));
            Parent root = fxmlLoader.load();
            DeleteEmployeeController controller = fxmlLoader.getController();
            ServiceInjector.injectAllServices(controller);
            controller.getClients(getTechnician().getId());
            controller.setUsername(username);
            Stage stage = new Stage();

            stage.setTitle("NAPRAW.IO");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            AlertUtil.errorAlert("Nie udało się otworzyć okna usuwania pracowników.");
            e.printStackTrace();
        }
    }
}
