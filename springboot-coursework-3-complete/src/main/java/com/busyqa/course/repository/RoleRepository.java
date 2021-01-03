package com.busyqa.course.repository;

import org.springframework.stereotype.Repository;

import com.busyqa.course.jpa.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role, Integer> {
  
}
