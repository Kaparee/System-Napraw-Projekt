package pl.naprawy.util;

import pl.naprawy.controller.BaseController;
import pl.naprawy.service.*;

public class ServiceInjector {

    public static void injectAllServices(BaseController controller) {
        controller.setLoginService(new LoginService());
        controller.setClientService(new ClientService());
        controller.setDeviceService(new DeviceService());
        controller.setRepairOrderService(new RepairOrderService());
        controller.setUserStatusService(new UserStatusService());
        controller.setUserExportService(new UserExportService());
    }
}
