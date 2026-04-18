package com.crud.beneficiario.service;

import com.crud.beneficiario.dto.BeneficiarioRequest;
import com.crud.beneficiario.dto.BeneficiarioResponse;
import com.crud.beneficiario.dto.DocumentoDto;
import com.crud.beneficiario.mapper.BeneficiarioMapper;
import com.crud.beneficiario.model.Beneficiario;
import com.crud.beneficiario.model.Documento;
import com.crud.beneficiario.repository.BeneficiarioRepositoty;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
public class BeneficiarioService {

    @Autowired
    BeneficiarioRepositoty repositoty;

    @Autowired
    BeneficiarioMapper mapper;

    public BeneficiarioResponse salvar(BeneficiarioRequest request){
        log.info("beneficiario Salvo");
        Beneficiario b = mapper.toEntity(request);
        return mapper.toResponse(repositoty.save(b));
    }

    public List<BeneficiarioResponse> listartodos(){
        log.info("Processando listagem todos documentos");
        return repositoty.findAll().stream().map(mapper::toResponse).toList();
    }


    public List<DocumentoDto> listarDocumentos (Long id){
        log.info("Processando listagem documento");
        Beneficiario b = repositoty.findById(id).orElseThrow(EntityNotFoundException::new);
        return b.getDocumentos().stream().map(d -> new DocumentoDto(d.getTipoDocumento(),d.getDescricao())).toList();
    }

    public void remover(Long id){
        log.info("Beneficiario removido");
        repositoty.deleteById(repositoty.findById(id).orElseThrow(EntityNotFoundException::new).getId());
    }

    public BeneficiarioResponse atualizar(Long id, BeneficiarioRequest request){
        Beneficiario b = repositoty.findById(id).orElseThrow(EntityNotFoundException::new);
        b.setNome(request.nome());
        b.setDataNascimento(request.dataNascimento());
        b.getDocumentos().clear();
        for (DocumentoDto doc: request.documentos()){
            Documento d = new Documento();
            d.setTipoDocumento(doc.tipoDocumento());
            d.setDescricao(doc.descricao());
            d.setBeneficiario(b);
            b.getDocumentos().add(d);
        }
        log.info("Beneficiario Atualizado");
        return mapper.toResponse(repositoty.save(b));
    }
}