package pl.naprawy.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.naprawy.model.Device;
import pl.naprawy.util.HibernateUtil;

public class DeviceService {
    Session session = HibernateUtil.getSessionFactory().openSession();

    public Device getDeviceInfo(String serial_number){
        String hql = "SELECT c FROM Device c WHERE c.serial_number = :serial_number";
        Query<Device> query = session.createQuery(hql, Device.class);
        query.setParameter("serial_number", serial_number);

        Device result = query.uniqueResult();
        return result;
    }
}
