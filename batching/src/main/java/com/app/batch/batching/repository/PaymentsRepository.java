package com.app.batch.batching.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.batch.batching.entity.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Long>{

}
