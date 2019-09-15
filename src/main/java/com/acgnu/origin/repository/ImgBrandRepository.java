package com.acgnu.origin.repository;

import com.acgnu.origin.entity.ImgBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgBrandRepository extends JpaRepository<ImgBrand, Long> {
}
