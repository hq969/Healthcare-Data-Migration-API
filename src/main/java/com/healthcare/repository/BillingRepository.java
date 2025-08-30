package com.healthcare.repository;

import com.healthcare.domain.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Long> {
}
