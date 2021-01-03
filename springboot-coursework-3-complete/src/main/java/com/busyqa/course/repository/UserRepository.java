package com.busyqa.course.repository;

import org.springframework.stereotype.Repository;

import com.busyqa.course.jpa.User;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {
  
}
