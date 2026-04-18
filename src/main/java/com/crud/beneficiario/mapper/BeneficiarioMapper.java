package com.crud.beneficiario.mapper;

import com.crud.beneficiario.dto.BeneficiarioRequest;
import com.crud.beneficiario.dto.BeneficiarioResponse;
import com.crud.beneficiario.dto.DocumentoDto;
import com.crud.beneficiario.model.Beneficiario;
import com.crud.beneficiario.model.Documento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BeneficiarioMapper {
    public Beneficiario toEntity(BeneficiarioRequest req){
        Beneficiario b =  new Beneficiario();
        b.setNome(req.nome());
        b.setTelefone(req.telefone());
        b.setDataNascimento(req.dataNascimento());
        List<Documento> documentos = req.documentos().stream().map(d->{
            Documento doc =  new Documento();
            doc.setTipoDocumento(d.tipoDocumento());
            doc.setDescricao(d.descricao());
            doc.setBeneficiario(b);
            return doc;
        }).toList();
        b.setDocumentos(documentos);
        return b;
    }

    public BeneficiarioResponse toResponse(Beneficiario b) {
        List<DocumentoDto> docs = b.getDocumentos()
                .stream()
                .map(d -> new DocumentoDto(d.getTipoDocumento(), d.getDescricao()))
                .collect(Collectors.toList());

        return new BeneficiarioResponse(
                b.getId(),
                b.getNome(),
                b.getTelefone(),
                b.getDataNascimento(),
                docs
        );
    }
}
