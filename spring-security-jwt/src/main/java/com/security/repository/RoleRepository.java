package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.security.models.Roles;

public interface RoleRepository extends JpaRepository<Roles, Integer> {

}
