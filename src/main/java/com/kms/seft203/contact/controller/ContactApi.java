package com.kms.seft203.contact.controller;

import com.kms.seft203.contact.entity.Contact;
import com.kms.seft203.contact.dto.SaveContactRequest;
import com.kms.seft203.contact.service.ContactService;
import com.kms.seft203.exception.APImessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
public class ContactApi {
    private static final Map<String, Contact> DATA = new HashMap<>();
    @Autowired
    ContactService contactService;

    @GetMapping({"/all", "/"})
    public List<Contact> getAll() {
        //return new ArrayList<>(DATA.values());
        return contactService.getAllContacts();
    }

    @GetMapping("/delete/all")
    public ResponseEntity<?> deleteAll() {
        contactService.deleteAll();
        APImessages imessages = new APImessages("Deleted all the database");
        return new ResponseEntity<APImessages>(imessages, HttpStatus.OK);
    }
    @GetMapping("/createDB")
    public List<Contact> createDB() {
        for (int i = 1 ; i < 5 ; i++) {
            Contact contact = new Contact("Bui","Phuoc", "BE", "Depart" + i*564, "project " + i*123, "avatar " + i*92, 1000+ i);
            contactService.saveContact(contact);
        }
        return contactService.getAllContacts();
    }
    @GetMapping("/{id}")
    public Contact getOne(@PathVariable String id) {
        //return DATA.get(id);
        return contactService.getContactId(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody @Valid SaveContactRequest request) {
        //DATA.put(request.getId(), request);
        //Tất cả các constrains của input sẽ được đặt bên trong validateContract
        if (Validation.validateContact(request)) {
            Contact newContact = new Contact(request);
            contactService.saveContact(newContact);
            return new ResponseEntity<Contact>(newContact, HttpStatus.OK);
        } else return new ResponseEntity<>("Request not valid", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody SaveContactRequest request) {
        if (contactService.getContactId(id) == null) {
            APImessages apImessages = new APImessages("Not found contact id: " + id);
            return new ResponseEntity<APImessages>(apImessages, HttpStatus.NOT_FOUND);
        }
        if (Validation.validateContact(request)) {
            Contact contact = new Contact(request);
            contactService.updateContact(id, request);
            return new ResponseEntity<Contact>(contactService.getContactId(id), HttpStatus.OK);
        } else return new ResponseEntity<>("Request not valid", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {

        if (contactService.getContactId(id) == null) {
            APImessages error = new APImessages("Not found contact id: "+id);
            return new ResponseEntity<APImessages>(error, HttpStatus.NOT_FOUND);
        } else {
            contactService.deleteContactById(id);
            APImessages message = new APImessages("Deleted contact: "+ id);
            return new ResponseEntity<APImessages>(message, HttpStatus.OK);
        }
    }
    /*
    {
    "firstName":"Nguyen",
    "lastName":"phuoc",
    "title":"title new",
    "department":"department new",
    "project": "project new",
    "avatar":"avatar 32422342",
    "employeeId": "124353"
    }
     */
}
