package com.nz.eazyschool.service;

import com.nz.eazyschool.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Slf4j
@Service
//@RequestScope
//@SessionScope
@ApplicationScope
public class ContactService {
    //private static Logger log = LoggerFactory.getLogger(ContactService.class);
    private int counter= 0;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public ContactService(){
        System.out.println("Contact Service Bean Initialized");
    }
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = true;
        //Todo
        log.info(contact.toString());
        return isSaved;
    }
}
