package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.repository.AuditLogProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogProductService {

    private final AuditLogProductRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void logInsert(Object newData, Long userId) {
        saveLog( null, newData, "INSERT", userId, "Novo produto cadastrado");
    }

    public void logUpdate(Object oldData, Object newData, Long userId) {
        saveLog(oldData, newData, "UPDATE", userId, "Produto atualizado");
    }

    public void logDelete(Object oldData, Long userId) {
        saveLog(oldData, null, "DELETE", userId, "Produto deletado");
    }

    private void saveLog(Object oldData, Object newData, String action, Long userId, String reason) {
        try {
            String oldJson = oldData != null ? objectMapper.writeValueAsString(oldData) : null;
            String newJson = newData != null ? objectMapper.writeValueAsString(newData) : null;

            com.VendaServicosProdutosApi.model.AuditLogProduct log = com.VendaServicosProdutosApi.model.AuditLogProduct.builder()
                    .product("Produto")
                    .productId(extractId(newData != null ? newData : oldData))
                    .actionType(action)
                    .oldData(oldJson)
                    .newData(newJson)
                    .userId(userId)
                    .reason(reason)
                    .build();

            repository.save(log);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar log de auditoria: " + e.getMessage());
        }
    }

    // Metodo auxiliar para pegar o id do produto via reflexão
    private Long extractId(Object obj) {
        try {
            var field = obj.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return (Long) field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }
}
