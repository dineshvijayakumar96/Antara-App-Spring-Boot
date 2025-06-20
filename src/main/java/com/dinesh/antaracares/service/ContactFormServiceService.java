package com.dinesh.antaracares.service;

import com.dinesh.antaracares.entity.ContactFormService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactFormServiceService {

    List<ContactFormService> searchByFilters(String firstName,String lastName, String email);

    Page<ContactFormService> findAll(Pageable pageable);

    ContactFormService findById(int theId);

    ContactFormService save(ContactFormService theContactFormService);

    String deleteById(int theId);
}
