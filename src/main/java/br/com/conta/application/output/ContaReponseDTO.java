package br.com.conta.application.output;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaReponseDTO {

	private UUID id;
    private String dataVencimento;
    private String dataPagamento;
    private String valor;
    private String descricao;
	private String situacao;
   

}
