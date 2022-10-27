package com.kms.seft203.contact.service;

import com.kms.seft203.contact.entity.Contact;
import com.kms.seft203.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public void saveContact(Contact contact) {
        contactRepository.save(contact);
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
        System.out.println("Contact deleted!");
    }
    public Contact updateContact(String id, Contact contactNew) {
        Contact contactChange = contactRepository.findById(id).get();
        contactChange.clone(contactNew);
        contactRepository.update(contactChange.getFirstName(), contactChange.getLastName(),
                contactChange.getTitle(), contactChange.getDepartment(), contactChange.getProject(),
                contactChange.getAvatar(),
                contactChange.getEmployeeId(), id );
        return contactChange;
    }

    public void deleteAll() {
        contactRepository.deleteAll();
    }

    public Contact getContactId(String id) {
        return contactRepository.findById(id).get();
    }

    public void deleteContactById(String id) {
        contactRepository.deleteById(id);
    }


    public Integer findNumberOfTitle(String title) {
        return contactRepository.findNumberOfTitle(title);
    }
}
