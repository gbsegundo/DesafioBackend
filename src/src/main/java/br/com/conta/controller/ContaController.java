package br.com.conta.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.conta.application.input.ContaRequestDTO;
import br.com.conta.application.output.ContaReponseDTO;
import br.com.conta.application.output.RetornoReponseDTO;
import br.com.conta.application.service.ContaService;
import br.com.conta.application.swagger.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Endpoint Conta")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/conta")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	

	@ApiOperation(method = "GET", description = "Listagem de contas", summary = "Paginação das contas")
	@GetMapping(value = "/obterListaConta")
	public ResponseEntity<Map<String, Object>> findAllPageConta(
			@RequestParam(value = "dataVencimento" ,required = false, defaultValue = "") String dataVencimento,
			@RequestParam(value = "descricao",required = false, defaultValue = "") String descricao,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "rowsPerPage", required = false, defaultValue = "10") int size) {
		if(page < 1) page = 1;
		Map<String, Object> response = null;
		if(dataVencimento.equals("")) {
			response = contaService.findAllPageConta(PageRequest.of(page-1, size), descricao.toLowerCase());
		}else {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date dataFormatada = formato.parse(dataVencimento); 
				response = contaService.findAllPageConta(PageRequest.of(page-1, size), dataFormatada, descricao.toLowerCase());
			} catch (ParseException e) {}
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(method = "GET", description = "Busca os dados da conta pelo id", summary = "Obter conta filtrando o id")
	@GetMapping(value = "/obterValorTotalPago")
	public ResponseEntity<Map<String, Object>> obterValorTotalPago(
			@RequestParam(value = "dataPagamentoInicio" ,required = false, defaultValue = "") String dataPagamentoInicio,
			@RequestParam(value = "dataPagamentoFim" ,required = false, defaultValue = "") String dataPagamentoFim)  {
		String valorTotal = "0,00";
		if(!dataPagamentoInicio.equals("") && !dataPagamentoFim.equals("")) {
			valorTotal = contaService.obterValorTotalPago(dataPagamentoInicio, dataPagamentoFim);
		}
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("valorTotal", valorTotal);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@ApiOperation(method = "GET", description = "Busca os dados da conta pelo id", summary = "Obter conta filtrando o id")
	@GetMapping(value = "/obterContaPorId/{id}")
	public Object obterContaPorId(@PathVariable(name="id") UUID id)  {
		return contaService.obterContaPorId(id);
	}
	
	
	@ApiOperation(method = "POST", description = "Cadastra as contas a pagar", summary = "Cadastrar conta")
	@PostMapping(value = "/cadastraConta", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ContaReponseDTO cadastraConta(@RequestBody ContaRequestDTO contaRequestDTO)  {
		return contaService.salvarConta(contaRequestDTO);
	}
	
	@ApiOperation(method = "POST", description = "Importação de contas a pagar via arquivo CSV", summary = "Importaçao conta via CSV")
	@PostMapping(value = "/importaContaCSV", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RetornoReponseDTO importaContaCSV(@RequestPart("file") final MultipartFile file)  {
		return contaService.importaContaCSV(file);
	}
	
	@ApiOperation(method = "PUT", description = "Altera os dados da conta a pagar", summary = "Atualizar conta")
	@PutMapping(value = "/atualizaConta/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public RetornoReponseDTO atualizaConta(@PathVariable(name="id") UUID id, @RequestBody ContaRequestDTO contaRequestDTO)  {
		return contaService.atualizaConta(id, contaRequestDTO);
	}
	
	@ApiOperation(method = "PUT", description = "Altera a situação da conta", summary = "Atualizar a situação da conta")
	@PutMapping(value = "/atualizaSituacao/{id}")
	public RetornoReponseDTO atualizaSituacao(@PathVariable(name="id") UUID id,  @RequestParam(value = "situacao", required = true) String situacao)  {
		return contaService.atualizaSituacao(id, situacao);
	}

}
