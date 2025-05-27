package pl.naprawy.service;

import pl.naprawy.model.RepairOrder;

import java.sql.Timestamp;
import java.util.List;

public interface IRepairOrderService {
    void sendRepairOrder(RepairOrder repairOrder);
    List<RepairOrder> getFreeRepairOrder(Long id);
    List<RepairOrder> getEmployeeOrderStatus(Long id);
    void deleteOrder(Long id);
    void closeOrder(Long id, Timestamp now);
    List<RepairOrder> getTechnicianReports(Long id);
}
