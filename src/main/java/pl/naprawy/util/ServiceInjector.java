package pl.naprawy.util;

import pl.naprawy.controller.BaseController;
import pl.naprawy.service.*;

public class ServiceInjector {

    public static void injectAllServices(BaseController controller) {
        controller.setClientService(new ClientService());
        controller.setCompanyService(new CompanyService());
        controller.setDeviceService(new DeviceService());
        controller.setRepairOrderService(new RepairOrderService());
    }
}
