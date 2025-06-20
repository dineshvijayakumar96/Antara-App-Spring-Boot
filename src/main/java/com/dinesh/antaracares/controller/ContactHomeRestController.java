package com.dinesh.antaracares.controller;

import com.dinesh.antaracares.entity.ContactFormHome;
import com.dinesh.antaracares.service.ContactFormHomeService;
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
@RequestMapping("api/home-contact-forms")
public class ContactHomeRestController {

    private ContactFormHomeService thecontactFormHomeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    public ContactHomeRestController(ContactFormHomeService contactFormHomeService) {
        this.thecontactFormHomeService = contactFormHomeService;
    }

    @GetMapping("")
    public ResponseEntity<List<ContactFormHome>> getContactsForReactAdmin(
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

            String name = filters.getOrDefault("name", "");
            String email = filters.getOrDefault("email", "");

            List<ContactFormHome> allFiltered = thecontactFormHomeService.searchByFilters(name, email);

            int start = 0, end = allFiltered.size();
            if (rangeParam != null && !rangeParam.isBlank()) {
                int[] range = objectMapper.readValue(rangeParam, int[].class);
                start = Math.max(0, range[0]);
                end = Math.min(range[1] + 1, allFiltered.size());
            }

            List<ContactFormHome> paginated = allFiltered.subList(start, end);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Range", "home-contact-forms " + start + "-" + (end - 1) + "/" + allFiltered.size());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(paginated);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{contactId}")
    public ContactFormHome contactById(@PathVariable int contactId) {
        return thecontactFormHomeService.findById(contactId);
    }

    @PostMapping("")
    public ContactFormHome addContact(@RequestBody ContactFormHome thecontactFormHome) {

        thecontactFormHome.setId(0);
        ContactFormHome dbSavedForm = thecontactFormHomeService.save(thecontactFormHome);

        try {
            Map<String, Object> variables = Map.of(
                    "name", dbSavedForm.getName(),
                    "email", dbSavedForm.getEmail(),
                    "mobile", dbSavedForm.getMobile(),
                    "gender", dbSavedForm.getGender(),
                    "country", dbSavedForm.getCountry(),
                    "city", dbSavedForm.getCity(),
                    "nationality", dbSavedForm.getNationality(),
                    "message", dbSavedForm.getMessage(),
                    "referral", dbSavedForm.getReferral()
            );
            emailService.sendHtmlEmail(
                    "dineshjustin95@gmail.com",
                    "New Home Contact Form Submission",
                    "home-contact-email",
                    variables
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> variables = Map.of(
                    "name", dbSavedForm.getName()
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

        return dbSavedForm;
    }

    @PutMapping("{contactId}")
    public ContactFormHome updateContact(@RequestBody ContactFormHome theContactFormHome, @PathVariable int contactId) {
        theContactFormHome.setId(contactId);
        return thecontactFormHomeService.save(theContactFormHome);
    }

    @DeleteMapping("/{contactId}")
    public void deleteContact(@PathVariable int contactId) {
        thecontactFormHomeService.deleteById(contactId);
    }
}
