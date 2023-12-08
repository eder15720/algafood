package com.alga.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.alga.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@JsonRootName("gastronomia") PARA ALTERAR O NOME DA TAG QUE REPRESENTA O CONTEÚDO TOTAL
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cozinha")
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnore A PRECEDENCIA DAS DENOTAÇÕES É IMPORTANTE E TEM EFEITO
	//@JsonProperty("cozinb")
	@NotBlank(groups = Groups.CadastroRestaurante.class)
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "observacao")
	private String descricao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cozinha")
	private List<Restaurante> restaurantes = new ArrayList<>();
	
}
