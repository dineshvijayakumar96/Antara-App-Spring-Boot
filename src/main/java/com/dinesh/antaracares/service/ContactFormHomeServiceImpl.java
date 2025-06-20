package com.dinesh.antaracares.service;

import com.dinesh.antaracares.dao.ContactFormHomeRepository;
import com.dinesh.antaracares.entity.ContactFormHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactFormHomeServiceImpl implements ContactFormHomeService {

    private ContactFormHomeRepository contactFormHomeRepositor;

    @Autowired
    public ContactFormHomeServiceImpl(ContactFormHomeRepository contactFormHomeRepositor) {
        this.contactFormHomeRepositor = contactFormHomeRepositor;
    }

    @Override
    public List<ContactFormHome> searchByFilters(String name, String email) {
        return contactFormHomeRepositor.searchByFilters(name, email);
    }

    @Override
    public Page<ContactFormHome> findAll(Pageable pageable) {
        return contactFormHomeRepositor.findAll(pageable);
    }

    @Override
    public ContactFormHome findById(int theId) {

        Optional<ContactFormHome> dataById = contactFormHomeRepositor.findById(theId);

        ContactFormHome theContactFormHome = null;

        if (dataById.isPresent()) {
            theContactFormHome = dataById.get();
        } else {
            throw new RuntimeException("No Data by the ID: " + theId + " has been found!");
        }

        return theContactFormHome;
    }

    @Override
    public ContactFormHome save(ContactFormHome theContactFormHome) {
        return contactFormHomeRepositor.save(theContactFormHome);
    }

    @Override
    public String deleteById(int theId) {

        Optional<ContactFormHome> dataById = contactFormHomeRepositor.findById(theId);

        if (dataById.isPresent()) {
            contactFormHomeRepositor.deleteById(theId);
            return "Data by ID: " + theId + " has been deleted successfully.";
        } else {
            throw new RuntimeException("No Data by the ID: " + theId + " has been found!");
        }
    }
}
