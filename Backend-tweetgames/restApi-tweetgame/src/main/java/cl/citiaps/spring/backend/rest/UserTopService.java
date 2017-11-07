package cl.citiaps.spring.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import cl.citiaps.spring.backend.entities.UserTop;
import cl.citiaps.spring.backend.repository.UserTopRepository;

@CrossOrigin
@RestController
@RequestMapping("/top")
public class UserTopService {
	
	@Autowired
	private UserTopRepository usertoprepository;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<UserTop> getAllGames() {
		return usertoprepository.findAllByrankAsc();
	}
}
