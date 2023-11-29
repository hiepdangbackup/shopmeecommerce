package com.shopme.admin.product;

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
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateProduct() {
		Brand brand = entityManager.find(Brand.class, 1);
		Category category = entityManager.find(Category.class, 15);

		Product product = new Product();
//		product.setName("Dell Inspiron 3000");
//		product.setAlias("dell inspiron 3000");
//		product.setShortDescription("A good smartphone from Dell Inspiron 3000");
//		product.setFullDescription("Full description for Dell Inspiron 3000");
		product.setName("Samsung Galaxy A31");
		product.setAlias("samsung_galaxy_a31");
		product.setShortDescription("A good smartphone from samsung");
		product.setFullDescription("This is a very good smartphone full description");

		product.setBrand(brand);
		product.setCategory(category);

		product.setPrice(456);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());

		Product savedProduct = repo.save(product);

		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);

	}

	@Test
	public void testListAllProduct() {
		Iterable<Product> iterableProducts = repo.findAll();
		iterableProducts.forEach(System.out::println);
	}

	public void testGetProduct() {
		Integer id = 2;
		Product product = repo.findById(id).get();

		System.out.println(product);
		assertThat(product).isNotNull();
	}

	@Test
	public void testUpdateProduct() {
		Integer id = 1;
		Product product = repo.findById(id).get();
		product.setPrice(499);

		repo.save(product);

		Product updatedProduct = entityManager.find(Product.class, id);

		assertThat(updatedProduct.getPrice()).isEqualTo(499);

	}

	@Test
	public void testDeleteProduct() {
		Integer id = 3;
		repo.deleteById(id);

		Optional<Product> result = repo.findById(id);

		assertThat(!result.isPresent());

	}

	@Test
	public void testSaveProductWithImages() {
		Integer productId = 1;
		Product product = repo.findById(productId).get();

		product.setMainImage("main_image.jpg");
		product.addExtraImage("extra_image_1.png");
		product.addExtraImage("extra_image_2.png");
		product.addExtraImage("extra_image_3.png");

		repo.save(product);
	}

	@Test
	public void testSaveProductWithDetails() {
		Integer productId = 3;
		Product product = repo.findById(productId).get();

		product.addDetail("Device Memory", "128 GB");
		product.addDetail("CPU Model", "MediaTek");
		product.addDetail("OK", "Android 10");

		Product savedProduct = repo.save(product);
		assertThat(savedProduct.getDetails()).isNotEmpty();

	}

}
