package com.vaccineadminsystem.converter;

import com.vaccineadminsystem.dto.VaccineDto;
import com.vaccineadminsystem.entity.Vaccine;
import com.vaccineadminsystem.entity.VaccineType;
import com.vaccineadminsystem.repository.VaccineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VaccineConverter {
	
	@Autowired
	private VaccineTypeRepository vaccineTypeRepo;
	
	public VaccineDto toDto(Vaccine entity) {
		VaccineDto result = new VaccineDto();
		result.setVaccineID(entity.getVaccineID());
		result.setActive(entity.getActive());
		result.setName(entity.getName());
		result.setUsage(entity.getUsage());
		result.setIndication(entity.getIndication());
		result.setContraindication(entity.getContraindication());
		result.setNumberOfInjection(entity.getNumberOfInjection());
		result.setNextTimeStart(entity.getNextTimeStart());
		result.setNextTimeEnd(entity.getNextTimeEnd());
		result.setOrigin(entity.getOrigin());
		result.setVaccineTypeId(entity.getVaccineType().getId());
		result.setVaccineTypeName(entity.getVaccineType().getVaccineTypeName());
		return result;
	}
	
	public Vaccine toEntity(VaccineDto dto) {
		Vaccine result = new Vaccine();
		result.setVaccineID(dto.getVaccineID());
		result.setActive(dto.getActive());
		result.setName(dto.getName());
		result.setUsage(dto.getUsage());
		result.setIndication(dto.getIndication());
		result.setContraindication(dto.getContraindication());
		result.setNumberOfInjection(dto.getNumberOfInjection());
		result.setNextTimeStart(dto.getNextTimeStart());
		result.setNextTimeEnd(dto.getNextTimeEnd());
		result.setOrigin(dto.getOrigin());
		return result;
	}
	
	public Vaccine toEntity(Vaccine result, VaccineDto dto) {
		Optional<VaccineType> optVaccineType = vaccineTypeRepo.findById(dto.getVaccineTypeId());
		
		result.setActive(dto.getActive());
		result.setName(dto.getName());
		result.setUsage(dto.getUsage());
		result.setIndication(dto.getIndication());
		result.setContraindication(dto.getContraindication());
		result.setVaccineType(optVaccineType.isPresent() ? optVaccineType.get() : null);
		result.setNumberOfInjection(dto.getNumberOfInjection());
		result.setNextTimeStart(dto.getNextTimeStart());
		result.setNextTimeEnd(dto.getNextTimeEnd());
		result.setOrigin(dto.getOrigin());
		return result;
	}
}
