package com.alga.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@JsonRootName("gastronomia") PARA ALTERAR O NOME DA TAG QUE REPRESENTA O CONTEÚDO TOTAL
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_cozinhas")
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnore A PRECEDENCIA DAS DENOTAÇÕES É IMPORTANTE E TEM EFEITO
	//@JsonProperty("cozinb")
	@Column(name = "nom_cozinha", nullable = false)
	private String nome;	
	
}
