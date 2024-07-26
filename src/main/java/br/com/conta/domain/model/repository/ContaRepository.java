package br.com.conta.domain.model.repository;

import br.com.conta.domain.model.Conta;

public interface ContaRepository {
	
	Conta save(Conta conta);
	
}