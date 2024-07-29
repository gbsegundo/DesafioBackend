package br.com.conta.infrastructure.persistence;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import br.com.conta.domain.model.Conta;

@Repository
public interface ContaJPARepositoryInterface extends JpaRepository<Conta, UUID>, JpaSpecificationExecutor<Conta> {
	
	@Query(value = "select c from Conta c where lower(c.descricao) like %?1% and c.dataVencimento = ?2")
	Page<Conta> findByFilters(String descricao, Date dataVencimento, Pageable pageable);
	
	@Query(value = "select c from Conta c where lower(c.descricao) like %?1%")
	Page<Conta> findByFilters(String descricao, Pageable pageable);
	
	@Query(value = "select sum(c.valor) from Conta c where c.dataPagamento between ?1 and ?2")
	double obterValorTotalPago(Date dataPagamentoInicio, Date dataPagamentoFim);

}