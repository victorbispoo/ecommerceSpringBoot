package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.cliente.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public void cadastrarCliente(@RequestBody @Valid DadosCadastroCliente dadosCliente) {
        clienteRepository.save(new Cliente(dadosCliente));
    }

    @GetMapping
    public Page<DadosListagemCliente> listarClientes(@PageableDefault(size=5,sort = {"id"}) Pageable paginacao) {
        return clienteRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCliente::new);
    }
    @PutMapping("/{id}")
    @Transactional
    public void atualizarCliente(@RequestBody @Valid DadosAtualizarCliente dadosCliente) {
        var cliente = clienteRepository.getReferenceById(dadosCliente.id());
        cliente.atualizarCliente(dadosCliente);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void deletarCliente(@PathVariable Long id) {
        var cliente = clienteRepository.getReferenceById(id);
        cliente.excluirCliente();
    }
    @GetMapping("/{id}")
    public DadosDetalhamentoCliente detalharCliente(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não existe"
                ));
        return new DadosDetalhamentoCliente(cliente);
    }
}
