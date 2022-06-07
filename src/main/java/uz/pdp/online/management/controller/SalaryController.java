package uz.pdp.online.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.management.payload.ApiResponse;
import uz.pdp.online.management.payload.SalaryDto;
import uz.pdp.online.management.payload.TimestampDto;
import uz.pdp.online.management.service.SalaryService;

import java.util.UUID;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {
    @Autowired
    SalaryService salaryService;
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    @PostMapping
    public HttpEntity<?> addSalary(@RequestBody SalaryDto salaryDto){
        ApiResponse apiResponse = salaryService.addSalary(salaryDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.ok(apiResponse.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @PostMapping("/getByDate")
    public HttpEntity<?> getSalaryListByDate(@RequestBody TimestampDto timestampDto){
        ApiResponse apiResponse = salaryService.getSalaryByDate(timestampDto.getTimestamp());
        if (apiResponse.isSuccess())
            return ResponseEntity.ok(apiResponse.getObject());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR','ROLE_MANAGER')")
    @GetMapping("/getByEmployee/{employeeId}")
    public HttpEntity<?> getSalaryListByEmployeeId(@PathVariable UUID employeeId){
        ApiResponse apiResponse = salaryService.getSalaryByEmployeeId(employeeId);
        if (apiResponse.isSuccess())
            return ResponseEntity.ok(apiResponse.getObject());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse.getMessage());
    }
}
