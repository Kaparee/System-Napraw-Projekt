package pl.naprawy.service;

import pl.naprawy.model.Company;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;

import java.util.List;

public interface ITechnicianExportService {
    void exportFile(Technician technician, List<Company> companies, List<RepairOrder> repairOrders);
}
