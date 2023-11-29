package com.shopme.admin.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Currency;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CurrencyRepositoryTests {
	
	@Autowired
	private CurrencyRepository repo;
	
	@Test
	public void testCreateCurrencies() {
		List<Currency> listCurrencies = Stream.of(
			new Currency("United States Dollar", "$", "USD"),
			new Currency("British Pound", "£", "GPB"),
			new Currency("Japanese Yen", "¥", "JPY"),
			new Currency("Euro", "€", "EUR"),
			new Currency("Russian Ruble", "₽", "RUB"),
			new Currency("South Korean Won", "₩", "KRW"),
			new Currency("Chinese Yuan", "¥", "CNY"),
			new Currency("Brazilian", "R$", "BRL"),
			new Currency("Australian Dollar", "$", "AUD"),
			new Currency("Canadian Dollar", "$", "CAD"),
			new Currency("Vietnamese dong", "₫", "VND"),
			new Currency("Idian Rupee", "₹", "INR")
		).collect(Collectors.toList());
		
		repo.saveAll(listCurrencies);
		
		Iterable<Currency> iterable = repo.findAll();
		
		assertThat(iterable).size().isEqualTo(12);
	}
	
	@Test
	public void testListAllOrderByNameAsc() {
		List<Currency> currencies = repo.findAllByOrderByNameAsc();
		
		currencies.forEach(System.out::println);
		
		assertThat(currencies.size()).isGreaterThan(0);
		
	}
	
}
