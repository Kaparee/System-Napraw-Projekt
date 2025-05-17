package pl.naprawy.controller;

import pl.naprawy.model.Client;
import pl.naprawy.model.Company;
import pl.naprawy.service.*;

public abstract class BaseController {

    protected ClientService clientService;
    protected CompanyService companyService;
    protected DeviceService deviceService;
    protected RepairOrderService repairOrderService;
    protected UserStatusService userStatusService;
    protected UserExportService userExportService;
    protected String username;

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void setRepairOrderService(RepairOrderService repairOrderService) {
        this.repairOrderService = repairOrderService;
    }

    public void setUserStatusService(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    public void setUserExportService(UserExportService userExportService) {
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
