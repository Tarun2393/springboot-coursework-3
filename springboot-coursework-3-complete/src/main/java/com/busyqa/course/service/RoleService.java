package com.busyqa.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busyqa.course.jpa.Role;
import com.busyqa.course.repository.RoleRepository;

@Service
public class RoleService{
	
	@Autowired
	RoleRepository roleRepository;

    @Transactional(readOnly=true)
    public List<Role> listRoles() {
        return this.roleRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Role findRole(int idRole){
        return this.roleRepository.findById(idRole).get();
    }
}
