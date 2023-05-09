package com.vaccineadminsystem.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.vaccineadminsystem.exception.handle.AppExceptionHandle;
import com.vaccineadminsystem.util.ErrorMess;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageTest {

    private MockMvc restMvc;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private AppExceptionHandle appExceptionHandle;

    @Before
    public void setUp() throws Exception {
        this.restMvc =
            MockMvcBuilders.standaloneSetup(employeeController)
                .setControllerAdvice(appExceptionHandle)
                .build();
    }

    @Test
    public void testSaveImageSuccess() throws Exception {

        MockMultipartFile file
            = new MockMultipartFile(
            "image",
            "image.JPG",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello!".getBytes()
        );

        restMvc.perform(multipart("/save-image").file(file))
            .andExpect(status().isOk());

    }

    @Test
    public void testSaveImageFailWithNonImageFile(){


    }

    @Test
    public void testSaveImageFailWithNullMultipart() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", (byte[]) null);
        restMvc.perform(multipart("/save-image").file(file))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.message").value(ErrorMess.INVALID_IMAGE));
    }





}
