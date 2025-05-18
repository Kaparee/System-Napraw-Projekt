package pl.naprawy.util;

import pl.naprawy.controller.BaseController;
import pl.naprawy.model.Technician;
import pl.naprawy.service.*;

public class ServiceInjector {

    public static void injectAllServices(BaseController controller) {
        controller.setLoginService(new LoginService());
        controller.setClientService(new ClientService());
        controller.setTechnicianService(new TechnicianService());
        controller.setTechnicianCompanyService(new TechnicianCompanyService());
        controller.setDeviceService(new DeviceService());
        controller.setRepairOrderService(new RepairOrderService());
        controller.setUserExportService(new UserExportService());
    }
}
