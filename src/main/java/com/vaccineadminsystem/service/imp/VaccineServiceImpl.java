package com.vaccineadminsystem.service.imp;

import com.vaccineadminsystem.converter.VaccineConverter;
import com.vaccineadminsystem.dto.VaccineDto;
import com.vaccineadminsystem.entity.Vaccine;
import com.vaccineadminsystem.entity.VaccineType;
import com.vaccineadminsystem.exception.ImportFileException;
import com.vaccineadminsystem.repository.VaccineRepository;
import com.vaccineadminsystem.repository.VaccineTypeRepository;
import com.vaccineadminsystem.service.VaccineService;
import com.vaccineadminsystem.util.ExcelUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VaccineServiceImpl implements VaccineService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VaccineRepository vaccineRepo;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepo;

    @Autowired
    private VaccineConverter vaccineConverter;

    @Autowired
    private ExcelUtil excelUtil;

    @Autowired
    private ModelMapper modelMapper;

    private VaccineDto convertToDto(Vaccine vaccine) {
        return modelMapper.map(vaccine, VaccineDto.class);
    }

    private Vaccine convertToEntity(VaccineDto vaccineDTO) {
        return modelMapper.map(vaccineDTO, Vaccine.class);
    }

    /**
     * @return return list of vaccines
     */
    @Override
    public List<VaccineDto> listAll() {
        List<VaccineDto> models = new ArrayList<>();
        List<Vaccine> entities = vaccineRepo.findAll();
        for (Vaccine item : entities) {
            VaccineDto vaccineDTO = vaccineConverter.toDto(item);
            models.add(vaccineDTO);
        }
        return models;
    }

    /**
     * @param id of vaccine that need to get information
     * @return return vaccine information if not null, else return null
     */
    @Override
    public VaccineDto get(String id) {
        Optional<Vaccine> optVaccine = vaccineRepo.findById(id);
        Vaccine vaccine = optVaccine.orElse(null);
        if (vaccine != null) {
            return vaccineConverter.toDto(vaccine);
        }
        return null;
    }

    /**
     * @param id of vaccine that need to delete
     */
    @Override
    public void delete(String id) {
        vaccineRepo.deleteById(id);
    }

    /**
     * @param ids: list ids of vaccine that need to update status to in-active
     */
    @Override
    public boolean updateStatus(List<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            for (String id : ids) {
                vaccineRepo.updateVaccineStatusWithId(id);
            }
            return true;
        }
        return false;
    }

    /**
     * @param vaccineDTO: information of vaccine that need to insert to db
     * @return true/false if vaccine is inserted or not
     */
    @Override
    public boolean save(VaccineDto vaccineDTO) {
        // check vaccineTypeId is not null
        if (vaccineDTO.getVaccineTypeId() != null) {
            Optional<VaccineType> optVaccineType = vaccineTypeRepo.findById(vaccineDTO.getVaccineTypeId());
            VaccineType vaccineType = optVaccineType.orElse(null);

            // check vaccineType != null, status of vaccineType is true, vaccineID matches format, startDate must less than endDate, check length of some attributes
            if (vaccineType != null && vaccineType.getStatus() && isRightVaccineIDFormat(vaccineDTO) && isStartDateLessThanEndDate(vaccineDTO) && checkLength(vaccineDTO)) {
                Vaccine entity = vaccineConverter.toEntity(vaccineDTO);
                entity.setActive(true);
                entity.setVaccineType(vaccineType);

                vaccineRepo.save(entity);
                return true;
            }
        }
        return false;
    }

    /**
     * @param vaccineID:  id of vaccine that need to update
     * @param vaccineDTO: new information of vaccine that need to update
     * @return true/false when vaccine is updated or not
     */
    @Override
    public boolean update(String vaccineID, VaccineDto vaccineDTO) {

        Optional<Vaccine> optVac = vaccineRepo.findById(vaccineID);

        // check if vaccine is present, status of vaccine is true
        if (optVac.isPresent() && optVac.get().getActive()) {

            // check vaccineTypeId is not null
            if (vaccineDTO.getVaccineTypeId() != null) {
                Optional<VaccineType> optVaccineType = vaccineTypeRepo.findById(vaccineDTO.getVaccineTypeId());
                VaccineType vaccineType = optVaccineType.orElse(null);

                // check vaccineType != null, status of vaccineType is true, startDate must less than endDate, check length of some attributes
                if (vaccineType != null && vaccineType.getStatus() && isStartDateLessThanEndDate(vaccineDTO) && checkLength(vaccineDTO)) {
                    Vaccine newVaccine = vaccineConverter.toEntity(optVac.get(), vaccineDTO);
                    newVaccine.setVaccineType(vaccineType);
                    vaccineRepo.save(newVaccine);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param b: status of active
     * @return list records by status
     */
    @Override
    public List<VaccineDto> findVaccinesByStatus(boolean b) {

        List<Vaccine> vaccines = vaccineRepo.findByActive(b);

        List<VaccineDto> models = new ArrayList<>();
        for (Vaccine vaccine : vaccines) {
            VaccineDto vaccineDTO = vaccineConverter.toDto(vaccine);
            models.add(vaccineDTO);
        }
        return models;
    }

    /**
     * @param file file excel import in client
     * @return record saved in db
     * @throws ImportFileException throw when file is not excel or wrong format
     * @throws IOException         throw when get exception IO
     */
    @Override
    public List<Vaccine> importData(MultipartFile file) throws ImportFileException, IOException {
        List<VaccineDto> vaccineDTOsFromExcel = excelUtil.excelToVaccineDTOS(file.getInputStream());
        List<String> vaccineIDs = vaccineRepo.findVaccineIDs();
        List<VaccineDto> vaccineDTOsToImport = new ArrayList<>();

        for (VaccineDto vaccineDTO : vaccineDTOsFromExcel) {
            if (!vaccineIDs.contains(vaccineDTO.getVaccineID())) {
                // check vaccineTypeId is not null
                if (vaccineDTO.getVaccineTypeId() != null) {
                    Optional<VaccineType> optVaccineType = vaccineTypeRepo.findById(vaccineDTO.getVaccineTypeId());
                    VaccineType vaccineType = optVaccineType.orElse(null);
                    // check vaccineType != null, status of vaccineType is true, vaccineID matches format, startDate must less than endDate, check length of some attributes
                    if (vaccineType != null && vaccineType.getStatus() && isRightVaccineIDFormat(vaccineDTO) && isStartDateLessThanEndDate(vaccineDTO) && checkLength(vaccineDTO)) {
                        vaccineDTOsToImport.add(vaccineDTO);
                    }
                }
            }
        }

        List<Vaccine> list = vaccineDTOsToImport.stream().map(this::convertToEntity).collect(Collectors.toList());
        return vaccineRepo.saveAll(list);
    }

    private boolean isStartDateLessThanEndDate(VaccineDto vaccineDTO) {
        return (vaccineDTO.getNextTimeStart() == null || vaccineDTO.getNextTimeEnd() == null)
                || (vaccineDTO.getNextTimeStart().compareTo(vaccineDTO.getNextTimeEnd()) < 0);
    }

    private boolean isRightVaccineIDFormat(VaccineDto vaccineDTO) {
        return vaccineDTO.getVaccineID() != null && vaccineDTO.getVaccineID().matches("[0-9]*") && vaccineDTO.getVaccineID().length() == 10;
    }

    private boolean checkLength(VaccineDto vaccineDto) {
        return ((null != vaccineDto.getName() && !vaccineDto.getName().equals("") && vaccineDto.getName().length() <= 50)
                && (vaccineDto.getUsage() == null || vaccineDto.getUsage().length() <= 200)
                && (vaccineDto.getIndication() == null || vaccineDto.getIndication().length() <= 200)
                && (vaccineDto.getContraindication() == null || vaccineDto.getContraindication().length() <= 200)
                && (vaccineDto.getOrigin() == null || vaccineDto.getOrigin().length() <= 50)
                && (vaccineDto.getNumberOfInjection() >= 0 && vaccineDto.getNumberOfInjection() <= 15));
    }
}
