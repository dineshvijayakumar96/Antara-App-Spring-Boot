package com.dinesh.antaracares.dao;

import com.dinesh.antaracares.entity.ContactFormHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactFormHomeRepository extends JpaRepository<ContactFormHome, Integer> {

    @Query("SELECT c FROM ContactFormHome c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    List<ContactFormHome> searchByFilters(@Param("name") String name, @Param("email") String email);
}
