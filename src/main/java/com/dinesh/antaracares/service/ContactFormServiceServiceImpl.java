package com.dinesh.antaracares.service;

import com.dinesh.antaracares.dao.ContactFormServiceRepository;
import com.dinesh.antaracares.entity.ContactFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactFormServiceServiceImpl implements ContactFormServiceService {

    private ContactFormServiceRepository contactFormServiceRepository;

    @Autowired
    public ContactFormServiceServiceImpl(ContactFormServiceRepository contactFormServiceRepository) {
        this.contactFormServiceRepository = contactFormServiceRepository;
    }

    @Override
    public List<ContactFormService> searchByFilters(String firstName,String lastName, String email) {
        return contactFormServiceRepository.searchByFilters(firstName, lastName, email);
    }

    @Override
    public Page<ContactFormService> findAll(Pageable pageable) {
        return contactFormServiceRepository.findAll(pageable);
    }

    @Override
    public ContactFormService findById(int theId) {
        Optional<ContactFormService> dataId = contactFormServiceRepository.findById(theId);

        ContactFormService theContactFormService = null;

        if (dataId.isPresent()) {
            theContactFormService = dataId.get();
        } else {
            throw new RuntimeException("No Data by the ID: " + theId + " has been found!");
        }

        return theContactFormService;
    }

    @Override
    public ContactFormService save(ContactFormService theContactFormService) {
        return contactFormServiceRepository.save(theContactFormService);
    }

    @Override
    public String deleteById(int theId) {

        Optional<ContactFormService> dataId = contactFormServiceRepository.findById(theId);

        if (dataId.isPresent()) {
            contactFormServiceRepository.deleteById(theId);
            return "Data by ID: " + theId + " has been deleted successfully.";
        } else {
            throw new RuntimeException("No Data by the ID: " + theId + " has been found!");
        }
    }
}
