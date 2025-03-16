package com.Alisson.ProductCatalog.controller;

import com.Alisson.ProductCatalog.dto.ProdutoDto;
import com.Alisson.ProductCatalog.model.Categoria;
import com.Alisson.ProductCatalog.model.Produto;
import com.Alisson.ProductCatalog.repository.CategoriaRepository;
import com.Alisson.ProductCatalog.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CategoriaRepository categoriaRepository;

    Categoria novaCategoria = new Categoria();


    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        // Verificar se a categoria do produto já existe no banco
        if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        } else if (produto.getCategoria() != null) {
            // Caso a categoria não tenha sido passada com id, criamos uma nova
            Categoria novaCategoria = new Categoria();
            novaCategoria.setNome(produto.getCategoria().getNome());  // Assume que o nome da categoria foi enviado
            categoriaRepository.save(novaCategoria);  // Salva a nova categoria
            produto.setCategoria(novaCategoria);  // Atribui a nova categoria ao produto
        }

        return produtoService.salvar(produto);  // Salva o produto com a categoria associada
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtosDto = produtoService.ListarProduto();
        return new ResponseEntity<>(produtosDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDto> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDto produtoDto) {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produtoDto);
        ProdutoDto produtoDtoAtualizado = modelMapper.map(produtoAtualizado, ProdutoDto.class);
        return new ResponseEntity<>(produtoDtoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
