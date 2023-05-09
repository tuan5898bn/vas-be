package com.vaccineadminsystem.service;

import com.vaccineadminsystem.dto.VaccineDto;
import com.vaccineadminsystem.entity.Vaccine;
import com.vaccineadminsystem.exception.ImportFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VaccineService {
	List<VaccineDto> listAll();
	boolean save(VaccineDto vaccineDTO);
	boolean update(String vaccineID, VaccineDto vaccineDTO);
	VaccineDto get(String id);
	void delete(String id);
	boolean updateStatus(List<String> ids);
	List<VaccineDto> findVaccinesByStatus(boolean b);
	List<Vaccine> importData(MultipartFile file) throws IOException, ImportFileException; // viet controlAdvice cho thang ImportFileException
}
