package com.VendaServicosProdutosApi.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.VendaServicosProdutosApi.model.Product;
import com.VendaServicosProdutosApi.VendaServicosProdutosApi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {

        return productRepository.findAll();
    }
}
