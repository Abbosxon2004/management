package uz.pdp.online.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.management.payload.ApiResponse;
import uz.pdp.online.management.payload.ChangeTaskStatusDto;
import uz.pdp.online.management.payload.TaskDto;
import uz.pdp.online.management.service.TaskService;


@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping
    public HttpEntity<?> addTask(@RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.assignTask(taskDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.ok(apiResponse.getMessage());
        }
        return ResponseEntity.status(401).body(apiResponse.getMessage());
    }
    @PreAuthorize(value = "hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER','ROLE_DIRECTOR')")
    @GetMapping
    public HttpEntity<?> getTask(){
        ApiResponse apiResponse = taskService.getAssignedTask();
        if (apiResponse.isSuccess())
            return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @PreAuthorize(value = "hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER','ROLE_DIRECTOR')")
    @PostMapping("/changeStatus")
    public HttpEntity<?> changeTaskStatus(@RequestBody ChangeTaskStatusDto changeTaskStatusDto){
        ApiResponse apiResponse = taskService.changeStatusOfTask(changeTaskStatusDto.getTaskStatusId());
        if (apiResponse.isSuccess())
            return ResponseEntity.ok(apiResponse.getMessage());
        return ResponseEntity.status(500).body(apiResponse.getMessage());
    }

}
