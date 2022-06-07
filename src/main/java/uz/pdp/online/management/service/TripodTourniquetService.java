package uz.pdp.online.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.pdp.online.management.entity.Employees;
import uz.pdp.online.management.entity.TripodTourniquet;
import uz.pdp.online.management.payload.AccessDto;
import uz.pdp.online.management.payload.ApiResponse;
import uz.pdp.online.management.repository.EmployeesRepository;
import uz.pdp.online.management.repository.TripodTourniquetRepository;

import java.sql.Timestamp;
import java.util.*;

@Service
public class TripodTourniquetService {
    @Autowired
    TripodTourniquetRepository tripodTourniquetRepository;
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthService authService;

    public ApiResponse registerAccess(AccessDto accessDto){
        TripodTourniquet tripodTourniquet=new TripodTourniquet();
        Optional<Employees> optionalEmployees = employeesRepository.findById(accessDto.getEmployeeId());
        if (optionalEmployees.isPresent()){
            Employees employees = optionalEmployees.get();
            UserDetails userDetails = authService.loadUserByUsername(employees.getUsername());
           UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                   new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            tripodTourniquet.setDate(new Date());
            tripodTourniquet.setEmployees(employees);
            if (accessDto.isExit()) {
                tripodTourniquet.setEntranceTime(new Timestamp(System.currentTimeMillis()));
                tripodTourniquetRepository.save(tripodTourniquet);
                return new ApiResponse("Allowed to ENTRANCE",true);
            }else {
                Optional<TripodTourniquet> optionalTripodTourniquet = tripodTourniquetRepository.findByEmployeesIdAndExitTimeIsNull(employees.getId());

                if (optionalTripodTourniquet.isPresent()) {
                    TripodTourniquet tripodTourniquet1 = optionalTripodTourniquet.get();
                    tripodTourniquet1.setExitTime(new Timestamp(System.currentTimeMillis()));
                    tripodTourniquetRepository.save(tripodTourniquet1);
                    return new ApiResponse("Allowed to EXIT", true);
                }
            }


        }
        return new ApiResponse("Employee not found",false
        );


    }

    public ApiResponse getListBYEmployee(UUID employeeId) {
        List<TripodTourniquet> list = new ArrayList<>();
        try {
            List<TripodTourniquet> tourniquetList = tripodTourniquetRepository.findByEmployeesId(employeeId);

            return new ApiResponse("List", true, list);
        } catch (Exception e) {
            return new ApiResponse("Empty list", false);
        }
    }
}
