package com.shopme.shopping.cart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Product;
import com.shopme.shoppingcart.CartItemRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartItemRepositoryTests {
	
	@Autowired
	private CartItemRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testSaveItems() {
		Integer customerId = 1;
		Integer productId = 3;
		
		Customer customer = entityManager.find(Customer.class, customerId);
		Product product = entityManager.find(Product.class, productId);
		
		CartItem newItem = new CartItem();
		newItem.setCustomer(customer);
		newItem.setProduct(product);
		newItem.setQuantity(1);
		
		CartItem savedItem = repo.save(newItem);
		assertThat(savedItem.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testSave2Items() {
		Integer customerId = 6;
		Integer productId = 10;
		
		Customer customer = entityManager.find(Customer.class, customerId);
		Product product = entityManager.find(Product.class, productId);
		
		CartItem item1 = new CartItem();
		item1.setCustomer(customer);
		item1.setProduct(product);
		item1.setQuantity(2);
		
		CartItem item2 = new CartItem();
		item2.setCustomer(new Customer(customerId));
		item2.setProduct(new Product(4));
		item2.setQuantity(3);
		
		List<CartItem> item = new ArrayList<>();
		item.add(item1);
		item.add(item2);
		
		Iterable<CartItem> iterable = repo.saveAll(item);
		
		assertThat(iterable).size().isGreaterThan(0);
		
	}
	
	@Test
	public void testFindByCustomer() {
		Integer customerId = 6;
		List<CartItem> listItems = repo.findByCustomer(new Customer(customerId));
		assertThat(listItems.size()).isEqualTo(2);
	}
	
	@Test
	public void testFindByCustomerAndProduct() {
		Integer customerId = 1;
		Integer productId = 3;
		
		CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
		assertThat(item).isNotNull();
		System.out.println(item);
	}
	
	@Test
	public void testUpdateQuantity() {
		Integer customerId = 1;
		Integer productId = 3;
		Integer quantity = 4;
		
		repo.updateQuantity(quantity, customerId, productId);
	}
	
	@Test
	public void testDeleteByCustomerAndProduct() {
		Integer customerId = 1;
		Integer productId = 3;
		
		repo.deleteByCustomerAndProduct(customerId, productId);
	
		CartItem item = repo.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
		
		assertThat(item).isNull();
	}
	
}
