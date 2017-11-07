package cl.citiaps.spring.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import cl.citiaps.spring.backend.entities.GameResume;

public interface GameResumeRepository extends PagingAndSortingRepository<GameResume, Integer>{

}
