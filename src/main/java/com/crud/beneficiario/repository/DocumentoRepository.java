package com.crud.beneficiario.repository;

import com.crud.beneficiario.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository  extends JpaRepository<Documento,Long> {
}
