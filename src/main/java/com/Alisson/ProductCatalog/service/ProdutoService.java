package com.Alisson.ProductCatalog.service;

import com.Alisson.ProductCatalog.dto.ProdutoDto;
import com.Alisson.ProductCatalog.model.Categoria;
import com.Alisson.ProductCatalog.model.Produto;
import com.Alisson.ProductCatalog.repository.CategoriaRepository;
import com.Alisson.ProductCatalog.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Produto criarProduto(ProdutoDto produtoDto) {

        if (produtoDto.getCategoriaId() == null) {
            throw new IllegalArgumentException("O campo categoriaId não pode ser nulo");
        }

        Categoria categoria = categoriaRepository.findById(produtoDto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Criar o Produto e associar a Categoria
        Produto produto = modelMapper.map(produtoDto, Produto.class);
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    public List<Produto> ListarProduto() {
        return produtoRepository.findAll();
    }

    public Produto atualizarProduto(Long id, ProdutoDto produtoDto) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        modelMapper.map(produtoDto, produtoExistente);

        return produtoRepository.save(produtoExistente);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
}
