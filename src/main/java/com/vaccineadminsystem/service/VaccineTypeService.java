package com.vaccineadminsystem.service;

import com.vaccineadminsystem.dto.ReportVaccineDto;
import com.vaccineadminsystem.dto.ReportVaccineTypeChart;
import com.vaccineadminsystem.dto.VaccineTypeDto;
import com.vaccineadminsystem.exception.ExistVaccineTypeIdException;
import com.vaccineadminsystem.exception.NotExistVaccineTypeIdException;
import com.vaccineadminsystem.exception.SaveInActiveVaccineTypeException;

import java.util.List;


public interface VaccineTypeService{
    List<VaccineTypeDto> findAllVaccineType();
    VaccineTypeDto saveVaccineType(VaccineTypeDto vaccineTypeDto) throws ExistVaccineTypeIdException, SaveInActiveVaccineTypeException;
    VaccineTypeDto updateVaccineType(VaccineTypeDto vaccineTypeDto) throws NotExistVaccineTypeIdException;
    VaccineTypeDto findById(String id);
    List<VaccineTypeDto> findAllVaccineTypeByActiveStatus(boolean b);
    boolean makeInActive(List<String> ids);
    boolean isPresentVaccineType(String id);
    List<ReportVaccineDto> reportVaccineByType();
    List<ReportVaccineTypeChart> reportVaccineTypeChart();
}
