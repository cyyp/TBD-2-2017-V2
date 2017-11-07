package cl.citiaps.spring.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cl.citiaps.spring.backend.entities.UserTop;

public interface UserTopRepository extends PagingAndSortingRepository<UserTop, Integer>{
	
	
	@Query("SELECT ut FROM UserTop ut ORDER BY rank ASC")
	List<UserTop> findAllByrankAsc();

}
