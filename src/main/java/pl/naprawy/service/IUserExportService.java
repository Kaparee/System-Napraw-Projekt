package pl.naprawy.service;

import pl.naprawy.model.Client;
import pl.naprawy.model.RepairOrder;

import java.util.List;

public interface IUserExportService {
    void exportFile(Client client, List<RepairOrder> repairOrders);
}
