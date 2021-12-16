//package com.example.resource;
//
//import java.util.Collection;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.model.Game;
//import com.example.service.IService;
//
//@RestController
//@RequestMapping("/games")
//@CrossOrigin(origins="http://localhost:3000")
//public class GameResource implements Resource<Game> {
//	
//	@Autowired
//	private IService<Game> gameservice;
//	
//	@Override
//	public ResponseEntity<Collection<Game>> findAll(){
//		return new ResponseEntity<>(gameservice.findAll(),HttpStatus.OK);
//
//			}
//
//	@Override
//	public ResponseEntity<Game> findById(Long id) {
//		return new ResponseEntity<>(gameservice.findById(id),HttpStatus.OK);
//
//	}
//
//	@Override
//	public ResponseEntity<Game> save(Game game) {
//		return new ResponseEntity<>(gameservice.saveOrUpdate(game),HttpStatus.CHECKPOINT);
//	}
//
//	@Override
//	public ResponseEntity<Game> update(Game game) {
//		return new ResponseEntity<>(gameservice.saveOrUpdate(game),HttpStatus.OK);
//		
//	}
//
//	@Override
//	public ResponseEntity<String> deleteById(Long id) {
//		return new ResponseEntity<>(gameservice.deleteById(id),HttpStatus.OK);
//		
//	}
//	}
