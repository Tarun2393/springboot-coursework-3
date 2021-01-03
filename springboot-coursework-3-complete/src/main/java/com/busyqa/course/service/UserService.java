package com.busyqa.course.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.busyqa.course.jpa.Role;
import com.busyqa.course.jpa.User;
import com.busyqa.course.jpa.UserRole;
import com.busyqa.course.repository.RoleRepository;
import com.busyqa.course.repository.UserRepository;
import com.busyqa.course.repository.UserRoleRepository;

@Service
public class UserService{
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
    @Transactional(readOnly=true)
    public List<User> listUsers() {
        return this.userRepository.findAll();
    }

    @Transactional(readOnly=true)
    public User findUser(int idUser){
        return this.userRepository.findById(idUser).get();
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public void updateUser(String idUser, String firstName, String lastName,
    		                String username, String password, String birth, String idRole) throws ParseException{

    	Role role = this.roleRepository.findById(this.parseId(idRole)).get(); /* Get the Role from the DB*/
    	
    	User user = this.findUser(Integer.parseInt(idUser)); /* Get the User from the DB*/
    	
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setPassword(password);
		user.setBirth(this.parseDate(birth));

		this.userRoleRepository.deleteAll(user.getUserRoles()); /* Delete previous UserRole from DB*/
		
		UserRole userRole = this.createUserRole(user, role); /* Create new UserRole in RAM*/

		user.getUserRoles().clear();
		user.addUserRole(userRole); /* Add new UserRole to the User*/
		
		this.userRepository.save(user); /* Update the User in the DB */
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void createUser(String firstName, String lastName, String username, 
    		                String password, String birth, String idRole) throws ParseException{
		    
    	Role role = this.roleRepository.findById(this.parseId(idRole)).get(); /* Get the Role from the DB*/
    	
    	User user = new User(); /* Create new User in RAM*/
    	  	
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setPassword(password);
		user.setBirth(this.parseDate(birth));
		user.setStatus("A");  /* Always created as Active */
		
		UserRole userRole = this.createUserRole(user, role); /* Create new UserRole in RAM*/
		
		user.setUserRoles(new ArrayList<>()); /* Initialize the UserRoles of the User */
		user.addUserRole(userRole); /* Add new UserRole to the User*/
		
		this.userRepository.save(user); /* Create the User in the DB */
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void deleteUser(int idUser){
    	
        this.userRepository.deleteById(idUser);
    }
 
    
    private UserRole createUserRole(User user, Role role) {
    	
    	UserRole userRole = new UserRole();
    	
    	userRole.setUser(user);
        userRole.setRole(role);
        
        return userRole;
    }
    
	private int parseId(String id) {
		return (id==null)?0:Integer.parseInt(id.trim());
	}
	
	private Date parseDate(String date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.parse(date);
    }
}
