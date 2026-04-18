package com.crud.beneficiario.dto;

import java.time.LocalDate;
import java.util.List;

public record BeneficiarioRequest(String nome, String telefone, LocalDate dataNascimento, List<DocumentoDto> documentos) {
}
