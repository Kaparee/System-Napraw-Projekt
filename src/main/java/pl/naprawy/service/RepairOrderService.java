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

    public List<RepairOrder> getEmployeeOrderStatus(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "SELECT ro FROM RepairOrder ro WHERE ro.employee.id = :id ORDER BY ro.status ASC";
            Query<RepairOrder> query = session.createQuery(hql, RepairOrder.class);
            query.setParameter("id", id);
            return query.list();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania zgłoszeń");
            return null;
        }
    }

    public void deleteOrder(Long id){
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

    public void closeOrder(Long id, Timestamp now) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "UPDATE RepairOrder SET status= 'Zakończono', updated_at = :now WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("now", now);
            query.setParameter("id", id);
            int updatedEntities = query.executeUpdate();
            transaction.commit();
            if (updatedEntities > 0) {
                AlertUtil.informationAlert("Zgłoszenie zostało pomyślnie zakończone.");
            } else {
                AlertUtil.errorAlert("Nie znaleziono zgłoszenia o podanym ID lub nie dokonano zmian.");
            }

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            AlertUtil.errorAlert("Wystąpił błąd podczas zamykania zgłoszenia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<RepairOrder> getTechnicianReports(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT ro FROM RepairOrder ro WHERE ro.technician.id = :id AND ro.status != 'Zakończono'";
            Query<RepairOrder> query = session.createQuery(hql, RepairOrder.class);
            query.setParameter("id",id);
            return query.list();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania zgłoszeń");
            return null;
        }
    }
}
