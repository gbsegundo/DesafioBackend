package br.com.conta;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.conta.application.service.ContaService;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTests {

	@Mock
	private ContaService service;

	private static EasyRandom easyRandom;

	@BeforeAll
	public static void beforeTests() {
		easyRandom = new EasyRandom();
	}

	@Test
	public void findAllPageConta() {
		Map<String, Object> expectedList = easyRandom.nextObject(HashMap.class);
		Mockito.when(service.findAllPageConta(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(expectedList);
		Map<String, Object> list = service.findAllPageConta(ArgumentMatchers.any(), ArgumentMatchers.any());

		Assertions.assertTrue(list.isEmpty());
	}

	

}