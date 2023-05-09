package com.vaccineadminsystem.service.imp;

import com.vaccineadminsystem.dto.ReportVaccineDto;
import com.vaccineadminsystem.dto.ReportVaccineTypeChart;
import com.vaccineadminsystem.dto.VaccineTypeDto;
import com.vaccineadminsystem.entity.Vaccine;
import com.vaccineadminsystem.entity.VaccineType;
import com.vaccineadminsystem.exception.ExistVaccineTypeIdException;
import com.vaccineadminsystem.exception.NotExistVaccineTypeIdException;
import com.vaccineadminsystem.exception.SaveInActiveVaccineTypeException;
import com.vaccineadminsystem.repository.VaccineRepository;
import com.vaccineadminsystem.repository.VaccineTypeRepository;
import com.vaccineadminsystem.service.VaccineTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaccineadminsystem.util.ErrorMess;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VaccineTypeServiceImpl implements VaccineTypeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    VaccineRepository vaccineRepository;
    @Autowired
    VaccineTypeRepository vaccineTypeRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<VaccineTypeDto> findAllVaccineType() {
        List<VaccineType> vaccineTypes = vaccineTypeRepository.findAll();
        List<VaccineTypeDto> vaccineTypeDtos = new ArrayList<>();
       if (!vaccineTypes.isEmpty()){
           try {
               vaccineTypeDtos = vaccineTypes.stream().map(this::convertToDto).collect(Collectors.toList());
           } catch (Exception e) {
               logger.error(e.getMessage());
           }
           return vaccineTypeDtos;
       }
       return vaccineTypeDtos;
    }

    /**
     * @param vaccineTypeDto is vaccine users enter from the form and save
     * @return vaccineType when save success
     * @throws ExistVaccineTypeIdException      when VaccineTypeId exist
     * @throws SaveInActiveVaccineTypeException when VaccineType is In-Active
     */
    @Override
    public VaccineTypeDto saveVaccineType(VaccineTypeDto vaccineTypeDto) throws ExistVaccineTypeIdException, SaveInActiveVaccineTypeException {
        if (isPresentVaccineType(vaccineTypeDto.getId())) {
            throw new ExistVaccineTypeIdException(ErrorMess.VACCINE_TYPE_EXIST);
        } else {
            if (vaccineTypeDto.isStatus()) {
                VaccineType vaccineType = convertToEntity(vaccineTypeDto);
                VaccineType result= vaccineTypeRepository.save(vaccineType);
                return convertToDto(result);
            } else throw new SaveInActiveVaccineTypeException(ErrorMess.SAVE_VACCINE_TYPE_INACTIVE);
        }
    }

    /**
     * @param vaccineTypeDto is vaccine users enter from the form and save
     * @return VaccineType when Update Success
     * @throws NotExistVaccineTypeIdException   when VaccineType not exist
     */
    @Override
    public VaccineTypeDto updateVaccineType(VaccineTypeDto vaccineTypeDto) throws NotExistVaccineTypeIdException {
        if (!isPresentVaccineType(vaccineTypeDto.getId())) {
            throw new NotExistVaccineTypeIdException(ErrorMess.VACCINE_TYPE_NOT_EXIST);
        } else {
            VaccineType vaccineType = convertToEntity(vaccineTypeDto);
            if(!vaccineType.getStatus()){
                List<Vaccine> vaccines = vaccineRepository.findVaccineByVaccineType(vaccineType);
                for (Vaccine vaccine : vaccines) {
                    vaccine.setActive(false);
                    vaccineRepository.save(vaccine);
                }
            }
            return this.convertToDto(vaccineTypeRepository.save(vaccineType));
        }
    }

    @Override
    public VaccineTypeDto findById(String id) {
        VaccineTypeDto vaccineTypeDto;
        Optional<VaccineType> optionalVaccineType = vaccineTypeRepository.findById(id);
        if (optionalVaccineType.isPresent()) {
            vaccineTypeDto = convertToDto(optionalVaccineType.get());
            return vaccineTypeDto;
        }
        return null;
    }


    /**
     * Make all Vaccine Type are checked and vaccines belong to vaccine type are in-active
     *
     * @param ids is list VaccineTypeId are checked
     */
    @Override
    public boolean makeInActive(List<String> ids) {
        if (ids!=null && !ids.isEmpty()){
            for (String id : ids) {
                VaccineType vaccineType = vaccineTypeRepository.findById(id).orElse(null);
                if (vaccineType != null) {
                    List<Vaccine> vaccines = vaccineRepository.findVaccineByVaccineType(vaccineType);
                    for (Vaccine vaccine : vaccines) {
                        vaccine.setActive(false);
                        vaccineRepository.save(vaccine);
                    }
                    vaccineType.setStatus(false);
                    vaccineTypeRepository.save(vaccineType);
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean isPresentVaccineType(String id) {
        Optional<VaccineType> optVaccineType = vaccineTypeRepository.findById(id);
        return optVaccineType.isPresent();
    }


    private VaccineTypeDto convertToDto(VaccineType vaccineType) {
        return modelMapper.map(vaccineType, VaccineTypeDto.class);
    }

    private VaccineType convertToEntity(VaccineTypeDto vaccineTypeDto) {
        return modelMapper.map(vaccineTypeDto, VaccineType.class);
    }


    public List<VaccineTypeDto> findAllVaccineTypeByActiveStatus(boolean b) {
        List<VaccineType> vaccineTypes = vaccineTypeRepository.findByStatus(b);
        return vaccineTypes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ReportVaccineDto> reportVaccineByType() {
        return vaccineTypeRepository.reportVaccineByType();
    }

    @Override
    public List<ReportVaccineTypeChart> reportVaccineTypeChart() {
        return vaccineTypeRepository.reportVaccineType();
    }
}
