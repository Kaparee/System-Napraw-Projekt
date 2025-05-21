package pl.naprawy.controller;

import pl.naprawy.model.Client;
import pl.naprawy.model.Company;
import pl.naprawy.model.Technician;
import pl.naprawy.service.*;

public abstract class BaseController {
    protected ILoginService loginService;
    protected IClientService clientService;
    protected ITechnicianService technicianService;
    protected ITechnicianCompanyService technicianCompanyService;
    protected IDeviceService deviceService;
    protected INewAccountService newAccountService;
    protected IRepairOrderService repairOrderService;
    protected IUserExportService userExportService;
    protected ITechnicianExportService technicianExportService;
    protected String username;

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public void setClientService(IClientService clientService) {
        this.clientService = clientService;
    }

    public void setTechnicianService(ITechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    public void setTechnicianCompanyService(ITechnicianCompanyService technicianCompanyService) {
        this.technicianCompanyService = technicianCompanyService;
    }

    public void setNewAccountService(INewAccountService newAccountService) {
        this.newAccountService = newAccountService;
    }

    public void setDeviceService(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void setRepairOrderService(IRepairOrderService repairOrderService) {
        this.repairOrderService = repairOrderService;
    }

    public void setUserExportService(IUserExportService userExportService) {
        this.userExportService = userExportService;
    }

    public void setTechnicianExportService(ITechnicianExportService technicianExportService) {
        this.technicianExportService = technicianExportService;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    protected Client getClient() {
        return clientService.getClientByLogin(username);
    }

    protected Technician getTechnician(){
        return technicianService.getTechnicianByLogin(username);
    }

    protected Company getCompany(Client client) {
        return client.getCompany();
    }

}
