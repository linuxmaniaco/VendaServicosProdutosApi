package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.dto.ProductSummaryResponseDTO;
import com.VendaServicosProdutosApi.dto.requestDTO.ProductCreateRequestDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.ProductResponseDTO;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.mapper.ProductMapper;
import com.VendaServicosProdutosApi.model.Product;
import com.VendaServicosProdutosApi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Page<ProductSummaryResponseDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toSummaryResponse);
    }

    public ProductResponseDTO productSave(ProductCreateRequestDTO request) {
        if(productRepository.existsByNameIgnoreCase(request.name()))
            throw new RecursoNaoEncontradoException("Esse produto já existe!");

        Product product = productMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);

        return  productMapper.toResponse(savedProduct);
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
