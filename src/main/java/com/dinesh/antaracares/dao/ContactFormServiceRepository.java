package com.dinesh.antaracares.dao;

import com.dinesh.antaracares.entity.ContactFormService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactFormServiceRepository extends JpaRepository<ContactFormService, Integer> {

    @Query("SELECT c FROM ContactFormService c WHERE " +
            "(:firstName = '' OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
            "(:lastName = '' OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
            "(:email = '' OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    List<ContactFormService> searchByFilters(@Param("firstName") String firstName,
                                             @Param("lastName") String lastName,
                                             @Param("email") String email);
}
