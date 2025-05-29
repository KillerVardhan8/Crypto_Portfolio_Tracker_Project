package com.cryptotracker.CryptoTrackerApplication.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cryptotracker.CryptoTrackerApplication.Mappers.UserMapper;
import com.cryptotracker.CryptoTrackerApplication.dto.UserDTO;
import com.cryptotracker.CryptoTrackerApplication.entity.Role;
import com.cryptotracker.CryptoTrackerApplication.entity.User;
import com.cryptotracker.CryptoTrackerApplication.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
	@Autowired
    private  UserRepository userRepository;
	
	// the method  returns all the users ,or else if the role is not Admin ,then the method throws exception
	public  List<UserDTO> getAllUser(){
		
		return userRepository.findAll().stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());			
	}
	
	//This method finds the user by userId, if found checks if the role is Admin,if Admin ,then updates the user role and if user is not Admin ,then the method throws exceptions
	 public boolean updateUserRole(String reqPersonMail,Long userId, String role){
	    	try {
	        User u=userRepository.findByEmail(reqPersonMail).orElseThrow();
	        User user = userRepository.findById(userId).orElseThrow();
	        if(u.getRole() == Role.ADMIN) {
	        	user.setRole(Role.valueOf(role.toUpperCase()));
	            userRepository.save(u);
	            return true;
	        }
	    	}
	        catch(Exception e) {
	        	System.out.println("Error while updating user role: " + e.getMessage());
	        }
	        return false;
	    }
    
    //Deletes the user only when the role is Admin 
    public boolean deleteUser(String reqPersonMail,Long userid) {
    	try {
    		User u=userRepository.findByEmail(reqPersonMail).orElseThrow();
    		
    		if(u.getRole() == Role.ADMIN) {
    			userRepository.deleteById(userid);
        		return true;
    		}
    		
    	
    	}    	
    		catch(Exception e) {
    			System.out.println("Error while deleting: "+e.getMessage());
    			
    		}
    	return false;
    }
    
    //Finds the user by user email
    public User getbyEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
    
}