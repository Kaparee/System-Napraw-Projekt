package pl.naprawy.service;

import pl.naprawy.model.Client;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.DateFormatterUtil;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class UserExportService implements IUserExportService{
    public void exportFile(Client client, List<RepairOrder> repairOrders) {
        try (PrintWriter writer = new PrintWriter(new File("client_"+client.getName()+"_info_and_repairs.csv"))) {
            writer.println("client_name, phone,email, company_name");
            writer.printf("%s, %s, %s, %s%n",client.getName(), client.getPhone(), client.getEmail(), client.getCompany().getName());
            writer.println("description,created_at,updated_at,technician,device,status");
            for (RepairOrder ro : repairOrders) {
                String technicianName = "Brak przypisanego technika";
                if (ro.getTechnician() != null) {
                    technicianName = ro.getTechnician().getName()+" "+ro.getTechnician().getEmail();
                }
                writer.printf("%s, %s, %s, %s, %s, %s%n", ro.getDescription().replace(",", ";"), DateFormatterUtil.format(ro.getCreated_at()), DateFormatterUtil.format(ro.getUpdated_at()), technicianName, ro.getDevice().getBrand()+" "+ro.getDevice().getModel(), ro.getStatus());
            }
        } catch (Exception e) {
            AlertUtil.informationAlert("Błąd podczas tworzenia pliku");
            e.printStackTrace();
        }
    }

}