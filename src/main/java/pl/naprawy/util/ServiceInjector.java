package pl.naprawy.util;

import pl.naprawy.controller.BaseController;
import pl.naprawy.service.*;

public class ServiceInjector {

    public static void injectAllServices(BaseController controller) {
        controller.setLoginService(new LoginService());
        controller.setEmployeeService(new EmployeeService());
        controller.setTechnicianService(new TechnicianService());
        controller.setTechnicianCompanyService(new TechnicianCompanyService());
        controller.setDeviceService(new DeviceService());
        controller.setNewAccountService(new NewAccountService());
        controller.setRepairOrderService(new RepairOrderService());
        controller.setEmployeeExportService(new EmployeeExportService());
        controller.setTechnicianExportService(new TechnicianExportService());
    }
}
