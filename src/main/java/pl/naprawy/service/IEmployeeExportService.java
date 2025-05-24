package pl.naprawy.service;

import pl.naprawy.model.Employee;
import pl.naprawy.model.RepairOrder;

import java.util.List;

public interface IEmployeeExportService {
    void exportFile(Employee employee, List<RepairOrder> repairOrders);
}
