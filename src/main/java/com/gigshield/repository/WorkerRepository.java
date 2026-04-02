package com.gigshield.repository;

import com.gigshield.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByAadhaarNumber(String aadhaarNumber);
    Optional<Worker> findByEmail(String email);
}