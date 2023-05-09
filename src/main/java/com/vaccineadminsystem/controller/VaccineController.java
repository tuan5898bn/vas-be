package com.vaccineadminsystem.controller;

import com.vaccineadminsystem.dto.MessageRes;
import com.vaccineadminsystem.dto.RestResponse;
import com.vaccineadminsystem.dto.VaccineDto;
import com.vaccineadminsystem.entity.Vaccine;
import com.vaccineadminsystem.exception.ImportFileException;
import com.vaccineadminsystem.service.VaccineService;
import com.vaccineadminsystem.util.ErrorMess;
import com.vaccineadminsystem.util.ExcelUtil;
import com.vaccineadminsystem.util.SuccessMess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VaccineController {

    @Autowired
    private VaccineService service;

    @Autowired
    private ExcelUtil excelUtil;

    @GetMapping("/vaccines")
    public ResponseEntity<List<VaccineDto>> getAllVaccines() {
        List<VaccineDto> listVaccines = new ArrayList<>(service.listAll());
        return new ResponseEntity<>(listVaccines, HttpStatus.OK);
    }

    @GetMapping("/vaccines/{id}")
    public ResponseEntity<Object> getVaccineById(@PathVariable String id) {
        VaccineDto vaccineDTO = service.get(id);
        if (null != vaccineDTO) {
            return new ResponseEntity<>(vaccineDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageRes(ErrorMess.NOT_FOUND_VACCINE), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/vaccines")
    public ResponseEntity<Object> createVaccine(@Valid @RequestBody VaccineDto vaccineDTO, BindingResult resutl) {
        if (resutl.hasErrors()) {
            return new ResponseEntity<>(new MessageRes(ErrorMess.FAIL_REQUIREMENT), HttpStatus.CONFLICT);
        }
        if (null != service.get(vaccineDTO.getVaccineID())) {
            return new ResponseEntity<>(new MessageRes(ErrorMess.EXISTED_VACCINE), HttpStatus.CONFLICT);
        }
        if (service.save(vaccineDTO)) {
            return new ResponseEntity<>(new MessageRes(SuccessMess.CREATE_SUCCESS), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new MessageRes(ErrorMess.FAIL_SYSTEM), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/vaccines/{id}")
    public ResponseEntity<Object> updateVaccine(@PathVariable String id, @RequestBody VaccineDto vaccineDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new MessageRes(ErrorMess.FAIL_REQUIREMENT), HttpStatus.BAD_REQUEST);
        }
        if(service.update(id,vaccineDTO)){
            return new ResponseEntity<>(new MessageRes(SuccessMess.UPDATE_SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageRes(ErrorMess.FAIL_SYSTEM), HttpStatus.CONFLICT);
    }

    @DeleteMapping("/vaccines/{id}")
    public ResponseEntity<Object> deleteVaccineById(@PathVariable String id) {
        VaccineDto vaccineDTO = service.get(id);
        if (vaccineDTO == null) {
            return new ResponseEntity<>(new MessageRes(ErrorMess.NOT_FOUND_VACCINE), HttpStatus.NOT_FOUND);
        }

        service.delete(id);
        return new ResponseEntity<>(new MessageRes(SuccessMess.DELETE_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("/vaccines")
    public ResponseEntity<Object> makeInActive(@RequestBody List<String> ids) {
        if(service.updateStatus(ids)) {
            return new ResponseEntity<>(new MessageRes(SuccessMess.UPDATE_SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageRes(ErrorMess.UPDATE_FAIL), HttpStatus.OK);

    }

    @GetMapping("/vaccinesByStatus/{status}")
    public ResponseEntity<List<VaccineDto>> getVaccinesByStatus(@PathVariable boolean status) {
        return new ResponseEntity<>(service.findVaccinesByStatus(status), HttpStatus.OK);
    }

    @PostMapping("/vaccines/import-file")
    public ResponseEntity<Object> importVaccine(@RequestParam("file") MultipartFile file) throws IOException, ImportFileException {
        if (excelUtil.hasExcelFormat(file)) {
            List<Vaccine> list = service.importData(file);
            return new ResponseEntity<>(new RestResponse(200, String.format(SuccessMess.IMPORT_SUCCESS, list.size())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageRes(ErrorMess.FAIL_IMPORT_EXCEL_FILE), HttpStatus.BAD_REQUEST);
        }
    }

}
