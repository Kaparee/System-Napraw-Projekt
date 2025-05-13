package pl.naprawy.service;

import org.hibernate.Session;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.util.HibernateUtil;

import java.sql.Timestamp;

public class RepairOrderService {
    Session session = HibernateUtil.getSessionFactory().openSession();

    public void sendRepairOrder(RepairOrder repairOrder){
        try{
            session.beginTransaction();
            session.save(repairOrder);
            session.getTransaction().commit();
        } catch (Exception e){
            System.err.println("Błąd zapisu: "+e.getMessage());
        } finally {
            session.close();
        }
    }
}
