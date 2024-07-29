package br.com.conta;

import java.util.Locale;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContaApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		Locale.setDefault(new Locale("pt", "BR"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ContaApplication.class, args);
	}

}
