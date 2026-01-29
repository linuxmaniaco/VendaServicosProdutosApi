package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.Product;
import com.VendaServicosProdutosApi.repository.ProductRepository;
import jakarta.transaction.Transactional;
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
        return  productRepository.save(product);
    }

    public Product productFindById(Long idProduct) {
        return productRepository.findById(idProduct)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + idProduct));
    }

    @Transactional
    public Product productUpdate(Long id, Product product) {
        Product productFromDb = productRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

        productFromDb.setName(product.getName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setUnit_Price(product.getUnit_Price());
        productFromDb.setStock_quantity(product.getStock_quantity());
        productFromDb.setActive(product.getActive());

        return productFromDb;
//        product.setId(id);
//        return productRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto não Encontrado: " + id));
    }

    public void productDelete(Long idProduct) {
        productRepository.findById(idProduct).orElseThrow(()-> new RecursoNaoEncontradoException("Produto não encontrado: " + idProduct));
        productRepository.deleteById(idProduct);
    }
}
