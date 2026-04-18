package com.crud.beneficiario.controller;

import com.crud.beneficiario.dto.BeneficiarioRequest;
import com.crud.beneficiario.dto.BeneficiarioResponse;
import com.crud.beneficiario.dto.DocumentoDto;
import com.crud.beneficiario.service.BeneficiarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BeneficiarioControllerTest {

    @Mock
    private BeneficiarioService service;


    @InjectMocks
    private BeneficiarioController beneficiarioController;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    MockMvc mockMvc;

    private BeneficiarioRequest request;
    private BeneficiarioResponse response;


        @BeforeEach
        void setup() {
            mockMvc = MockMvcBuilders.standaloneSetup(beneficiarioController).build();
           // request = new BeneficiarioRequest("João", "123456", LocalDate.of(1990, 1, 1), List.of(
            //        new DocumentoDto("CPF", "123.456.789-00")));

            response = new BeneficiarioResponse(1L, "João", "123456", LocalDate.of(1990, 1, 1), List.of(
                    new DocumentoDto("CPF", "123.456.789-00")));
        }

    @Test
    void criar_cadastroValido_statusCreated() throws Exception {
        String json = """
                {
                   "nome": "string",
                   "telefone": "string",
                   "dataNascimento": "1800-01-29",
                   "documentos": [
                     {
                        "tipoDocumento": "string",
                       "descricao": "string"
                    }
                  ]
                }
                """;


        when(service.salvar(any())).thenReturn(response);


        mockMvc.perform(post("/beneficiarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void criar_cadastroInvalido_statusBadRequest() throws Exception {
        String jsonInvalid = """
                {
                   "nome": "string",
                   "telefone": "string",
                   "dataNascimento": ,
                   "documentos": [
                     {
                        "tipoDocumento": "string",
                       "descricao": "string"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/beneficiarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalid))
                .andExpect(status().isBadRequest());
    }


    @Test
    void listarTodos_existemBeneficiarios_statusOk() throws Exception {
        when(service.listartodos()).thenReturn(List.of(response));

        mockMvc.perform(get("/beneficiarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }

    @Test
    void listarDocumentos_idValido_statusOk() throws Exception {
        List<DocumentoDto> documentos = List.of(new DocumentoDto("RG", "123456"));
        when(service.listarDocumentos(1L)).thenReturn(documentos);

        mockMvc.perform(get("/beneficiarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoDocumento").value("RG"));
    }

    @Test
    void listarDocumentos_idInexistente_statusNotFound() throws Exception {
        when(service.listarDocumentos(99L)).thenThrow(new EntityNotFoundException());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/beneficiarios/99"))
                    .andExpect(status().isNotFound());

        });

    }



    @Test
    void atualizar_idExistente_statusOk() throws Exception {

        String json = """
                {
                  "id": 1,
                   "nome": "string",
                   "telefone": "string",
                   "dataNascimento": "1800-01-29",
                   "documentos": [
                     {
                        "tipoDocumento": "string",
                       "descricao": "string"
                    }
                  ]
                }
                """;
        when(service.atualizar(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/beneficiarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void atualizar_idInexistente_statusNotFound() throws Exception {

        String json = """
                {
                  "id": 99,
                   "nome": "string",
                   "telefone": "string",
                   "dataNascimento": "1800-01-29",
                   "documentos": [
                     {
                        "tipoDocumento": "string",
                       "descricao": "string"
                    }
                  ]
                }
                """;
        when(service.atualizar(eq(99L), any())).thenThrow(new EntityNotFoundException());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(put("/beneficiarios/99")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isNotFound());

        });
    }


    @Test
    void remover_idExistente_statusNoContent() throws Exception {
        mockMvc.perform(delete("/beneficiarios/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).remover(1L);
    }

    @Test
    void remover_idInexistente_statusNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).remover(99L);

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/beneficiarios/99"))
                    .andExpect(status().isNotFound());

        });

    }





}

