package pl.naprawy.service;

import pl.naprawy.model.Client;

import java.util.List;

public interface IClientService {
    Client getClientByLogin(String login);
    List<Client> getAllClientInCompanies(Long id);
    void deleteEmployee(Long id);
}
