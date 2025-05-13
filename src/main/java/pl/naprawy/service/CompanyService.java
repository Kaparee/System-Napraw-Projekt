package pl.naprawy.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Company;
import pl.naprawy.util.HibernateUtil;

public class CompanyService {
    Session session = HibernateUtil.getSessionFactory().openSession();


    public Company getCompanyById(int id) {
        String hql = "FROM Company co WHERE co.id = :id";
        Query<Company> query = session.createQuery(hql, Company.class);
        query.setParameter("id", id);

        Company result = query.uniqueResult();
        return result;
    }

}

