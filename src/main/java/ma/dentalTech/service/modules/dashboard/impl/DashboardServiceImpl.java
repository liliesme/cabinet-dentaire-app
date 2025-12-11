package ma.dentalTech.service.modules.dashboard.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.service.modules.dashboard.api.DashboardService;
import ma.dentalTech.service.modules.patient.api.PatientService;
import ma.dentalTech.service.modules.rdv.api.RdvService;
import ma.dentalTech.service.modules.revenues.api.RevenuesService;
import ma.dentalTech.service.modules.charges.api.ChargesService;
import ma.dentalTech.common.exceptions.ServiceException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private PatientService patientService;
    private RdvService rdvService;
    private RevenuesService revenuesService;
    private ChargesService chargesService;

    @Override
    public Map<String, Object> getDashboardStatistics() throws ServiceException {
        Map<String, Object> stats = new HashMap<>();

        
        stats.putAll(getFinancialStats());
        stats.putAll(getPatientStats());
        stats.putAll(getRdvStats());

        return stats;
    }

    @Override
    public Map<String, Double> getFinancialStats() throws ServiceException {
        Map<String, Double> stats = new HashMap<>();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        stats.put("revenuesMois", revenuesService.getTotalRevenuesOfMonth(year, month));
        stats.put("chargesMois", chargesService.getTotalChargesOfMonth(year, month));
        stats.put("beneficeMois", stats.get("revenuesMois") - stats.get("chargesMois"));

        return stats;
    }

    @Override
    public Map<String, Long> getPatientStats() throws ServiceException {
        Map<String, Long> stats = new HashMap<>();

        
        
        

        return stats;
    }

    @Override
    public Map<String, Long> getRdvStats() throws ServiceException {
        Map<String, Long> stats = new HashMap<>();

        stats.put("rdvAujourdhui", (long) rdvService.getTodayRdv().size());

        return stats;
    }

    @Override
    public Map<String, Object> getChartData() throws ServiceException {
        
        
        return new HashMap<>();
    }
}