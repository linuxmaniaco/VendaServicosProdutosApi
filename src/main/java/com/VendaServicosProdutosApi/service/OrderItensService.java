package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.OrderItens;
import com.VendaServicosProdutosApi.repository.OrderItensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItensService {

    private final OrderItensRepository orderItensRepository;

    public List<OrderItens> findAll(){
        return orderItensRepository.findAll();
    }

    public OrderItens orderItensSave(OrderItens orderItens){
        return orderItensRepository.save(orderItens);
    }

    public OrderItens findById(Long id){
        return orderItensRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado " + id));
    }

    public OrderItens OrderItensUpdate(Long id, OrderItens orderItens){
        if(!orderItensRepository.findById(id).isPresent()){
            throw new RecursoNaoEncontradoException("Pedido não encontrado " + id);
        }
        orderItens.setId(id);
        return orderItensRepository.save(orderItens);
    }

    public void delete(Long id){
        orderItensRepository.deleteById(id);
    }
}
