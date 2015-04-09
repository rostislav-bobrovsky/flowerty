/**
 * @author Eugene Putsykovich(slesh) Apr 5, 2015
 *
 */
package by.itechart.flowerty.web.controller;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import by.itechart.flowerty.model.Address;
import by.itechart.flowerty.model.Contact;
import by.itechart.flowerty.model.Phone;
import by.itechart.flowerty.model.Phone.PHONE_TYPE;
import by.itechart.flowerty.web.exception.NotFoundException;
import by.itechart.flowerty.web.service.ContactService;

/**
 * @author Eugene Putsykovich(slesh) Apr 5, 2015
 *
 */
@Controller
public class ContactController {
    private Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
    
    @Autowired
    private ContactService contactService;
    
    @ResponseBody
    @RequestMapping(value = "contact/list/{page}")
    public Page<Contact> getPage(@PathVariable("page") Integer page) {
	LOGGER.info("get contact page with number {}", page);
	
	page = (page == null || page < 1) ? 0 : --page;
	
	return contactService.getPage(page, 10);
    }
    
    @ResponseBody
    @RequestMapping(value = "contact/details/{id}")
    public Contact getById(@PathVariable("id") Long id) throws NotFoundException {
	LOGGER.info("get details about contact with id: {}", id);
	
	if (id == null || id < 0) {
	    throw new NotFoundException("contact id cannot be negative or null");
	}

	final Phone phone = new Phone();
	phone.setId(1L);
	phone.setCountry("+375");
	phone.setOperator("029");
	phone.setNumber("8272039");
	phone.setType(PHONE_TYPE.CELL);
	phone.setComment("nice number");
	
	final Phone phone1 = new Phone();
	phone1.setId(2L);
	phone1.setCountry("+375");
	phone1.setOperator("029");
	phone1.setNumber("8272037");
	phone1.setType(PHONE_TYPE.HOME);
	phone1.setComment("cool number");

	final Phone phone2 = new Phone();
	phone2.setId(3L);
	phone2.setCountry("+375");
	phone2.setOperator("029");
	phone2.setNumber("1234567");
	phone2.setType(PHONE_TYPE.HOME);
	phone2.setComment("cool number 123");
	
	Contact contact = contactService.findOne(id);
	// if uncomment, then get exception: java.lang.IllegalStateException: Cannot forward after response has been committed
//	phone.setContact(contact);
//	phone1.setContact(contact);
//	phone2.setContact(contact);
	
	contact.setPhones(new HashSet<Phone>(){{
	    add(phone);
	    add(phone1);
	    add(phone2);
	}});
	
	return contact;
    }
    
    @RequestMapping(value = "contact/remove", method = RequestMethod.POST)
    public void remove(@RequestBody List<Contact> contacts){
	LOGGER.info("remove contacts. obtained {} contacts, wicht not remove", contacts.size());
	
	for (Contact contact : contacts) {
	    contactService.delete(contact.getId());
	}
    }

    @ResponseBody
    @RequestMapping(value = "contact/save", method = RequestMethod.POST)
    public Contact save(@RequestBody Contact contact) {
	LOGGER.info("save contact: {} {} {}", contact.getName(), contact.getSurname(), contact.getFathername());
	Address a = contact.getAddress();
	LOGGER.info("address details: id:{}, town:{}, street:{}, house:{}, flat:{}, country:{}", 
		    		a.getId(), a.getTown(), a.getStreet(), a.getHouse(), a.getFlat(), a.getCountry());
	
	contact.setPhones(null);
	contactService.save(contact);
	
	return contact;
    }
}
