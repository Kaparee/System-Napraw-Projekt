package pl.naprawy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Device;
import pl.naprawy.util.AlertUtil;
import pl.naprawy.util.HibernateUtil;

public class DeviceService implements IDeviceService{
    public Device getDeviceInfo(String serial_number){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT c FROM Device c WHERE c.serial_number = :serial_number";
            Query<Device> query = session.createQuery(hql, Device.class);
            query.setParameter("serial_number", serial_number);
            return query.uniqueResult();
        } catch (HibernateException e) {
            AlertUtil.errorAlert("Wystąpił błąd podczas pobierania danych urządzenia");
            return null;
        }
    }
}
