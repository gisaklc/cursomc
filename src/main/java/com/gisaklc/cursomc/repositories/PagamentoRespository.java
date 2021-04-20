package com.gisaklc.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gisaklc.cursomc.domain.Pagamento;

@Repository
public interface PagamentoRespository extends JpaRepository<Pagamento, Integer> {

}
