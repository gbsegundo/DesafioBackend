package br.com.conta.infrastructure.persistence;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.conta.domain.model.Conta;
import br.com.conta.domain.repository.ContaRepository;

@Service
public class ContaJPARepository implements ContaRepository {
	
    @Autowired
    ContaJPARepositoryInterface contaJPARepositoryInterface;

 
    @Override
    public Conta save(Conta conta) {
        return contaJPARepositoryInterface.save(conta);
    }
    
    @Override
    public Optional<Conta> findById(UUID id) {
        return contaJPARepositoryInterface.findById(id);
    }
    
    @Override
    public Page<Conta> findAllPageConta(Pageable pageable, Date dataVencimento, String descricao) {
        return contaJPARepositoryInterface.findByFilters(descricao, dataVencimento,  pageable);
    }

    @Override
    public Page<Conta> findAllPageConta(Pageable pageable,  String descricao) {
        return contaJPARepositoryInterface.findByFilters(descricao,  pageable);
    }

	@Override
	public Double obterValorTotalPago(Date dataPagamentoInicio, Date dataPagamentoFim) {
		return contaJPARepositoryInterface.obterValorTotalPago(dataPagamentoInicio,  dataPagamentoFim);
	}

 
}