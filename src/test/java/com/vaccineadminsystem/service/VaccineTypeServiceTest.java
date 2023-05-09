package com.vaccineadminsystem.service;

import com.vaccineadminsystem.dto.VaccineTypeDto;
import com.vaccineadminsystem.entity.VaccineType;
import com.vaccineadminsystem.exception.ExistVaccineTypeIdException;
import com.vaccineadminsystem.exception.NotExistVaccineTypeIdException;
import com.vaccineadminsystem.exception.SaveInActiveVaccineTypeException;

import com.vaccineadminsystem.repository.VaccineTypeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VaccineTypeServiceTest {

    @MockBean
    private VaccineTypeRepository vaccineTypeRepository;
    @MockBean
    private ModelMapper modelMapper;
    @Autowired
    private VaccineTypeService vaccineTypeService;

    VaccineType vaccineType1;
    VaccineType vaccineType2;
    VaccineTypeDto vaccineTypeDto1;
    VaccineTypeDto vaccineTypeDto2;
    List<VaccineType> vaccineTypes;
    List<String> ids;
    @Before
    public void setUp() {
        vaccineType1 = new VaccineType("VCT01", "Adenovirus", true);
        vaccineType2 = new VaccineType("VCT02", "Pneumococcal", false);
        vaccineTypeDto1 = new VaccineTypeDto("VCT01", "Adenovirus", true);
        vaccineTypeDto2 = new VaccineTypeDto("VCT02", "Pneumococcal", false);
        VaccineType vaccineType3 = new VaccineType("VCT03", "Pertussis", true);
        vaccineTypes=Arrays.asList(vaccineType1, vaccineType2,vaccineType3);
        ids=Arrays.asList(vaccineType1.getId(),vaccineType2.getId(),vaccineType3.getId());
    }


    @Test
    public void testFindByIdSuccess() {
        when(vaccineTypeRepository.findById(vaccineType1.getId())).thenReturn(Optional.of(vaccineType1));
        when(modelMapper.map(vaccineType1, VaccineTypeDto.class)).thenReturn(new VaccineTypeDto("VCT01", "Adenovirus", true));
        Assert.assertNotNull(vaccineTypeService.findById("VCT01"));
    }

    @Test
    public void testFindByIdFail() {
        when(vaccineTypeRepository.findById("VCT01")).thenReturn(Optional.empty());
        Assert.assertNull(vaccineTypeService.findById("VCT01"));
    }

    @Test
    public void testSaveSuccess() throws ExistVaccineTypeIdException, SaveInActiveVaccineTypeException {
        when(vaccineTypeRepository.findById(vaccineTypeDto1.getId())).thenReturn(Optional.empty());
        when(vaccineTypeRepository.save(vaccineType1)).thenReturn(vaccineType1);
        when(modelMapper.map(vaccineTypeDto1, VaccineType.class)).thenReturn(vaccineType1);
        when(modelMapper.map(vaccineType1, VaccineTypeDto.class)).thenReturn(vaccineTypeDto1);
        Assert.assertNotNull(vaccineTypeService.saveVaccineType(vaccineTypeDto1));
    }

    @Test(expected = SaveInActiveVaccineTypeException.class)
    public void testExceptionWhenSaveVaccineType1() throws ExistVaccineTypeIdException, SaveInActiveVaccineTypeException {
        vaccineTypeService.saveVaccineType(vaccineTypeDto2);
    }

    @Test(expected = ExistVaccineTypeIdException.class)
    public void testExceptionWhenSaveVaccineType2() throws ExistVaccineTypeIdException, SaveInActiveVaccineTypeException {

        when(vaccineTypeRepository.findById(vaccineTypeDto1.getId())).thenReturn(Optional.of(vaccineType1));
        vaccineTypeService.saveVaccineType(vaccineTypeDto1);
    }

    @Test
    public void testUpdateSuccessVaccineType() throws NotExistVaccineTypeIdException {
        when(vaccineTypeRepository.findById("VCT01")).thenReturn(Optional.of(vaccineType1));
        when(vaccineTypeRepository.save(vaccineType1)).thenReturn(vaccineType1);
        when(modelMapper.map(vaccineTypeDto1, VaccineType.class)).thenReturn(vaccineType1);
        when(modelMapper.map(vaccineType1, VaccineTypeDto.class)).thenReturn(new VaccineTypeDto("VCT01", "Adenovirus", true));
        Assert.assertNotNull(vaccineTypeService.updateVaccineType(vaccineTypeDto1));
    }

    @Test(expected = NotExistVaccineTypeIdException.class)
    public void testExceptionWhenUpdateVaccineType1() throws NotExistVaccineTypeIdException {
        when(vaccineTypeRepository.findById(vaccineTypeDto1.getId())).thenReturn(Optional.empty());
        vaccineTypeService.updateVaccineType(vaccineTypeDto1);
    }
    @Test
    public void testMakeInActiveSuccess(){
        Assert.assertTrue(vaccineTypeService.makeInActive(ids));
    }
    @Test
    public void testMakeInActiveFail1(){
        Assert.assertFalse(vaccineTypeService.makeInActive(null));
    }
    @Test
    public void testMakeInActiveFail2(){
        Assert.assertFalse(vaccineTypeService.makeInActive(new ArrayList<>()));
    }





}
