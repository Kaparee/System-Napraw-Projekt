package pl.naprawy.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Company;
import pl.naprawy.util.HibernateUtil;

import java.util.List;

public class NewAccountService implements INewAccountService{
    Session session = HibernateUtil.getSessionFactory().openSession();
    public List<Company> getAllCompany(){
        String hql = "SELECT c FROM Company c";
        Query<Company> query = session.createQuery(hql, Company.class);
        List<Company> result = query.list();
        return result;
    }
    public void createNewClient(){

    }
}
