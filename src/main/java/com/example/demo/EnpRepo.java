package com.example.demo;

import com.example.demo.model.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EnpRepo extends JpaRepository<Emp,Long>, JpaSpecificationExecutor<Emp> {
}
