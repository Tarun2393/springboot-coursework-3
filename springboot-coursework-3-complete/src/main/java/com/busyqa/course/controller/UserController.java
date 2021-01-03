package com.busyqa.course.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.busyqa.course.jpa.Role;
import com.busyqa.course.jpa.User;
import com.busyqa.course.service.RoleService;
import com.busyqa.course.service.UserService;

@Controller
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	/*
	 * The method below is executed every time this Controller is called. It adds 
	 * the roles list to the Model so the roles list is always sent to the View.
	 */
    @ModelAttribute("list_roles")
    public List<Role> addListRoles() {
    	
    	logger.debug("Listing Roles!!!");
    	
        return this.roleService.listRoles();
    }
    
	@GetMapping(value ={"/", "/index"})
	public String index() {
				
		logger.debug("Show Index!!!");
				
		return "index";
	}
	
	@GetMapping(value = "/list_users")
	public ModelAndView listUsers(){
		
		logger.debug("Listing Users!!!");
		
        List<User> users = this.userService.listUsers();
       
        users.forEach(u -> logger.debug(u.toString()));
       
        return new ModelAndView("list_users","users",users);
    }	
	
	@GetMapping(value = "/input_user")
	public String inputUser(){
		
		logger.debug("Show Input User!!!");
		
        return "input_user";
    }	
	
	@PostMapping(value = "/create_user")
    public String createUser(HttpServletRequest request) throws ParseException {

		logger.debug("Creating User!!!");
		
        String firstName = request.getParameter("first");
        String lastName = request.getParameter("last");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String birth = request.getParameter("birth");
        
        String idRole = request.getParameter("id_role");
        
        this.userService.createUser(firstName,lastName,username,password,birth,idRole);

        return "redirect:/list_users";
    }	

	@GetMapping(value = "/show_user")
    public ModelAndView showUser(@RequestParam("id") int id) {

		logger.debug("Show User!!!");
		
        User user = this.userService.findUser(id);

        return new ModelAndView("show_user","user",user);
    }	
	
	@PostMapping(value = "/update_user")
    public String updateUser(HttpServletRequest request) throws ParseException {
       
		logger.debug("Updating User!!!");
		
        String idUser = request.getParameter("id");
        String firstName = request.getParameter("first");
        String lastName = request.getParameter("last");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String birth = request.getParameter("birth");
        
        String idRole = request.getParameter("id_role");

        this.userService.updateUser(idUser,firstName,lastName,username,password,birth, idRole);

        return "redirect:/list_users";
    }

	@GetMapping(value = "/delete_user")
    public String deleteUser(@RequestParam("id") int id){

		logger.debug("Deleting User!!!");
		
        this.userService.deleteUser(id);

        return "redirect:/list_users";
    }    
}
