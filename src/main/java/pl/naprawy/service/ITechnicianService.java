package pl.naprawy.service;

import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;

import java.util.List;

public interface ITechnicianService {
    Technician getTechnicianByLogin(String login);
}
