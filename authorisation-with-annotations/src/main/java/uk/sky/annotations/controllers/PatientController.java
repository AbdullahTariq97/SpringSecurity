package uk.sky.annotations.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.sky.annotations.models.Patient;

import java.util.List;

// both admin and trainee admin have permissions to access this api because of their role
// however admin has use read,write,delete, create and post http opertions but the trainee can only do read operation

@RestController
@RequestMapping("/patient/info")
public class PatientController {

    private final List<Patient> patientList = List.of(new Patient(1,"tom",23, "broken foot"),
            new Patient(2,"anna", 88, "chest infection"),
            new Patient(3,"max", 44, "back ache"));

    @GetMapping
    public List<Patient> getAllPatients(){
        System.out.println("getAllPatients");
        return patientList;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('patient:write')")
    public void createPatient(@RequestBody Patient patient){
        System.out.println(patient);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('patient:write')")
    public void updatePatient(@PathVariable("id") int id, @RequestBody Patient patient){
        System.out.println("updatePatient");
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('patient:write')")
    public void deletePatient(@PathVariable("id") int id){
        System.out.println("deletePatient");
    }
}
