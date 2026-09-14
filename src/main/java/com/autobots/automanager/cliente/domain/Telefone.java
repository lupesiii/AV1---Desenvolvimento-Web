package com.autobots.automanager.cliente.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Telefone extends EntidadeBase {
	@Column
	private String ddd;
	@Column
	private String numero;
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	public static String formataNumero(String numeroNaoFormatado) {
		return numeroNaoFormatado.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
	}
}