package pl.naprawy.service;

import pl.naprawy.model.Company;

import java.util.List;

public interface ITechnicianCompanyService {
    List<Company> getTechnicianCompanies(Long id);
}
