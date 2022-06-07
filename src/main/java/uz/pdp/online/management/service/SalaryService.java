package uz.pdp.online.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.online.management.entity.Employees;
import uz.pdp.online.management.entity.Salary;
import uz.pdp.online.management.payload.ApiResponse;
import uz.pdp.online.management.payload.SalaryDto;
import uz.pdp.online.management.repository.EmployeesRepository;
import uz.pdp.online.management.repository.SalaryRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    EmployeesRepository employeesRepository;
    public ApiResponse addSalary(SalaryDto salaryDto){
        Salary salary=new Salary();
        Optional<Employees> optionalEmployees = employeesRepository.findById(salaryDto.getEmployeeId());
        if (optionalEmployees.isPresent()){
            Employees employees = optionalEmployees.get();
            salary.setEmployees(employees);
            salary.setAmount(salaryDto.getAmount());
            salaryRepository.save(salary);
            return new ApiResponse("Success",true);
        }
        return new ApiResponse("Employee not found",false);
    }

    public ApiResponse getSalaryByEmployeeId(UUID employeeId){
        List<Salary> salaryList = salaryRepository.findByEmployeesId(employeeId);
        if (salaryList!=null)
            return new ApiResponse("List of salary",true,salaryList);
        return new ApiResponse("Empty list",false);
    }

    public ApiResponse getSalaryByDate(Timestamp timestamp){
        List<Salary> salaryList = salaryRepository.findByDateEquals(timestamp);
        if (salaryList.isEmpty())
            return new ApiResponse("List is empty",false);
        return new ApiResponse("List of salary by given date",true,salaryList);
    }
}
