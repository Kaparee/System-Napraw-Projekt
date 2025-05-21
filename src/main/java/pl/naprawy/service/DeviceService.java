package pl.naprawy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Device;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;

import java.util.List;

public class DeviceService implements IDeviceService{
    public Device getDeviceInfo(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT c FROM Device c WHERE c.id = :id";
            Query<Device> query = session.createQuery(hql, Device.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych urządzenia");
            return null;
        }
    }

    public List<Device> getUserDevice(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT d FROM Device d WHERE d.client.id = :id";
            Query<Device> query = session.createQuery(hql, Device.class);
            query.setParameter("id", id);
            return query.list();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych urządzenia dla klienta");
            return null;
        }
    }

    public void createNewDevice(Device device){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(device);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas dodawania urządzenia");
        }
    }
}
