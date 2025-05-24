package pl.naprawy.controller;

import pl.naprawy.model.Employee;
import pl.naprawy.model.Company;
import pl.naprawy.model.Technician;
import pl.naprawy.service.*;

public abstract class BaseController {
    protected ILoginService loginService;
    protected IEmployeeService employeeService;
    protected ITechnicianService technicianService;
    protected ITechnicianCompanyService technicianCompanyService;
    protected IDeviceService deviceService;
    protected INewAccountService newAccountService;
    protected IRepairOrderService repairOrderService;
    protected IEmployeeExportService employeeExportService;
    protected ITechnicianExportService technicianExportService;
    protected String username;

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
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

    public void setEmployeeExportService(IEmployeeExportService employeeExportService) {
        this.employeeExportService = employeeExportService;
    }

    public void setTechnicianExportService(ITechnicianExportService technicianExportService) {
        this.technicianExportService = technicianExportService;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    protected Employee getEmployee() {
        return employeeService.getEmployeeByLogin(username);
    }

    protected Technician getTechnician(){
        return technicianService.getTechnicianByLogin(username);
    }

    protected Company getCompany(Employee employee) {
        return employee.getCompany();
    }

}
