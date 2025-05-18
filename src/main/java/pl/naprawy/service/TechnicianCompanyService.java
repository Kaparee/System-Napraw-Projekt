package pl.naprawy.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Company;
import pl.naprawy.util.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class TechnicianCompanyService implements ITechnicianCompanyService{
    Session session = HibernateUtil.getSessionFactory().openSession();

    public List<Company> getTechnicianCompanies(Long id) {
        String hql = "SELECT tc.company FROM TechnicianCompany tc WHERE tc.technician.id = :id";
        Query<Company> query = session.createQuery(hql, Company.class);
        query.setParameter("id", id);

        return query.list();
    }
}
