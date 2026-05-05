package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.cliente.*;
import br.com.senai.api_ecommerce.exceptions.ErroResponse;
import br.com.senai.api_ecommerce.produto.DadosDetalhamentoProduto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("clientes")
@Tag(name="Clientes",description="Gerenciamento dos clientes no ecommerce")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastra um cliente no sistema")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoCliente.class))
                    }),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content),
    })
    public void cadastrarCliente(@RequestBody @Valid DadosCadastroCliente dados){
        repository.save(new Cliente(dados));
    }

    @GetMapping
    @Operation(summary = "Lista todos os clientes cadastrados")
    public Page<DadosListagemCliente> listarClientes(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCliente::new);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualiza os dados de um cliente pelo ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoProduto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado", content = @Content),

    })
    public void atualizarCliente(@RequestBody @Valid DadosAtualizarCliente dados){
        var cliente = repository.getReferenceById(dados.id());
        cliente.atualizarCliente(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Exclui um cliente pelo ID")
    public void deletarCliente(@PathVariable Long id){
        var cliente = repository.getReferenceById(id);
        cliente.excluirCliente();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista todos os dados de um cliente pelo ID")
    public DadosDetalhamentoCliente detalharCliente(@PathVariable Long id){
        Cliente cliente = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não existe"
                ));
        return new DadosDetalhamentoCliente(cliente);
    }
}
