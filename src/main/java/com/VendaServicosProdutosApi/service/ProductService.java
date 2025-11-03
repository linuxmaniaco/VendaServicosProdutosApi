package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.Product;
import com.VendaServicosProdutosApi.repository.AuditLogProductRepository;
import com.VendaServicosProdutosApi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final AuditLogProductService auditLogProductService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product productSave(Product product) {
//        auditLogProductService.logInsert(product, );
        return  productRepository.save(product);
    }

    public Product productFindById(Long idProduct) {
        return productRepository.findById(idProduct)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + idProduct));
    }

    public Product productUpdate(Long id, Product product) {
        if(!productRepository.findById(id).isPresent()) {
            throw new RecursoNaoEncontradoException("Produto não encontrado: " + id);
        }
        product.setId(id);
        return  productRepository.save(product);
    }

    public void productDelete(Long idProduct) {
        productRepository.findById(idProduct).orElseThrow(()-> new RecursoNaoEncontradoException("Produto não encontrado: " + idProduct));
        productRepository.deleteById(idProduct);
    }
}
