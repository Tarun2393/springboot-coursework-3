package com.busyqa.course.repository;

import org.springframework.stereotype.Repository;

import com.busyqa.course.jpa.UserRole;

@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, Integer> {
  
}
