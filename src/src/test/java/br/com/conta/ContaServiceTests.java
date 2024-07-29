package br.com.conta;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.conta.application.input.ContaRequestDTO;
import br.com.conta.application.output.ContaReponseDTO;
import br.com.conta.application.service.ContaService;
import br.com.conta.controller.ContaController;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ContaController.class)
public class ContaServiceTests {

    @Mock
    private ContaService service;

    private static EasyRandom easyRandom;
    
	@Autowired
	private MockMvc mockMvc;

    @BeforeAll
    public static void beforeTests(){
        easyRandom = new EasyRandom();
    }



    @Test
    public void carregaConta(){
        ContaReponseDTO retornoConta = easyRandom.nextObject(ContaReponseDTO.class);
        Mockito.when(service.obterContaPorId(ArgumentMatchers.any())).thenReturn(retornoConta);
        Object conta = service.obterContaPorId(ArgumentMatchers.any());

        Assertions.assertNotNull(conta);
        Assertions.assertEquals(((ContaReponseDTO)conta).getId(), retornoConta.getId());
    }
    
	@Test
	public void cadastraConta() throws Exception {

		ContaRequestDTO empStub = new ContaRequestDTO();
		empStub.setId(UUID.randomUUID());
		
		ContaReponseDTO response = new ContaReponseDTO();
		empStub.setId(UUID.randomUUID());
		when(service.salvarConta(any(ContaRequestDTO.class))).thenReturn(response);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/conta/cadastraConta").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(empStub))).andReturn();

		// verify that service method was called once
		verify(service).salvarConta(any(ContaRequestDTO.class));

		ContaReponseDTO resultEmployee = TestUtils.jsonToObject(result.getResponse().getContentAsString(), ContaReponseDTO.class);
		assertNotNull(resultEmployee);
		

	}


}