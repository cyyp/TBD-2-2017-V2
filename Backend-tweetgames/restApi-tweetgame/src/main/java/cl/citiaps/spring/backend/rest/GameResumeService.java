package cl.citiaps.spring.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import cl.citiaps.spring.backend.entities.GameResume;
import cl.citiaps.spring.backend.entities.Game;
import cl.citiaps.spring.backend.repository.GameResumeRepository;

@CrossOrigin
@RestController
@RequestMapping("/resume")
public class GameResumeService {
	
	@Autowired
	private GameResumeRepository gameresumerepository;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<GameResume> getAllGameResume() {
		return gameresumerepository.findAll();
	}
	
	
}
