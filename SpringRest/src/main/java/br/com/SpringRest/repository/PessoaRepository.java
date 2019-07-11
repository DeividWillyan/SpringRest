package br.com.SpringRest.repository;

import br.com.SpringRest.model.Pessoa;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, Long> {
}
