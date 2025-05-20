package pl.naprawy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Company;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;
import java.util.List;

public class TechnicianCompanyService implements ITechnicianCompanyService{
    public List<Company> getTechnicianCompanies(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT tc.company FROM TechnicianCompany tc WHERE tc.technician.id = :id";
            Query<Company> query = session.createQuery(hql, Company.class);
            query.setParameter("id", id);
            return query.list();
        }catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania firm technika");
            return null;
        }
    }
}
