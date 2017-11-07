package cl.citiaps.spring.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import cl.citiaps.spring.backend.entities.UserReTop;
import cl.citiaps.spring.backend.repository.UserReTopRepository;

@CrossOrigin
@RestController
@RequestMapping("/retop")
public class UserReTopService {
	
	@Autowired
	private UserReTopRepository userretoprepository;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<UserReTop> getAllGames() {
		return userretoprepository.findAll();
	}
}
