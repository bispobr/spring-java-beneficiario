package com.crud.beneficiario.repository;


import com.crud.beneficiario.model.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiarioRepositoty  extends JpaRepository<Beneficiario,Long> {
}