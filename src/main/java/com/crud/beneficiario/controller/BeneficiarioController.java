package com.crud.beneficiario.controller;

import com.crud.beneficiario.dto.BeneficiarioRequest;
import com.crud.beneficiario.dto.BeneficiarioResponse;
import com.crud.beneficiario.dto.DocumentoDto;
import com.crud.beneficiario.service.BeneficiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/beneficiarios")
public class BeneficiarioController {

    @Autowired
    BeneficiarioService service;

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novos Beneficiarios e seus documentos")
    @ApiResponse(responseCode = "201", description = "Beneficiario criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<BeneficiarioResponse> criar (@RequestBody @Valid BeneficiarioRequest request){
        log.info("Solicitação para criação de beneficiario recebida");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @GetMapping
    @Operation(description = "Endpoint responsável por listar todos os Beneficiarios e seus respectivos documentos")
    @ApiResponse (responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<List<BeneficiarioResponse>> listarTodos(){
        log.info(" Requisição de Listagem de todos os beneficiarios recebida");
        return ResponseEntity.ok().body(service.listartodos());
    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint responsável por listar documentos associado a um beneficiario identificado por id")
    @ApiResponse (responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "404", description = "não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<List<DocumentoDto>> listarDocumentos(@PathVariable Long id){
        log.info("solicitação de Listagem de documentos por id recebida;");
        return ResponseEntity.ok().body(service.listarDocumentos(id));
    }

    @PutMapping("/{id}")
    @Operation(description = "Endpoint responsável por atualizar dados do Beneficiario")
    @ApiResponse(responseCode = "200", description = "beneficiario atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "beneficiario não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<BeneficiarioResponse> atualizar (@PathVariable Long id, @RequestBody @Valid BeneficiarioRequest request){
        log.info("Solicitação de Atualização de dados beneficiario recebida");
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint responsável por remover beneficiario")
    @ApiResponse(responseCode = "204", description = "beneficiario removido com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "beneficiario não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Void> remover (@PathVariable Long id){
        log.info("Solicitação de remoção de beneficiario recebida");
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}

