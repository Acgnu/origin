package com.acgnu.origin.repository;

import com.acgnu.origin.entity.AccessUvLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessUvLogRepository extends JpaRepository<AccessUvLog, Long> {
}
