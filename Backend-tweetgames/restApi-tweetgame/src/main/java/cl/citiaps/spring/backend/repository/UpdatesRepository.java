package cl.citiaps.spring.backend.repository;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import cl.citiaps.spring.backend.entities.Updates;

public interface UpdatesRepository extends PagingAndSortingRepository<Updates, Integer>{

	//List<Updates> findAllByOrderByIdDesc();
	
	@Query("SELECT u FROM Updates u ORDER BY fecha DESC")
	List<Updates> findAllByfechaDesc();
}
