package pl.naprawy.service;

import pl.naprawy.model.RepairOrder;

import java.util.List;

public interface IRepairOrderService {
    void sendRepairOrder(RepairOrder repairOrder);
    List<RepairOrder> getFreeRepairOrder(Long id);
    List<RepairOrder> getUserOrderStatus(Long id);
    void deleteUserOrder(Long id);
}
