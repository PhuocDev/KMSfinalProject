package com.kms.seft203.contact;

import com.kms.seft203.Validation.Validation;
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

    @GetMapping({  ""})
    public List<Contact> getAll() {
        //return new ArrayList<>(DATA.values());
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") String id) {
        //return DATA.get(id);
        if (contactService.contactRepository.existsById(id)) {
            return new ResponseEntity<>(contactService.getContactId(id), HttpStatus.FOUND);
        } else return new ResponseEntity<>("Not found contact id: " + id, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addNewContact(@RequestBody @Valid SaveContactRequest request) {
        //DATA.put(request.getId(), request);
        //Tất cả các constrains của input sẽ được đặt bên trong validateContract
        if (Validation.validateContact(request)) {
            Contact newContact = new Contact(request);
            contactService.saveContact(newContact);
            return new ResponseEntity<Contact>(newContact, HttpStatus.OK);
        } else return new ResponseEntity<>("Request not valid", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable String id, @RequestBody SaveContactRequest request) {
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

//    @GetMapping("/delete/all")
//    public ResponseEntity<?> deleteAll() {
//        contactService.deleteAll();
//        APImessages imessages = new APImessages("Deleted all the database");
//        return new ResponseEntity<APImessages>(imessages, HttpStatus.OK);
//    }
//    @GetMapping("/createDB")
//    public List<Contact> createDB() {
//        for (int i = 1 ; i < 5 ; i++) {
//            Contact contact = new Contact("Bui","Phuoc", "BE", "Depart" + i*564, "project " + i*123, "avatar " + i*92, 1000+ i);
//            contactService.saveContact(contact);
//        }
//        return contactService.getAllContacts();
//    }
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
