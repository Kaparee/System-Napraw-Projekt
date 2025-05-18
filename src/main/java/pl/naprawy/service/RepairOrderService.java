package pl.naprawy.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.util.HibernateUtil;

import java.sql.Timestamp;
import java.util.List;

public class RepairOrderService implements IRepairOrderService {
    Session session = HibernateUtil.getSessionFactory().openSession();

    public void sendRepairOrder(RepairOrder repairOrder){
        try{
            session.beginTransaction();
            session.save(repairOrder);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<RepairOrder> getFreeRepairOrder(Long id){
        String hql = "SELECT ro FROM RepairOrder ro WHERE ro.technician.id IS NULL AND ro.company.id " +
                "IN (SELECT tc.company.id FROM TechnicianCompany tc WHERE tc.technician.id = :id)";
        Query<RepairOrder> query = session.createQuery(hql, RepairOrder.class);
        query.setParameter("id",id);

        List<RepairOrder> result = query.list();
        return result;
    }

    public List<RepairOrder> getUserOrderStatus(Long id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT c FROM RepairOrder c WHERE c.client.id = :id ORDER BY c.status ASC";
        Query<RepairOrder> query = session.createQuery(hql, RepairOrder.class);
        query.setParameter("id", id);

        return query.list();

    }

    public void deleteUserOrder(Long id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        RepairOrder order = session.get(RepairOrder.class, id);
        if (order != null){
            session.delete(order);
        }
        transaction.commit();
        session.close();
    }
}
