package com.arash.edu.statemachinedemo.repository;

import com.arash.edu.statemachinedemo.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<MyUser, UUID> {
}
