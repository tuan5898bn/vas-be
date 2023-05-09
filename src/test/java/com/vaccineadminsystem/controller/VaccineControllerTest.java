package com.vaccineadminsystem.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaccineadminsystem.dto.VaccineDto;
import com.vaccineadminsystem.entity.Vaccine;
import com.vaccineadminsystem.entity.VaccineType;
import com.vaccineadminsystem.exception.handle.AppExceptionHandle;
import com.vaccineadminsystem.repository.VaccineRepository;
import com.vaccineadminsystem.repository.VaccineTypeRepository;
import com.vaccineadminsystem.service.VaccineService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VaccineControllerTest {


    @Autowired
    private VaccineController vaccineController;

    @Autowired
    private AppExceptionHandle appExceptionHandle;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VaccineService vaccineService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private VaccineRepository vaccineRepository;

    @MockBean
    private VaccineTypeRepository vaccineTypeRepository;

    private MockMvc restMvc;

    @Before
    public void setUp() {
        this.restMvc =
            MockMvcBuilders.standaloneSetup(vaccineController)
                .setControllerAdvice(appExceptionHandle)
                .build();
    }

    @Test
    public void testGetAllVaccines() throws Exception {
        // Setup
        VaccineType vaccineType = new VaccineType();
        vaccineType.setStatus(true);
        vaccineType.setId("vct 1");
        vaccineType.setVaccineTypeName("vaccine type 1");
        Vaccine vaccine1 =
            new Vaccine("1321547854", true,
                "vaccine cum ga", vaccineType);
        Vaccine vaccine2 =
            new Vaccine("3256856856", true,
                "vaccine viem mang tui", vaccineType);
        Vaccine vaccine3 =
            new Vaccine("6523587458", true,
                "vaccine soi", vaccineType);
        List<Vaccine> vaccineList = Arrays.asList(vaccine1, vaccine2, vaccine3);
        when(vaccineRepository.findAll()).thenReturn(vaccineList);
        when(vaccineService.listAll()).thenReturn(modelMapper.map(vaccineList,
            ArrayList.class));
        // Run the test
        restMvc.perform(get("/vaccines")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].vaccineID").value("1321547854"))
            .andExpect(jsonPath("$.[0].active").value(true))
            .andExpect(jsonPath("$.[0].name").value("vaccine cum ga"));
    }

    @Test
    public void testCreateVaccineSuccess() throws Exception {
        // Setup
        VaccineType vaccineType = new VaccineType();
        vaccineType.setStatus(true);
        vaccineType.setId("vct 1");
        vaccineType.setVaccineTypeName("vaccine type 1");
        Vaccine vaccine =
            new Vaccine("2665895668", true, "vaccine cum ga", vaccineType
            );
        when(vaccineTypeRepository.findById("vct 1"))
            .thenReturn(Optional.of(vaccineType));
        when(vaccineRepository.save(vaccine)).thenReturn(vaccine);
        // Run the test
        final MockHttpServletResponse response =
            restMvc.perform(post("/vaccines")
                .content(objectMapper.writeValueAsString(
                    modelMapper.map(vaccine, VaccineDto.class)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("{\"message\":\"Create!\"}",
            response.getContentAsString());
    }

    @Test
    public void testCreateVaccineWithExistedVaccine()
        throws Exception {
        //set up
        VaccineType vaccineType = new VaccineType();
        vaccineType.setStatus(true);
        vaccineType.setId("vct 1");
        vaccineType.setVaccineTypeName("vaccine type 1");
        Vaccine vaccine =
            new Vaccine("2665895668", true, "vaccine cum ga", vaccineType
            );
        when(vaccineTypeRepository.findById("vct 1"))
            .thenReturn(Optional.of(vaccineType));
        when(vaccineRepository.findById("2665895668"))
            .thenReturn(Optional.of(vaccine));
        // Run the test
        final MockHttpServletResponse response =
            restMvc.perform(post("/vaccines")
                .content(objectMapper.writeValueAsString(
                    modelMapper.map(vaccine, VaccineDto.class)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
        assertEquals("{\"message\":\"Fail\"}",
            response.getContentAsString());
    }

    @Test
    public void testCreateVaccineWithNull() throws Exception {
        final MockHttpServletResponse response =
            restMvc.perform(post("/vaccines")
                .content((byte[]) null)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(400, response.getStatus());
    }


    @Test
    public void testGetVaccineByIdWithNonExistVaccine() throws Exception {
        when(vaccineRepository.findById("3658745256"))
            .thenReturn(Optional.empty());
        final MockHttpServletResponse response =
            restMvc.perform(get("/vaccines/{id}", "3658745256")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals("{\"message\":\"Not found vaccine!\"}",
            response.getContentAsString());
    }

    @Test
    public void testGetVaccineByIdWrongFormatId() throws Exception {
        when(vaccineRepository.findById("wrongformat"))
            .thenReturn(Optional.empty());
        final MockHttpServletResponse response =
            restMvc.perform(get("/vaccines/{id}", "wrongformat")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals("{\"message\":\"Not found vaccine!\"}",
            response.getContentAsString());
    }

    @Test
    public void testUpdateVaccineSuccess() throws Exception {
//        String id = "3658745256";
//        VaccineType vaccineType =
//            new VaccineType("vct 1", "vaccine type 1", true);
//        Vaccine vaccineData =
//            new Vaccine(id, true, "vaccine cum", vaccineType);
//        Vaccine oldVaccine =
//            new Vaccine(id, true, "", vaccineType);
//        String content = objectMapper.writeValueAsString(
//            modelMapper.map(vaccineData, VaccineDto.class));
//
//        final MockHttpServletResponse response =
//            restMvc.perform(put("/vaccines/{id}", id)
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();

    }

    @Test
    public void testUpdateVaccine() throws Exception {
        VaccineType vaccineType =
            new VaccineType("vct 1", "vaccine type 1", true);
        Vaccine vaccineData =
            new Vaccine("3658745256", true, "vaccine cum", vaccineType);
        VaccineDto mapD = modelMapper.map(vaccineData, VaccineDto.class);
        String content = objectMapper.writeValueAsString(mapD);
        // Setup
        when(vaccineService.update("3658745256", mapD))
            .thenReturn(true);

        // Run the test
        final MockHttpServletResponse response =
            restMvc.perform(put("/vaccines/{id}", "3658745256")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


    }

    @Test
    public void testVaccineWithInvalidDataVaccine() throws Exception {
        String id = "3658745256";
        final MockHttpServletResponse response =
            restMvc.perform(put("/vaccines/{id}", id)
                .content((byte[]) null)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testUpdateVaccineWithNullVaccineType() throws Exception {
        String id = "3658745256";
        VaccineType vaccineType = null;
        Vaccine vaccineData =
            new Vaccine(id, true, "vaccine cum", vaccineType);
        String content = objectMapper.writeValueAsString(
            modelMapper.map(vaccineData, VaccineDto.class));
        when(vaccineTypeRepository.findById("vct 1"))
            .thenReturn(Optional.empty());
        // Run the test
        final MockHttpServletResponse response =
            restMvc.perform(put("/vaccines/{id}", id)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"message\":\"Vaccine data is invalid\"}",
            response.getContentAsString());
    }
    @Test
    public void testImportVaccineSuccess() throws Exception {
        MockMultipartFile file
            = new MockMultipartFile(
            "file",
            "data vaccine.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            new ClassPathResource("data vaccine.xlsx").getInputStream()
        );
        restMvc.perform(multipart("/vaccines/import-file").file(file))
            .andExpect(status().isOk());

    }

    @Test
    public void testDeleteVaccineSuccess() throws Exception {
        String id = "3658745256";
        VaccineType vaccineType =
            new VaccineType("vct 1", "vaccine type 1", true);
        Vaccine vaccine =
            new Vaccine(id, true, "vaccine cum", vaccineType);
        when(vaccineRepository.findById(id)).thenReturn(Optional.of(vaccine));
        VaccineDto rsMap = modelMapper.map(vaccine,VaccineDto.class);
        when(vaccineService.get(id)).thenReturn(rsMap);
        final MockHttpServletResponse response =
            restMvc.perform(delete("/vaccines/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"message\":\"Delete!\"}",
            response.getContentAsString());
    }

    @Test
    public void testDeleteVaccineWithWrongFormat() throws Exception {
        String id = "abcdef";
        when(vaccineRepository.findById(id)).thenReturn(Optional.empty());
        final MockHttpServletResponse response =
            restMvc.perform(delete("/vaccines/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("{\"message\":\"Not found vaccine!\"}",
            response.getContentAsString());
    }

    @Test
    public void testDeleteVaccineWithEmpty() throws Exception {
        String id = "";
        when(vaccineRepository.findById(id)).thenReturn(Optional.empty());
        final MockHttpServletResponse response =
            restMvc.perform(delete("/vaccines/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());
    }


    @Test
    public void testImportVaccine_FailWrongType() throws Exception {
        MockMultipartFile file
            = new MockMultipartFile(
            "file",
            "test.txt",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            new ClassPathResource("test.txt").getInputStream()
        );
        restMvc.perform(multipart("/vaccines/import-file").file(file))
            .andExpect(status().isBadRequest());
    }
}
