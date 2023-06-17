package com.alga.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alga.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{

}
