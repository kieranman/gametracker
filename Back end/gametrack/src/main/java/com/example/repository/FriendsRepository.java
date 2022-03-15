package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Friends;
import com.example.model.User;

public interface FriendsRepository extends JpaRepository<Friends,Long> {
    
	boolean existsByFirstUserAndSecondUser(User userObj,User friendObj);
	List<Friends> findByfirstUserIdAndRequestStatus(Long id,String status);
	List<Friends> findBysecondUserIdAndRequestStatus(Long id,String status);




}

