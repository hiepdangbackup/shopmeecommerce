package com.shopme.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private TestEntityManager entityManger;
	
	@Test
	public void testCreateCustomer1() {
		Integer countryId = 4; //USA
		Country country = entityManger.find(Country.class, countryId);
		
		Customer customer = new Customer();
		customer.setCountry(country);
		customer.setFirstName("David");
		customer.setLastName("Fountaine");
		customer.setPassword("password123");
		customer.setEmail("david.s.fountainer@gmail.com");
		customer.setPhoneNumber("312-462-7518");
		customer.setAddressLine1("1927 West Drive");
		customer.setAddressLine2("1986 Notingham Forest");
		customer.setCity("Sacramento");
		customer.setState("California");
		customer.setPostalCode("95867");
		customer.setCreatedTime(new Date());
		
		Customer savedCustomer = repo.save(customer);
		
		assertThat(savedCustomer).isNotNull();
		assertThat(savedCustomer.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateCustomer2() {
		Integer countryId = 3; //India
		Country country = entityManger.find(Country.class, countryId);
		
		Customer customer = new Customer();
		customer.setCountry(country);
		customer.setFirstName("Sanya");
		customer.setLastName("Lad");
		customer.setPassword("password456");
		customer.setEmail("sanya.lad2020@gmail.com");
		customer.setPhoneNumber("02224928052");
		customer.setAddressLine1("173, A-, Shah & Nahar Indl.estate, Sunmill Road");
		customer.setAddressLine2("Dhanraj Mill Compound, Lower Parel (west)");
		customer.setCity("Mumbai");
		customer.setState("Maharashtra");
		customer.setPostalCode("400013");
		customer.setCreatedTime(new Date());
		
		Customer savedCustomer = repo.save(customer);
		
		assertThat(savedCustomer).isNotNull();
		assertThat(savedCustomer.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListCustomers() {
		Iterable<Customer> customers = repo.findAll();
		customers.forEach(System.out::println);
		
		assertThat(customers).hasSizeGreaterThan(1);
	}
	
	@Test
	public void testUpdateCustomer() {
		Integer customerId = 1;
		String lastName = "Stanfield";
		
		Customer customer = repo.findById(customerId).get();
		customer.setLastName(lastName);
		customer.setEnabled(true);
		
		Customer updateCustomer = repo.save(customer);
		
		assertThat(updateCustomer.getLastName()).isEqualTo(lastName);
		
	}
	
	@Test
	public void testGetCustomer() {
		Integer customerId = 2;
		Optional<Customer> findById = repo.findById(customerId);
	
		assertThat(findById).isPresent();
	}
	
	@Test
	public void testUpdateAuthenticationType() {
		Customer customer = repo.findById(1).get();
		
		System.out.println(customer.getAuthenticationType());
		
		assertThat(true);
	}
	
}
