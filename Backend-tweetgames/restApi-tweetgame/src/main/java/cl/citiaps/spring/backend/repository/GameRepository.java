package cl.citiaps.spring.backend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import cl.citiaps.spring.backend.entities.Game;

public interface GameRepository extends PagingAndSortingRepository<Game, Integer>{

}
