package br.com.senai.api_ecommerce.cliente;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "clientes")
@Entity(name = "Cliente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private boolean ativo;

    public Cliente(DadosCadastroCliente dados){
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.ativo = true;
    }

    public void atualizarCliente(DadosAtualizarCliente dados) {
        if(dados.nome() != null && !dados.nome().isBlank())
            this.nome = dados.nome();
        if(dados.email() != null && !dados.email().isBlank())
            this.email = dados.email();
        if(dados.telefone() != null)
            this.telefone = dados.telefone();
    }

    public void excluirCliente(){
        this.ativo = false;
    }
}
