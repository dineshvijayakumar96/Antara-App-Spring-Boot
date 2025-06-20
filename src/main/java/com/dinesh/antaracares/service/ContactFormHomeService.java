package com.dinesh.antaracares.service;

import com.dinesh.antaracares.entity.ContactFormHome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactFormHomeService {

    List<ContactFormHome> searchByFilters(String name, String email);

    Page<ContactFormHome> findAll(Pageable pageable);

    ContactFormHome findById(int theId);

    ContactFormHome save(ContactFormHome theContactFormHome);

    String deleteById(int theId);
}
