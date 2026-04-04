package com.gigshield.repository;

import com.gigshield.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByWorkerIdAndIsActiveTrue(Long workerId);
    List<Policy> findByIsActiveTrue();
}
