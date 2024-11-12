package com.pes.pedido_ms.service;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pes.pedido_ms.domain.enums.StatusPedidoDomain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PedidoQuery {

    public Query filter(final FilterPedido filter) {
        Query query = new Query();

        if (StringUtils.hasText(filter.codPedido())) {
            query.addCriteria(Criteria.where("codPedido").is(filter.codAbrigo()));
        }
    
    
        if (StringUtils.hasText(filter.codCentroDestribuicao())) {
            query.addCriteria(Criteria.where("codCentroDestribuicao").is(filter.codCentroDestribuicao()));
        }

        if (StringUtils.hasText(filter.codAbrigo())) {
            query.addCriteria(Criteria.where("codCentroDestribuicao").is(filter.codAbrigo()));
        }
    
        if (StringUtils.hasText(filter.status())) {
            try {
                StatusPedidoDomain status = StatusPedidoDomain.valueOf(filter.status());
                query.addCriteria(Criteria.where("status").is(status));
            } catch (IllegalArgumentException e) {
                log.error("Erro ao tentar buscar status", e);
            }
        }
    
        return query;
    }
}
