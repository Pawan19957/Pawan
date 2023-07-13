package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.GamingMachine;

public interface GamingMachineRepository extends JpaRepository<GamingMachine, Long> {
}
