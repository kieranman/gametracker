package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Game;
import com.example.model.UserList;
import com.example.repository.GameRepository;
import com.example.repository.UserListRepository;

@Service
public class UserListService {
    
	@Autowired
    private UserListRepository userListRepository;

    public List<UserListDto> listUserList() {
        List<UserList> userLists = userListRepository.findAll();
        List<UserListDto> userListDtos = new ArrayList<>();
        for(UserList userList : userLists) {
        	UserListDto userListDto = getDtoFromUserList(userList);
            userListDtos.add(userListDto);
        }
        return userListDtos;
    }

    public static UserListDto getDtoFromUserList(UserList userList) {
    	UserListDto userListDto = new UserListDto(userList);
        return userListDto;
    }

   

}