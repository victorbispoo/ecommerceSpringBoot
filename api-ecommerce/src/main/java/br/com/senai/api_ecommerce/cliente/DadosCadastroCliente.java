package br.com.senai.api_ecommerce.cliente;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record DadosCadastroCliente(
        @NotBlank
        @Size(min = 3, max = 100)
        String nome,
        @Email
        @Column(unique = true)
        String email,
        @CPF
        String cpf,
        @Size(max = 20)
        String telefone
) {
}
