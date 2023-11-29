package com.shopme.admin.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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
import com.shopme.common.entity.Setting;
import com.shopme.common.entity.SettingCategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {

	@Autowired
	SettingRepository repo;
	
	@Test
	public void testCreateGeneralSettings() {
//		Setting siteName = new Setting("SITE_NAME", "Shopme", SettingCategory.GENERAL);
//		Setting savedSetting = repo.save(siteName);
//		
//		assertThat(savedSetting).isNotNull();
		
		Setting siteLogo = new Setting("SITE_LOGO", "Shopme.png", SettingCategory.GENERAL);
		Setting copyRight = new Setting("COPYRIGHT", "Copyright (C) 2021 Shopme Ltd.", SettingCategory.GENERAL);
		
		List<Setting> settings = new ArrayList<>();
		settings.add(siteLogo);
		settings.add(copyRight);
		
		repo.saveAll(settings);
		
		Iterable<Setting> iterable = repo.findAll();
		assertThat(iterable).size().isGreaterThan(0);
		
	}
	
	
	@Test
	public void testCreateCurrencySettings() {
		
		Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.GENERAL);
		Setting symbol = new Setting("CURRENCY_SYMBOL", "$", SettingCategory.GENERAL);
		Setting symbolPosition = new Setting("CURRENCY_SYMBOL_POSITION", "before", SettingCategory.GENERAL);
		Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.GENERAL);
		Setting decimalDigits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.GENERAL);
		Setting thousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.GENERAL);

		 List<Setting> settings = Stream.of(currencyId,symbol, symbolPosition, decimalPointType, decimalDigits, thousandsPointType )
			      .collect(Collectors.toList());
		
		repo.saveAll(settings);
		
	}
	
	@Test
	public void testListSettingsByCategory() {
		List<Setting> settings = repo.findByCategory(SettingCategory.GENERAL);
		
		settings.forEach(System.out::println);
	}
	
	
}























