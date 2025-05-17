package pl.naprawy.controller;

import pl.naprawy.model.Client;
import pl.naprawy.model.Company;
import pl.naprawy.service.*;

public abstract class BaseController {

    protected ILoginService loginService;
    protected IClientService clientService;
    protected IDeviceService deviceService;
    protected IRepairOrderService repairOrderService;
    protected IUserStatusService userStatusService;
    protected IUserExportService userExportService;
    protected String username;

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public void setClientService(IClientService clientService) {
        this.clientService = clientService;
    }

    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void setRepairOrderService(IRepairOrderService repairOrderService) {
        this.repairOrderService = repairOrderService;
    }

    public void setUserStatusService(IUserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    public void setUserExportService(IUserExportService userExportService) {
        this.userExportService = userExportService;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    protected Client getClient() {
        return clientService.getClientByLogin(username);
    }

    protected Company getCompany(Client client) {
        return client.getCompany();
    }

}
