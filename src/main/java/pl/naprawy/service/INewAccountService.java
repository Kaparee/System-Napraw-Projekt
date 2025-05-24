package pl.naprawy.service;

import pl.naprawy.model.Employee;
import pl.naprawy.model.Company;

import java.util.List;

public interface INewAccountService{
    List<Company> getAllCompany();
    void createNewEmployee(Employee employee, Long company_id);
    void createNewAccount(Employee employee, String username, String password);
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
    boolean isPhoneNumberTaken(String phoneNumber);
}
