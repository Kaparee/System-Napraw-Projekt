package pl.naprawy.service;

import pl.naprawy.model.Device;

import java.util.List;

public interface IDeviceService {
    Device getDeviceInfo(Long id);
    List<Device> getUserDevice(Long id);
    void createNewDevice(Device device);
    boolean isSerialNumberTaken(String serial_number);
}
