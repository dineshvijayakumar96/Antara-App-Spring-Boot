package com.dinesh.antaracares.dao;

import com.dinesh.antaracares.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
}
