package com.example.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.model.UserList;

@Repository
public interface UserListRepository extends JpaRepository<UserList,Long> {
	List<UserList> findAllByUserIdOrderByRatingDesc(Long userId);
	
	Boolean existsByUserIdAndGameId(Long userId,Long gameId);
	
	UserList findByUserIdAndGameId(Long userId,Long gameId);

	List<UserList> findAllByGameId(Long GameId);
	
	List<UserList> findByStatusAndUserId(String status, Long userId);
	
	List<UserList> findByStatus(String status);
	
	Boolean existsByUserIdAndGameTitle(Long userId,String title);

	List<UserList> findAllByUserId(Long userId);


}
