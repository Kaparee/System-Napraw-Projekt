package pl.naprawy.service;

import pl.naprawy.model.Employee;

import java.util.List;

public interface IEmployeeService {
    Employee getEmployeeByLogin(String login);
    List<Employee> getAllEmployeesInCompanies(Long id);
    void deleteEmployee(Long id);
}
