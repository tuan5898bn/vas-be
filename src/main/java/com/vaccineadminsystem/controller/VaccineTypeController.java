package com.vaccineadminsystem.controller;

import com.vaccineadminsystem.dto.EmployeeDto;
import com.vaccineadminsystem.dto.MessageRes;
import com.vaccineadminsystem.dto.VaccineTypeDto;
import com.vaccineadminsystem.exception.ExistVaccineTypeIdException;
import com.vaccineadminsystem.exception.NotExistVaccineTypeIdException;
import com.vaccineadminsystem.exception.SaveInActiveVaccineTypeException;
import com.vaccineadminsystem.service.VaccineTypeService;
import com.vaccineadminsystem.util.ErrorMess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VaccineTypeController {
    @Autowired
    private VaccineTypeService vaccineTypeService;

    @GetMapping("/vaccine-type/get")
    public ResponseEntity<List<VaccineTypeDto>> getAllVaccineType() {
        return new ResponseEntity<>(vaccineTypeService.findAllVaccineType(), HttpStatus.OK);
    }

    @GetMapping("/vaccine-type/get/{status}")
    public ResponseEntity<List<VaccineTypeDto>> getAllVaccineTypeByStatus(@PathVariable boolean status) {
        return new ResponseEntity<>(vaccineTypeService.findAllVaccineTypeByActiveStatus(status), HttpStatus.OK);
    }

    @PostMapping("/vaccine-type/add")
    public ResponseEntity<?> saveVaccineType(@Valid @RequestBody VaccineTypeDto vaccineTypeDto, BindingResult bindingResult) throws ExistVaccineTypeIdException, SaveInActiveVaccineTypeException {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new MessageRes(ErrorMess.VACCINE_TYPE_VALIDATE), HttpStatus.NOT_ACCEPTABLE);
        } else {
            vaccineTypeService.saveVaccineType(vaccineTypeDto);
            return new ResponseEntity<>(new MessageRes("Created Success"), HttpStatus.CREATED);
        }

    }


    @PostMapping("/vaccine-type/update")
    public ResponseEntity<?> updateVaccineType(@Valid @RequestBody VaccineTypeDto vaccineTypeDto, BindingResult bindingResult) throws NotExistVaccineTypeIdException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new MessageRes(ErrorMess.VACCINE_TYPE_VALIDATE),HttpStatus.NOT_ACCEPTABLE);
        } else {
            vaccineTypeService.updateVaccineType(vaccineTypeDto);
            return new ResponseEntity<>(new MessageRes("Updated Success"), HttpStatus.CREATED);
        }
    }


    @GetMapping("/vaccine-type/{id}")
    public ResponseEntity<?> getVaccineTypeById(@PathVariable String id) {
        VaccineTypeDto vaccineTypeDto = vaccineTypeService.findById(id);
        if (vaccineTypeDto == null) {
            return new ResponseEntity<>(new MessageRes(ErrorMess.VACCINE_TYPE_NOT_FOUND),HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(vaccineTypeDto, HttpStatus.OK);
    }

    @PutMapping("/vaccines-type/make-inactive")
    public ResponseEntity<EmployeeDto> makeInActiveVaccineType(@RequestParam List<String> ids) {
        vaccineTypeService.makeInActive(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
