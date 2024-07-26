package br.com.conta.application.input;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequestDTO {

    private UUID id;
    private String dataVencimento;
    private String dataPagamento;
    private String valor;
    private String descricao;
	private String situacao;

}
