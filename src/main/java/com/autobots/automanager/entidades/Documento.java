package com.autobots.automanager.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;

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