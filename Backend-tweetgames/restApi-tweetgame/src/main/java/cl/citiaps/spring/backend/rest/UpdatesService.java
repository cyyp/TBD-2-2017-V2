package cl.citiaps.spring.backend.rest;

import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.citiaps.spring.backend.entities.Updates;
import cl.citiaps.spring.backend.repository.UpdatesRepository;
import java.sql.Timestamp;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/updates")
public class UpdatesService {

	
	@Autowired
	private UpdatesRepository updatesrepository;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Iterable<Updates> getAllUpdates(){
		return updatesrepository.findAll();
	}
	
	
	@RequestMapping(value = "/{last}", method = RequestMethod.GET)
	@ResponseBody
	public Updates getlastUpdate(){
		
		//List<Updates> up = updatesrepository.findAllByOrderByIdDesc();
		List<Updates> up = updatesrepository.findAllByfechaDesc();
		return up.get(0);	
	}
}
