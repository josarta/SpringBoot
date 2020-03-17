package com.vass.dbs_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vass.dbs_store.model.*;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}