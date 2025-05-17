package pl.naprawy.service;

import pl.naprawy.model.RepairOrder;

import java.util.List;

public interface IUserStatusService {
    List<RepairOrder> getUserOrderStatus(Long id);
    void deleteUserOrder(Long id);
}
