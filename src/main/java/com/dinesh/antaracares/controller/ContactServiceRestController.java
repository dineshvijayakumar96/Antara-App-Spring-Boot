package com.dinesh.antaracares.controller;

import com.dinesh.antaracares.entity.ContactFormService;
import com.dinesh.antaracares.service.ContactFormServiceService;
import com.dinesh.antaracares.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/service-contact-forms")
public class ContactServiceRestController {

    private ContactFormServiceService theContactFormServiceService;

    @Autowired
    private EmailService emailService;

    @Autowired
    public ContactServiceRestController(ContactFormServiceService theContactFormServiceService) {
        this.theContactFormServiceService = theContactFormServiceService;
    }

    @GetMapping("")
    public ResponseEntity<List<ContactFormService>> getContactsForReactAdmin(
            @RequestParam(value = "filter", required = false) String filterParam,
            @RequestParam(value = "range", required = false) String rangeParam,
            @RequestParam(value = "sort", required = false) String sortParam
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, String> filters = new HashMap<>();
            if (filterParam != null && !filterParam.isBlank()) {
                filters = objectMapper.readValue(filterParam, Map.class);
            }

            String firstName = filters.getOrDefault("firstName", "");
            String lastName = filters.getOrDefault("lastName", "");
            String name = filters.getOrDefault("name", "");
            String email = filters.getOrDefault("email", "");

            if (name != null && !name.isBlank()) {
                String[] parts = name.split(" ", 2);
                firstName = parts[0];
                if (parts.length > 1) {
                    lastName = parts[1];
                }
            }

            List<ContactFormService> allFiltered = theContactFormServiceService.searchByFilters(firstName, lastName, email);

            int start = 0, end = allFiltered.size();
            if (rangeParam != null && !rangeParam.isBlank()) {
                int[] range = objectMapper.readValue(rangeParam, int[].class);
                start = Math.max(0, range[0]);
                end = Math.min(range[1] + 1, allFiltered.size());
            }

            List<ContactFormService> paginated = allFiltered.subList(start, end);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Range", "service-contact-forms " + start + "-" + (end - 1) + "/" + allFiltered.size());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(paginated);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{contactId}")
    public ContactFormService getContact(@PathVariable int contactId) {
        return theContactFormServiceService.findById(contactId);
    }

    @PostMapping("")
    public ContactFormService addContact(@RequestBody ContactFormService theContactFormService) {

        theContactFormService.setId(0);
        ContactFormService dbSavedForm = theContactFormServiceService.save(theContactFormService);

        try {
            Map<String, Object> variables = Map.of(
                    "firstName", dbSavedForm.getFirstName(),
                    "lastName", dbSavedForm.getLastName(),
                    "email", dbSavedForm.getEmail(),
                    "message", dbSavedForm.getMessage()
            );
            emailService.sendHtmlEmail(
                    "dineshjustin95@gmail.com",
                    "New Service Contact Form Submission",
                    "service-contact-email",
                    variables
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> variables = Map.of(
                    "name", dbSavedForm.getFirstName() + " " + dbSavedForm.getFirstName()
            );
            emailService.sendHtmlEmail(
                    dbSavedForm.getEmail(),
                    "Thank You for your Submission",
                    "reply-thankyou-email",
                    variables
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @PutMapping("{contactId}")
    public ContactFormService updateContact(@RequestBody ContactFormService theContactFormService, @PathVariable int contactId) {
        theContactFormService.setId(contactId);
        return theContactFormServiceService.save(theContactFormService);
    }

    @DeleteMapping("/{contactId}")
    public void deleteContact(@PathVariable int contactId) {
        theContactFormServiceService.deleteById(contactId);
    }
}
