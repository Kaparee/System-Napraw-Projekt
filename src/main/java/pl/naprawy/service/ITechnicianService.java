package pl.naprawy.service;

import pl.naprawy.model.RepairOrder;
import pl.naprawy.model.Technician;
import java.sql.Timestamp;
import java.util.List;


public interface ITechnicianService {
    Technician getTechnicianByLogin(String login);
    void claimRaport(Long technician_id, Long id, Timestamp now);
    boolean isPasswordCorrect(Long id, String password);
}
