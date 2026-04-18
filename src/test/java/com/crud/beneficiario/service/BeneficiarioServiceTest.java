package com.crud.beneficiario.service;

import com.crud.beneficiario.dto.BeneficiarioRequest;
import com.crud.beneficiario.dto.BeneficiarioResponse;
import com.crud.beneficiario.dto.DocumentoDto;
import com.crud.beneficiario.mapper.BeneficiarioMapper;
import com.crud.beneficiario.model.Beneficiario;
import com.crud.beneficiario.model.Documento;
import com.crud.beneficiario.repository.BeneficiarioRepositoty;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BeneficiarioServiceTest {

    @Mock
    private BeneficiarioRepositoty repository;

    @Mock
    private BeneficiarioMapper mapper;

    @Autowired
    @InjectMocks
    private BeneficiarioService service;

    private BeneficiarioRequest request;
    private Beneficiario entity;
    private BeneficiarioResponse response;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        request = new BeneficiarioRequest("Joao","123456", LocalDate.of(1990, 1, 1), List.of());
        entity = new Beneficiario();
        entity.setId(1L);
        entity.setNome("Joao");
        response = new BeneficiarioResponse(1L, "Joao", "123456",LocalDate.of(1990, 1, 1), List.of());

    }

    @Test
    void salvar_CadastroValido_RetornaResponse() {
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);


        BeneficiarioResponse result = service.salvar(request);


        assertEquals(response, result);
    }

    @Test
    void listartodos_ExistemBeneficiarios_RetornaListaResponse() {
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);


        List<BeneficiarioResponse> result = service.listartodos();


        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }


    @Test
    void listarDocumentos_BeneficiarioExiste_RetornaListaDocumentos() {
        Documento documento = new Documento(1L,"RG", "123456",new Beneficiario());
        documento.setBeneficiario(entity);
        entity.setDocumentos(List.of(documento));


        when(repository.findById(1L)).thenReturn(Optional.of(entity));


        List<DocumentoDto> result = service.listarDocumentos(1L);


        assertEquals(1, result.size());
        assertEquals("RG", result.getFirst().tipoDocumento());
    }

    @Test
    void listarDocumentos_BeneficiarioNaoExiste_LancaEntityNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> service.listarDocumentos(1L));
    }

    @Test
    void remover_BeneficiarioExiste_RemoveComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(repository).deleteById(1L);


        assertDoesNotThrow(() -> service.remover(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    void remover_BeneficiarioNaoExiste_LancaEntityNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> service.remover(1L));
    }

    @Test
    void atualizar_BeneficiarioExiste_AtualizaComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);


        BeneficiarioResponse result = service.atualizar(1L, request);


        assertEquals(response, result);
        verify(repository).save(any());
    }

    @Test
    void atualizar_BeneficiarioNaoExiste_LancaEntityNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> service.atualizar(1L, request));
    }
}