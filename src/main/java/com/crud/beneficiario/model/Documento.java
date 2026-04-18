package com.crud.beneficiario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Documento extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tipoDocumento;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "beneficiario_id")
    private Beneficiario beneficiario;
}