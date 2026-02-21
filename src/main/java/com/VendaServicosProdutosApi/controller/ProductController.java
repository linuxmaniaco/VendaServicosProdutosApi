package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.dto.ProductSummaryResponseDTO;
import com.VendaServicosProdutosApi.dto.requestDTO.ProductCreateRequestDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.ProductResponseDTO;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.Product;
import com.VendaServicosProdutosApi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductSummaryResponseDTO>> findAll(@PageableDefault(size = 10)Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.productSave(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        try {
            Product product = productService.productFindById(id);
            return ResponseEntity.ok(product);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product productUpdated = productService.productUpdate(id, product);
            return ResponseEntity.ok(productUpdated);
        } catch (RecursoNaoEncontradoException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        try {
            productService.productDelete(id);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
