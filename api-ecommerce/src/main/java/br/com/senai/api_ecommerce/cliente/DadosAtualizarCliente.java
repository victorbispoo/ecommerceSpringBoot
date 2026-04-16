package br.com.senai.api_ecommerce.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record DadosAtualizarCliente(
        Long id,

        @Size(min = 3, max = 100)
        String nome,
        @Email
        @Column(unique = true)
        String email,
        @Size(max=20)
        String telefone
) {
}
