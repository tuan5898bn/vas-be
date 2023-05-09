package com.vaccineadminsystem.controller;

import com.vaccineadminsystem.dto.ReportVaccineDto;
import com.vaccineadminsystem.dto.ReportVaccineTypeChart;
import com.vaccineadminsystem.service.VaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @GetMapping("/report/vaccines")
    public ResponseEntity<List<ReportVaccineDto>> reportVaccineByType() {
        List<ReportVaccineDto> listReportVaccine = vaccineTypeService.reportVaccineByType();
        return new ResponseEntity<List<ReportVaccineDto>>(listReportVaccine, HttpStatus.OK);
    }

    @GetMapping("/report/vaccine-chart")
    public ResponseEntity<?> reportVaccineTypeChart() {
        List<ReportVaccineTypeChart> rp = vaccineTypeService.reportVaccineTypeChart();
        return new ResponseEntity<>(rp, HttpStatus.OK);
    }
}
