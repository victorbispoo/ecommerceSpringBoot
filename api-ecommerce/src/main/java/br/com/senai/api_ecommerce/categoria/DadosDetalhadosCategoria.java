package br.com.senai.api_ecommerce.categoria;

public record DadosDetalhadosCategoria(Long id,
                                       String nome,
                                       String descricao) {
    public DadosDetalhadosCategoria(Categoria categoria) {
        this(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }

}
