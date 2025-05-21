package pl.naprawy.service;

import pl.naprawy.model.Client;
import pl.naprawy.model.Company;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.DateFormatterUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class TechnicianExportService implements ITechnicianExportService{
    public void exportFile(Technician technician, List<Company> companies, List<RepairOrder> repairOrders) {
        try (PrintWriter writer = new PrintWriter(new File("technician_"+technician.getName()+"_info_and_repairs.csv"))) {
            writer.println("technician_name, phone, email");
            writer.printf("%s, %s, %s%n",technician.getName(), technician.getPhone(), technician.getEmail());
            writer.println("=============================================================");
            for (Company c : companies){
                writer.println("company_name, company_address");
                writer.printf("%s, %s%n", c.getName(), c.getAddress());
                writer.println("=============================================================");
            }
            for (RepairOrder ro : repairOrders) {
                writer.println("description, created_at, updated_at, client_name, company_name, device, status");
                writer.printf("%s, %s, %s, %s, %s, %s, %s%n", ro.getDescription().replace(",", ";"),
                        DateFormatterUtil.format(ro.getCreated_at()), DateFormatterUtil.format(ro.getUpdated_at()),
                        ro.getClient().getName(), ro.getCompany().getName(), ro.getDevice().getBrand()+" "+ro.getDevice().getModel(), ro.getStatus());
                writer.println("==========================================================================================================================");
            }
        } catch (Exception e) {
            AlertUtil.informationAlert("Błąd podczas tworzenia pliku");
            e.printStackTrace();
        }
    }
}
