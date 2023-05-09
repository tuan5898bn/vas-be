package com.vaccineadminsystem.repository;

import com.vaccineadminsystem.dto.ReportVaccineDto;
import com.vaccineadminsystem.dto.ReportVaccineTypeChart;
import com.vaccineadminsystem.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface VaccineTypeRepository extends JpaRepository<VaccineType, String> {
    List<VaccineType> findByStatus(boolean status);


	@Query("SELECT new com.vaccineadminsystem.dto.ReportVaccineDto(vac.vaccineType.id, vacType.vaccineTypeName, vacType.status, COUNT(vac.vaccineID), vac.origin) \n" +
			"FROM Vaccine vac \n" +
			"JOIN VaccineType vacType ON vac.vaccineType.id = vacType.id \n" +
			"GROUP BY vac.vaccineType.id, vacType.vaccineTypeName, vacType.status, vac.origin \n" +
			"ORDER BY vac.vaccineType.id")
	List<ReportVaccineDto> reportVaccineByType();

    @Query("SELECT new com.vaccineadminsystem.dto.ReportVaccineTypeChart(count(vc), vct.vaccineTypeName) from Vaccine vc JOIN VaccineType vct " +
            "ON vc.vaccineType.id = vct.id " +
            "GROUP BY vct.vaccineTypeName")
    List<ReportVaccineTypeChart> reportVaccineType();

}
