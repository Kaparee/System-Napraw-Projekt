package pl.naprawy.service;

import pl.naprawy.model.Client;
import pl.naprawy.model.Company;

import java.util.List;

public interface INewAccountService{
    List<Company> getAllCompany();
    void createNewClient(Client client, Long company_id);
    void createNewAccount(Client client, String username, String password);
}
