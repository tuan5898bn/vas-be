package com.vaccineadminsystem.controller;

import com.vaccineadminsystem.dto.EmployeeDto;
import com.vaccineadminsystem.dto.ImageResource;
import com.vaccineadminsystem.dto.RestResponse;
import com.vaccineadminsystem.entity.Employee;
import com.vaccineadminsystem.exception.ConstraintException;
import com.vaccineadminsystem.exception.ExistEmployeeException;
import com.vaccineadminsystem.exception.ImageNotFoundException;
import com.vaccineadminsystem.exception.InvalidAgeException;
import com.vaccineadminsystem.exception.SaveEmployeeException;
import com.vaccineadminsystem.exception.SaveImageException;
import com.vaccineadminsystem.service.EmployeeService;
import com.vaccineadminsystem.service.FileStorageService;
import com.vaccineadminsystem.util.ErrorMess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FileStorageService fileStorageService;

    @Value("${BE_URL}")
    private String BE_URL;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        List<EmployeeDto> employeeDtos = employeeService.getAll();
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto,
                                          BindingResult bindingResult)
        throws ExistEmployeeException, InvalidAgeException,
        SaveEmployeeException {
        if (bindingResult.hasErrors()) {
            return responseBindingError(bindingResult);
        }
        if (employeeDto.getEmployeeId() == null) {
            return new ResponseEntity<>(
                    new RestResponse(400, ErrorMess.NOT_PROVIDE_EMP_ID), HttpStatus.BAD_REQUEST
            );
        }
        EmployeeDto newEmpDto = employeeService.save(employeeDto);
        return new ResponseEntity<>(newEmpDto, HttpStatus.CREATED);
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable String employeeId) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        if (employeeDto != null) {
            return new ResponseEntity<>(employeeDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/employees")
    public ResponseEntity<Employee> deleteEmployeeByIds(@RequestParam("ids") List<String> empIds)
        throws ConstraintException {
        employeeService.deleteByIds(empIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable String employeeId,
                                            @Valid @RequestBody EmployeeDto employeeDto,
                                            BindingResult bindingResult) throws InvalidAgeException {
        if (bindingResult.hasErrors()) {
            return responseBindingError(bindingResult);
        }
        EmployeeDto newE = employeeService.update(employeeId, employeeDto);
        if (newE != null) {
            return new ResponseEntity<>(newE, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/save-image")
    public ResponseEntity<ImageResource> uploadImage(@RequestParam(required = false,name = "image") MultipartFile image) throws SaveImageException {
        if(image == null){
           throw new SaveImageException(ErrorMess.INVALID_IMAGE);
        }
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        fileStorageService.store(image, fileName);
        return new ResponseEntity<>(new ImageResource(fileName, image.getSize(), BE_URL + "/api/image/" + fileName), HttpStatus.OK);
    }

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws ImageNotFoundException {
        Resource resource = fileStorageService.loadFile(fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }


    private ResponseEntity<?> responseBindingError(BindingResult bindingResult) {
        List<String> errorList = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new RestResponse(400, String.join(", ", errorList)), HttpStatus.BAD_REQUEST);
    }

}
