package com.example.demo.repository;

import com.example.demo.domain.UserShipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<UserShipping,Long> {
}
