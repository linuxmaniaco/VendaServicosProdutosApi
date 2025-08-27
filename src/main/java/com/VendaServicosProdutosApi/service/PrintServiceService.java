package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.PrintService;
import com.VendaServicosProdutosApi.repository.PrintServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrintServiceService {
    private final PrintServiceRepository printServiceRepository;

    public List<PrintService> findAll() {
        return printServiceRepository.findAll();
    }

    public PrintService printServiceSave(PrintService printService) {
        return printServiceRepository.save(printService);
    }

    public PrintService findPrintServiceById(Long id) {
        return printServiceRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado: " + id));
    }

    public PrintService printServiceUpdate(Long id, PrintService printService) {
        if(!printServiceRepository.findById(id).isPresent()) {
            throw new RecursoNaoEncontradoException("Serviço não encontrado: " + id);
        }
        printService.setId(id);
        return printServiceRepository.save(printService);
    }

    public void deletePrintService(Long id) {
        printServiceRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado: " + id));
        printServiceRepository.deleteById(id);
    }

}
