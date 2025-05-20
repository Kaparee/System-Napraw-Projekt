package pl.naprawy.service;

import pl.naprawy.model.Technician;


public interface ITechnicianService {
    Technician getTechnicianByLogin(String login);
}
