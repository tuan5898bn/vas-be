package com.vaccineadminsystem.repository;

import com.vaccineadminsystem.entity.Vaccine;
import com.vaccineadminsystem.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VaccineRepository extends JpaRepository<Vaccine,String> {

	@Query("UPDATE Vaccine v SET v.active = false WHERE v.vaccineID = :id")
	@Modifying
	void updateVaccineStatusWithId(@Param("id") String id);

	@Query("SELECT v.vaccineID FROM Vaccine v")
	List<String> findVaccineIDs();

	List<Vaccine> findVaccineByVaccineType(VaccineType vaccineType);
	
	List<Vaccine> findByActive(boolean status);
}
