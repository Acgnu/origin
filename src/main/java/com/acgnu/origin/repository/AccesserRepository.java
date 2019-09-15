package com.acgnu.origin.repository;

import com.acgnu.origin.entity.Accesser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesserRepository extends JpaRepository<Accesser, Integer> {
    Accesser getByUname(String uname);
}
