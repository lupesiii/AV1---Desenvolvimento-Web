package com.autobots.automanager.domain.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Documento extends EntidadeBase {
	@Column
	private String tipo;
	@Column(unique = true)
	private String numero;
}