package com.vaccineadminsystem.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaccineadminsystem.dto.VaccineTypeDto;
import com.vaccineadminsystem.entity.VaccineType;
import com.vaccineadminsystem.exception.handle.AppExceptionHandle;
import com.vaccineadminsystem.repository.VaccineTypeRepository;

import com.vaccineadminsystem.service.VaccineTypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VaccineTypeControllerTest {
    @MockBean
    private VaccineTypeRepository vaccineTypeRepository;
    @MockBean
    private VaccineTypeService vaccineTypeService;
    @Autowired
    private AppExceptionHandle appExceptionHandle;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private VaccineTypeController vaccineTypeController;
    @MockBean
    private ModelMapper modelMapper;
    private MockMvc restMvc;

    VaccineType vaccineType1;
    VaccineType vaccineType2;
    VaccineType vaccineTypeFail;
    VaccineTypeDto vaccineTypeDtoFail;
    VaccineTypeDto vaccineTypeDto1;
    VaccineTypeDto vaccineTypeDto2;
    List<VaccineType> vaccineTypes;
    List<VaccineTypeDto> vaccineTypeDtos;

    @Before
    public void setUp() {
        vaccineType1 = new VaccineType("VCT01", "Adenoids", true);
        vaccineType2 = new VaccineType("VCT02", "Pneumococcal", false);
        vaccineTypeDto1 = new VaccineTypeDto("VCT01", "Adenoids", true);
        vaccineTypeDto2 = new VaccineTypeDto("VCT02", "Pneumococcal", false);
        vaccineTypeFail=new VaccineType("","VaccineTypeName",true);
        vaccineTypeDtoFail=new VaccineTypeDto("","",true);
        VaccineType vaccineType3 = new VaccineType("VCT03", "Pertussis", true);
        vaccineTypes = Arrays.asList(vaccineType1, vaccineType2, vaccineType3);
        vaccineTypeDtos = Arrays.asList(vaccineTypeDto1, vaccineTypeDto2);
        this.restMvc =
                MockMvcBuilders.standaloneSetup(vaccineTypeController)
                        .setControllerAdvice(appExceptionHandle)
                        .build();
    }


    @Test
    public void testGetAllVaccineType1() throws Exception {
        when(vaccineTypeRepository.findAll()).thenReturn(vaccineTypes);
        when(modelMapper.map(vaccineType1, VaccineTypeDto.class)).thenReturn(vaccineTypeDto1);
        when(vaccineTypeService.findAllVaccineType()).thenReturn(vaccineTypeDtos);
        restMvc.perform(get("/vaccine-type/get")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("VCT01"))
                .andExpect(jsonPath("$[0].status").value(true))
                .andExpect(jsonPath("$[0].vaccineTypeName").value("Adenoids"));

    }
    @Test
    public void testGetAllVaccineType2() throws Exception {
        when(vaccineTypeRepository.findAll()).thenReturn(new ArrayList<>());
        when(vaccineTypeService.findAllVaccineType()).thenReturn(new ArrayList<>());
        final MockHttpServletResponse response =
                restMvc.perform(get("/vaccine-type/get")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"message\":\"Vaccine Type List don't have any record\"}", response.getContentAsString());

    }

    @Test
    public void testSaveVaccineTypeSuccess() throws Exception {
        when(vaccineTypeRepository.findById(vaccineTypeDto1.getId())).thenReturn(Optional.empty());
        when(vaccineTypeRepository.save(vaccineType1)).thenReturn(vaccineType1);
        when(modelMapper.map(vaccineTypeDto1, VaccineType.class)).thenReturn(vaccineType1);
        when(modelMapper.map(vaccineType1, VaccineTypeDto.class)).thenReturn(vaccineTypeDto1);
        // Run the test
        final MockHttpServletResponse response =
                restMvc.perform(post("/vaccine-type/add")
                        .content(objectMapper.writeValueAsString(
                                modelMapper.map(vaccineType1, VaccineTypeDto.class)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("{\"message\":\"Created Success\"}", response.getContentAsString());
    }
    @Test
    public void testSaveVaccineTypeFail() throws Exception {
        // Run the test
        final MockHttpServletResponse response =
                restMvc.perform(post("/vaccine-type/add")
                        .content(objectMapper.writeValueAsString(vaccineTypeDtoFail))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        // Verify the results
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
        assertEquals("{\"message\":\"Vaccine Type is not valid\"}", response.getContentAsString());
    }
    @Test
    public void testGetVaccineTypeByIdSuccess() throws Exception {
        when(vaccineTypeRepository.findById(vaccineTypeDto1.getId())).thenReturn(Optional.of(vaccineType1));
        when(vaccineTypeService.findById(vaccineTypeDto1.getId())).thenReturn(vaccineTypeDto1);
        when(modelMapper.map(vaccineType1, VaccineTypeDto.class)).thenReturn(vaccineTypeDto1);
        // Run the test
        final MockHttpServletResponse response =
                restMvc.perform(get("/vaccine-type/{id}",vaccineTypeDto1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        // Verify the results
        assertEquals(objectMapper.writeValueAsString(vaccineTypeDto1),response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    public void testGetVaccineTypeByIdFail() throws Exception {
        when(vaccineTypeRepository.findById(vaccineType1.getId())).thenReturn(Optional.empty());
        // Run the test
        final MockHttpServletResponse response =
                restMvc.perform(get("/vaccine-type/{id}",vaccineType1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
        // Verify the results
        assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatus());
        assertEquals("{\"message\":\"Vaccine Type is not found\"}", response.getContentAsString());
    }

}
