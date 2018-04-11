package com.example.demo.DBDAO;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Company;

public interface CompanyRepo extends CrudRepository<Company, Long> {

	boolean existsBycompanyName(String companyName);

	boolean existsBycompanyNameAndPassword(String companyName, String password);

	Company findCompanyBycompanyName(String companyName);

	Company findCompanyById(long id);

}
