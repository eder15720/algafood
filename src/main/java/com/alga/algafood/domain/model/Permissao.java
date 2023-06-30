package com.alga.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_permissao")
public class Permissao {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nom_permissao", nullable = false)
	private String nome;
	
	private String descricao;
	
	@ManyToMany
	@JoinTable(	name = "permissao_grupo", 
				joinColumns = @JoinColumn(name = "permissao_id"), 
				inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List<Grupo> grupoPermissoes = new ArrayList<>(); 

}
