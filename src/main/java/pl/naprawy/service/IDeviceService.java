package pl.naprawy.service;

import pl.naprawy.model.Device;

public interface IDeviceService {
    Device getDeviceInfo(String serial_number);
}
