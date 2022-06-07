package uz.pdp.online.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.management.payload.AccessDto;
import uz.pdp.online.management.payload.ApiResponse;
import uz.pdp.online.management.service.TripodTourniquetService;

import java.util.UUID;

@RestController
@RequestMapping("/api/accessControl")
public class TripodTourniquetController {
    @Autowired
    TripodTourniquetService tripodTourniquetService;

    @PostMapping
    public HttpEntity<?> registerAccess(@RequestBody AccessDto accessDto){
        ApiResponse apiResponse = tripodTourniquetService.registerAccess(accessDto);
       return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(apiResponse.getMessage());

    }
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_DIRECTOR')")
    @GetMapping("/getByEmployee/{employeeId}")
    public HttpEntity<?> getListByEmployee(@PathVariable UUID employeeId){
        ApiResponse apiResponse = tripodTourniquetService.getListBYEmployee(employeeId);
        if (apiResponse.isSuccess())
            return ResponseEntity.ok(apiResponse.getObject());
        return ResponseEntity.status(500).body(apiResponse.getMessage());
    }


}
