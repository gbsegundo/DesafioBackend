package br.com.conta.domain.repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.conta.domain.model.Conta;

public interface ContaRepository {
	
	Conta save(Conta conta);
	Optional<Conta> findById(UUID id);
	Page<Conta> findAllPageConta(Pageable pageable, Date dataVencimento, String descricao);
	Page<Conta> findAllPageConta(Pageable pageable,  String descricao);
	Double obterValorTotalPago(Date dataPagamentoInicio, Date dataPagamentoFim);
}