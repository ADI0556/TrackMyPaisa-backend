package com.empower.expense.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.empower.expense.model.Users;
import com.empower.expense.repository.UserRepository;

@Service
public class UserService  implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	

	public List<Users> findAll() {
		return userRepository.findAll();
	}

	public Users findById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public Optional<Users> findByEmailId(String emailId) {
		return userRepository.findByEmailId(emailId);
	}
	
	public Users findByName(String name) {
		return userRepository.findByName(name);
	}
	
	public Users save(Users users) {
		return userRepository.save(users);
	}

	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Users> temp = userRepository.findByEmailId(emailId);
		User user=null;
		if(temp.isPresent())
		{
			Users myUser = temp.get();
			user=new User(myUser.getEmailId(), myUser.getPassword(), new ArrayList<>());
		}
		return user;
	}

}
