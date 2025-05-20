package pl.naprawy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.naprawy.model.RepairOrder;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;

import java.sql.Timestamp;
import java.util.List;

public class RepairOrderService implements IRepairOrderService {
    public void sendRepairOrder(RepairOrder repairOrder){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(repairOrder);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas wysyłania zgłoszenia");
        }
    }

    public List<RepairOrder> getFreeRepairOrder(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT ro FROM RepairOrder ro WHERE ro.technician.id IS NULL AND ro.company.id " +
                    "IN (SELECT tc.company.id FROM TechnicianCompany tc WHERE tc.technician.id = :id)";
            Query<RepairOrder> query = session.createQuery(hql, RepairOrder.class);
            query.setParameter("id", id);
            return query.list();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas wysyłania zgłoszenia");
            return null;
        }
    }

    public List<RepairOrder> getUserOrderStatus(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT c FROM RepairOrder c WHERE c.client.id = :id ORDER BY c.status ASC";
            Query<RepairOrder> query = session.createQuery(hql, RepairOrder.class);
            query.setParameter("id", id);
            return query.list();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania zgłoszeń");
            return null;
        }
    }

    public void deleteUserOrder(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            RepairOrder order = session.get(RepairOrder.class, id);
            if (order != null){
                session.delete(order);
            }
            transaction.commit();
            session.close();
        }catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas usuwania zgłoszenia");
        }
    }
}
