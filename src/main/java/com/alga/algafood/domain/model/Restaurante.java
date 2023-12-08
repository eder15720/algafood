package com.alga.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.alga.algafood.core.validation.Groups;
import com.alga.algafood.core.validation.Multiplo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "restaurante")
public class Restaurante {

	@Valid  // garante que a aplicação terá que repassar neste ponto para efetuar as validações pré definidas no validator
	@NotNull // valores nulos nao serão aceitos
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank // valores em branco, somente espaços ou nnulos nao serão aceitos
	private String nome;

	@DecimalMin("0")
	@PositiveOrZero(message = "{TaxaFrete.invalida}")
	@Multiplo(numero = 5)
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	//@JsonIgnore
	//@JsonIgnoreProperties("hibernateLazyInitializer")
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
				joinColumns = @JoinColumn(name = "restaurante_id"), 
				inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

}
