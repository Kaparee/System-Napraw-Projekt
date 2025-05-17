package pl.naprawy.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.util.HibernateUtil;
import java.util.List;

public class UserStatusService implements IUserStatusService{

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
