package com.shopme.admin.setting.country;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.Country;

@RestController
public class CountryRestController {

	@Autowired
	private CountryRepository repo;
	
	@GetMapping("/countries/list")
	public List<CountryDTO> listAll() {
		List<Country> countries = repo.findAllByOrderByNameAsc();
		List<CountryDTO> listCountriesDTO = new ArrayList<>();
		for(int i = 0; i < countries.size(); i++) {
			Country country = countries.get(i);
			CountryDTO countryDTO = new CountryDTO();
			countryDTO.setId(country.getId());
			countryDTO.setName(country.getName());
			countryDTO.setCode(country.getCode());
			listCountriesDTO.add(countryDTO);
		}
		
		return listCountriesDTO;
	}
	
	@PostMapping("/countries/save")
	public String save(@RequestBody Country country) {
		Country savedCountry = repo.save(country);
		return String.valueOf(savedCountry.getId());
	}
	
	@GetMapping("/countries/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
}
