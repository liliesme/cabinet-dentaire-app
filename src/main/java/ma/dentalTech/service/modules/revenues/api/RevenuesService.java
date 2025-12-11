package ma.dentalTech.service.modules.revenues.api;

import ma.dentalTech.entities.pat.Revenues;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RevenuesService {

    
    List<Revenues> getAllRevenues() throws ServiceException;
    Optional<Revenues> getRevenueById(Long id) throws ServiceException;
    void createRevenue(Revenues revenue) throws ServiceException, ValidationException;
    void updateRevenue(Revenues revenue) throws ServiceException, ValidationException;
    void deleteRevenue(Long id) throws ServiceException;

    
    List<Revenues> getRevenuesByTitre(String titre) throws ServiceException;
    List<Revenues> getRevenuesBetweenDates(LocalDate start, LocalDate end) throws ServiceException;
    List<Revenues> getRevenuesOfMonth(int year, int month) throws ServiceException;

    
    Double getTotalRevenues() throws ServiceException;
    Double getTotalRevenuesOfMonth(int year, int month) throws ServiceException;
}