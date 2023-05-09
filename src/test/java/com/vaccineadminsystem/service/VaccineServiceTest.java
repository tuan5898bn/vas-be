package com.vaccineadminsystem.service;

import com.vaccineadminsystem.converter.VaccineConverter;
import com.vaccineadminsystem.dto.VaccineDto;
import com.vaccineadminsystem.entity.Vaccine;
import com.vaccineadminsystem.entity.VaccineType;
import com.vaccineadminsystem.repository.VaccineRepository;
import com.vaccineadminsystem.repository.VaccineTypeRepository;
import com.vaccineadminsystem.util.ExcelUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VaccineServiceTest {

    @Autowired
    private VaccineService vaccineService;

    @MockBean
    private VaccineRepository vaccineRepository;

    @MockBean
    private VaccineTypeRepository vaccineTypeRepository;

    @MockBean
    private VaccineConverter vaccineConverter;

    @MockBean
    private ExcelUtil excelUtil;

    @MockBean
    private ModelMapper modelMapper;


    @Test
    public void testSave_Success() {
        // Setup
        final VaccineDto vaccineDTO = new VaccineDto("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "vaccine covid-19 origin", "vaccineType1");

        // Configure VaccineTypeRepository.findById(...).
        final Optional<VaccineType> vaccineType = Optional.of(new VaccineType("vaccineType1", "vaccineTypeName1", true));
        when(vaccineTypeRepository.findById(vaccineDTO.getVaccineTypeId())).thenReturn(vaccineType);

        // Configure VaccineConverter.toEntity(...).
        final Vaccine vaccine = new Vaccine("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "vaccine covid-19 origin", new VaccineType("vaccineType1", "vaccineTypeName1", true));
        when(vaccineConverter.toEntity(vaccineDTO)).thenReturn(vaccine);

        // Configure VaccineRepository.save(...).
        when(vaccineRepository.save(vaccine)).thenReturn(vaccine);

        // Run the test
        final boolean result = vaccineService.save(vaccineDTO);

        // Verify the results
        assertThat(result).isTrue();
    }


    @Test
    public void testSave_FailDate() {
        // Setup
        final VaccineDto vaccineDTO = new VaccineDto("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), "vaccine covid-19 origin", "vaccineType1");

        // Configure VaccineTypeRepository.findById(...).
        final Optional<VaccineType> vaccineType = Optional.of(new VaccineType("vaccineType1", "vaccineTypeName1", true));
        when(vaccineTypeRepository.findById(vaccineDTO.getVaccineTypeId())).thenReturn(vaccineType);

        // Configure VaccineConverter.toEntity(...).
        final Vaccine vaccine = new Vaccine("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), "vaccine covid-19 origin", new VaccineType("vaccineType1", "vaccineTypeName1", true));
        when(vaccineConverter.toEntity(vaccineDTO)).thenReturn(vaccine);

        // Configure VaccineRepository.save(...).
        when(vaccineRepository.save(vaccine)).thenReturn(vaccine);

        // Run the test
        final boolean result = vaccineService.save(vaccineDTO);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testSave_NullID() {
        // Setup
        final VaccineDto vaccineDTO = new VaccineDto(null, true, null, "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "vaccine covid-19 origin", "vaccineType1");

        // Configure VaccineTypeRepository.findById(...).
        final Optional<VaccineType> vaccineType = Optional.of(new VaccineType("vaccineType1", "vaccineTypeName1", true));
        when(vaccineTypeRepository.findById(vaccineDTO.getVaccineTypeId())).thenReturn(vaccineType);

        // Configure VaccineConverter.toEntity(...).
        final Vaccine vaccine = new Vaccine(null, true, null, "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "vaccine covid-19 origin", new VaccineType("vaccineType1", "vaccineTypeName1", true));
        when(vaccineConverter.toEntity(vaccineDTO)).thenReturn(vaccine);

        // Configure VaccineRepository.save(...).
        when(vaccineRepository.save(vaccine)).thenReturn(vaccine);

        // Run the test
        final boolean result = vaccineService.save(vaccineDTO);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testUpdate() {
        // Setup
        final String vaccineID = "0000000000";
        final VaccineDto vaccineDTO = new VaccineDto("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "vaccine covid-19 origin", "vaccineType1");

        // Configure VaccineRepository.findById(...).
        final Optional<Vaccine> existedVaccine = Optional.of(new Vaccine("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "vaccine covid-19 origin", new VaccineType("vaccineType1", "vaccineTypeName1", true)));
        when(vaccineRepository.findById(vaccineID)).thenReturn(existedVaccine);

        // Configure VaccineTypeRepository.findById(...).
        final Optional<VaccineType> vaccineType = Optional.of(new VaccineType("vaccineType1", "vaccineTypeName1", true));
        when(vaccineTypeRepository.findById(vaccineDTO.getVaccineTypeId())).thenReturn(vaccineType);

        // Configure VaccineConverter.toEntity(...).
        final Vaccine newVaccine = new Vaccine("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "Vietnam", new VaccineType("vaccineType1", "vaccineTypeName1", true));
        when(vaccineConverter.toEntity(existedVaccine.get(), vaccineDTO)).thenReturn(newVaccine);

        // Configure VaccineRepository.save(...).
        when(vaccineRepository.save(any(Vaccine.class))).thenReturn(newVaccine);

        // Run the test
        final boolean result = vaccineService.update(vaccineID, vaccineDTO);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testUpdate_InvalidVaccine() {
        // Setup
        final String vaccineID = "0000000001";
        final VaccineDto vaccineDTO = new VaccineDto("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "vaccine covid-19 origin", "vaccineType1");

        // Configure VaccineRepository.findById(...).
        when(vaccineRepository.findById(vaccineID)).thenReturn(Optional.empty());

        // Run the test
        final boolean result = vaccineService.update(vaccineID, vaccineDTO);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testUpdate_FailDateValidation() {
        // Setup
        final String vaccineID = "0000000000";
        final VaccineDto vaccineDTO = new VaccineDto("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), "vaccine covid-19 origin", "vaccineType1");

        // Configure VaccineRepository.findById(...).
        final Optional<Vaccine> existedVaccine = Optional.of(new Vaccine("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "vaccine covid-19 origin", new VaccineType("vaccineType1", "vaccineTypeName1", true)));
        when(vaccineRepository.findById(vaccineID)).thenReturn(existedVaccine);

        // Configure VaccineTypeRepository.findById(...).
        final Optional<VaccineType> vaccineType = Optional.of(new VaccineType("vaccineType1", "vaccineTypeName1", true));
        when(vaccineTypeRepository.findById(vaccineDTO.getVaccineTypeId())).thenReturn(vaccineType);

        // Run the test
        final boolean result = vaccineService.update(vaccineID, vaccineDTO);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testUpdateStatus_Success() {
        // Run the test
        final boolean result = vaccineService.updateStatus(Arrays.asList("0000000000", "0000000001"));

        // Verity the result
        assertThat(result).isTrue();
    }

    @Test
    public void testUpdateStatus_FailEmptyList() {
        // Run the test
        final boolean result = vaccineService.updateStatus(Arrays.asList());

        // Verity the result
        assertThat(result).isFalse();
    }

    @Test
    public void testUpdateStatus_FailNullList() {
        // Run the test
        final boolean result = vaccineService.updateStatus(null);

        // Verity the result
        assertThat(result).isFalse();
    }

    @Test
    public void testFindVaccinesByStatus() {
        // Setup
        VaccineType vaccineType = new VaccineType("vaccineType1", "vaccineTypeName1", true);

        Vaccine vaccine1 = new Vaccine("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "Vietnam", vaccineType);
        Vaccine vaccine2 = new Vaccine("0000000001", true, "vaccine h5n1", "vaccine h5n1", "vaccine h5n1", "vaccine h5n1", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "Vietnam", vaccineType);

        // Configure VaccineRepository.findByActive(...).
        final List<Vaccine> vaccines = Arrays.asList(vaccine1, vaccine2);
        when(vaccineRepository.findByActive(true)).thenReturn(vaccines);

        // Configure VaccineConverter.toDto(...).
        final VaccineDto vaccineDto1 = new VaccineDto("0000000000", true, "vaccine covid-19", "vaccine covid-19 usage", "vaccine covid-19 indication", "vaccine covid-19 contraindication", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "Vietnam", "vaccineType1");
        final VaccineDto vaccineDto2 = new VaccineDto("0000000001", true, "vaccine h5n1", "vaccine h5n1", "vaccine h5n1", "vaccine h5n1", 0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime(), "Vietnam", "vaccineType1");
        when(vaccineConverter.toDto(vaccine1)).thenReturn(vaccineDto1);
        when(vaccineConverter.toDto(vaccine2)).thenReturn(vaccineDto2);

        // Run the test
        final List<VaccineDto> result = vaccineService.findVaccinesByStatus(true);

        // Verify the results
        Assert.assertEquals(2, result.size());
    }
}
