package com.kms.seft203.contact;

import com.kms.seft203.contact.Contact;
import com.kms.seft203.contact.ContactRepository;
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
        contactChange.setAvatar(contactNew.getAvatar());
        contactChange.setDepartment(contactChange.getDepartment());
        contactChange.setEmployeeId(contactChange.getEmployeeId());
        contactChange.setFirstName(contactChange.getFirstName());
        contactChange.setLastName(contactNew.getLastName());
        contactChange.setProject(contactNew.getProject());
        contactChange.setTitle(contactNew.getTitle());

        contactRepository.saveAndFlush(contactChange);
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
