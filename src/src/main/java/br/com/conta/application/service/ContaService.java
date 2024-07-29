package br.com.conta.application.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.conta.application.input.ContaRequestDTO;
import br.com.conta.application.output.ContaReponseDTO;
import br.com.conta.application.output.RetornoReponseDTO;
import br.com.conta.domain.model.Conta;
import br.com.conta.domain.repository.ContaRepository;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
    private ModelMapper modelMapper;
	
	private DecimalFormat df = new DecimalFormat();
	

	public Object obterContaPorId(UUID id) {
		Optional<Conta> opt = contaRepository.findById(id);
		if(opt.isPresent()) {
			
		    df.applyPattern("#,##0.00");
			ContaReponseDTO  contaReponseDTO = objectMapper.convertValue(opt.get(), ContaReponseDTO.class);
			contaReponseDTO.setValor(df.format(Double.parseDouble(contaReponseDTO.getValor())));
			contaReponseDTO.setDataPagamento(contaReponseDTO.getDataPagamento().substring(8,10) + "/" + contaReponseDTO.getDataPagamento().substring(5,7) + "/" + contaReponseDTO.getDataPagamento().substring(0,4));
			contaReponseDTO.setDataVencimento(contaReponseDTO.getDataVencimento().substring(8,10) + "/" + contaReponseDTO.getDataVencimento().substring(5,7) + "/" + contaReponseDTO.getDataVencimento().substring(0,4));
			return contaReponseDTO;
		}else {
			RetornoReponseDTO dto = new RetornoReponseDTO();
			dto.setMensagem("Conta informada não existe!");
			dto.setStatus("404");
			return dto;
		} 		
	}
	
	public String obterValorTotalPago(String dataPagamentoInicio, String dataPagamentoFim) {
		
	    df.applyPattern("#,##0.00");
		double dValorTotal = 0D;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date dtPagamentoInicio = formato.parse(dataPagamentoInicio); 
			Date dtPagamentoFim = formato.parse(dataPagamentoFim); 
			Double dValor = contaRepository.obterValorTotalPago(dtPagamentoInicio, dtPagamentoFim);
			if(dValor != null) dValorTotal = dValor.doubleValue();
		} catch (Exception e) {}
		
		return df.format(dValorTotal);
	}
	
    public Map<String, Object> findAllPageConta(Pageable pageable, Date dataVencimento, String descricao) {
    	
	    df.applyPattern("#,##0.00");
    	Page<Conta> pageContas = contaRepository.findAllPageConta(pageable, dataVencimento, descricao);
    	List<ContaReponseDTO> listContas = Arrays.asList(modelMapper.map(pageContas.getContent(), ContaReponseDTO[].class));
    	
    	listContas.stream()
    		    .peek(conta -> conta.setDataPagamento(conta.getDataPagamento().substring(8,10) + "/" + conta.getDataPagamento().substring(5,7) + "/" + conta.getDataPagamento().substring(0,4)))
    		    .peek(conta -> conta.setDataVencimento(conta.getDataVencimento().substring(8,10) + "/" + conta.getDataVencimento().substring(5,7) + "/" + conta.getDataVencimento().substring(0,4)))
    		    .peek(conta -> conta.setValor(df.format(Double.parseDouble(conta.getValor()))))
         		.collect(Collectors.toList());
    	
        Map<String, Object> response = new HashMap<String, Object>();
		response.put("contas", listContas);
	    response.put("page", pageContas.getNumber()+1);
	    response.put("totalContas", pageContas.getTotalElements());
	    response.put("totalPages", pageContas.getTotalPages());
	    response.put("rowsPerPage", pageContas.getNumberOfElements());
    	
    	return response;
	}
    
    public Map<String, Object> findAllPageConta(Pageable pageable, String descricao) {
    	
	    df.applyPattern("#,##0.00");
    	Page<Conta> pageContas = contaRepository.findAllPageConta(pageable,  descricao);
    	List<ContaReponseDTO> listContas = Arrays.asList(modelMapper.map(pageContas.getContent(), ContaReponseDTO[].class));
    	
    	listContas.stream()
		    .peek(conta -> conta.setDataPagamento(conta.getDataPagamento().substring(8,10) + "/" + conta.getDataPagamento().substring(5,7) + "/" + conta.getDataPagamento().substring(0,4)))
		    .peek(conta -> conta.setDataVencimento(conta.getDataVencimento().substring(8,10) + "/" + conta.getDataVencimento().substring(5,7) + "/" + conta.getDataVencimento().substring(0,4)))
		    .peek(conta -> conta.setValor(df.format(Double.parseDouble(conta.getValor()))))
	 		.collect(Collectors.toList());

    	
        Map<String, Object> response = new HashMap<String, Object>();
		response.put("contas", listContas);
	    response.put("page", pageContas.getNumber()+1);
	    response.put("totalContas", pageContas.getTotalElements());
	    response.put("totalPages", pageContas.getTotalPages());
	    response.put("rowsPerPage", pageContas.getNumberOfElements());
    	
    	return response;
	}
	
	public ContaReponseDTO salvarConta(ContaRequestDTO contaRequestDTO) {
		
	    df.applyPattern("#,##0.00");
		Conta conta = new Conta();
 		conta.setId(UUID.randomUUID());
 		conta.setValor(retornaDouble(contaRequestDTO.getValor()));
 		conta.setDescricao(contaRequestDTO.getDescricao());
 	    conta.setSituacao(contaRequestDTO.getSituacao());
 	    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			conta.setDataPagamento(formato.parse(contaRequestDTO.getDataPagamento()));
			conta.setDataVencimento(formato.parse(contaRequestDTO.getDataVencimento()));
		} catch (ParseException e) {}
		
		contaRepository.save(conta);
		
		ContaReponseDTO contaReponseDTO = new ContaReponseDTO();
		contaReponseDTO.setDescricao(contaRequestDTO.getDescricao());
		contaReponseDTO.setSituacao(contaRequestDTO.getSituacao());
		contaReponseDTO.setId(contaRequestDTO.getId());
		contaReponseDTO.setValor(contaRequestDTO.getValor());
		contaReponseDTO.setDataPagamento(contaRequestDTO.getDataPagamento());
		contaReponseDTO.setDataVencimento(contaRequestDTO.getDataVencimento());
		contaReponseDTO.setId(conta.getId());
		
 		return contaReponseDTO;
	}
	
	public RetornoReponseDTO atualizaConta(UUID id, ContaRequestDTO contaRequestDTO) {
		RetornoReponseDTO dto = new RetornoReponseDTO();
		
	    df.applyPattern("#,##0.00");
		Optional<Conta> opt = contaRepository.findById(id);
		if(opt.isPresent()) {
			Conta conta = opt.get();
			conta.setValor(retornaDouble(contaRequestDTO.getValor()));
	 		conta.setDescricao(contaRequestDTO.getDescricao());
	 	    conta.setSituacao(contaRequestDTO.getSituacao());
	 	    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			try {
				conta.setDataPagamento(formato.parse(contaRequestDTO.getDataPagamento()));
				conta.setDataVencimento(formato.parse(contaRequestDTO.getDataVencimento()));
			} catch (ParseException e) {}
			
			contaRepository.save(conta);
			dto.setMensagem("Conta alterada com sucesso.");
			dto.setStatus("200");
		}else {
			dto.setMensagem("Conta informada não existe!");
			dto.setStatus("404");
		}
		return dto;		
	}	
	
	public RetornoReponseDTO atualizaSituacao(UUID id, String situacao) {
		RetornoReponseDTO dto = new RetornoReponseDTO();
		Optional<Conta> opt = contaRepository.findById(id);
		if(opt.isPresent()) {
			Conta conta = opt.get();
			conta.setSituacao(situacao);
			contaRepository.save(conta);
			dto.setMensagem("Situação da conta alterada com sucesso.");
			dto.setStatus("200");
		}else {
			dto.setMensagem("Conta informada não existe!");
			dto.setStatus("404");
		}
		return dto;
	}
	

	public RetornoReponseDTO importaContaCSV(final MultipartFile file) {
		RetornoReponseDTO dto = new RetornoReponseDTO();
		if(Objects.isNull(file.getContentType()) || !file.getContentType().toLowerCase().equals("text/csv") ) {
			dto.setMensagem("Arquivo inválido.");
			dto.setStatus("400");
		}else {
			try {
				
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
				String linha = null;
				 while ((linha = br.readLine()) != null) {
					String[] sDados = linha.split(";");
					Conta conta = new Conta();
			 		conta.setId(UUID.randomUUID());
			 		conta.setValor(retornaDouble(sDados[2]));
			 		conta.setDescricao(sDados[3]);
			 	    conta.setSituacao(sDados[4]);
			 	    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					conta.setDataPagamento(formato.parse(sDados[1]));
					conta.setDataVencimento(formato.parse(sDados[0]));
					contaRepository.save(conta);
				}
				br.close();  
			    dto.setMensagem("Arquivo importado com sucesso.");
			    dto.setStatus("200");
			
			}catch (Exception e) {
				dto.setMensagem(e.getMessage());
			    dto.setStatus("500");
			}
		}
		
		return dto;
	}
	
	
	public static double retornaDouble(String valor){
		boolean bNegativo = false;
		if(valor.indexOf("-") != -1){
			valor = valor.substring(2, valor.length()).trim();
			bNegativo = true;
		}
		valor = valor.replaceAll("R$", "").trim();
		String newValor = "";
	    for(int i = 0; i < valor.length(); i++){
	    	if(!valor.substring(i, i+1).equals("R") && !valor.substring(i, i+1).equals("$")){
	    	
		    	if(!valor.substring(i, i+1).equals(".")){
		    		if(valor.substring(i, i+1).equals(",")){
		    			newValor = newValor + ".";
			    	}else
			    		newValor = newValor + valor.substring(i, i+1);
		    	}
	    	}
	    	
	    }
	    valor = newValor.trim();
	    if(valor.equals("")) valor = "0";
	    double dValor = new Double(valor).doubleValue();
	    
	    if(bNegativo){
	    	dValor = 0 - dValor;
	    }
	    
	    return dValor;
		
	}

}