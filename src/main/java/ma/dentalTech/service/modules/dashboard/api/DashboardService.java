package ma.dentalTech.service.modules.dashboard.api;

import ma.dentalTech.common.exceptions.ServiceException;
import java.util.Map;

public interface DashboardService {

    
    Map<String, Object> getDashboardStatistics() throws ServiceException;

    
    Map<String, Double> getFinancialStats() throws ServiceException;

    
    Map<String, Long> getPatientStats() throws ServiceException;

    
    Map<String, Long> getRdvStats() throws ServiceException;

    
    Map<String, Object> getChartData() throws ServiceException;
}