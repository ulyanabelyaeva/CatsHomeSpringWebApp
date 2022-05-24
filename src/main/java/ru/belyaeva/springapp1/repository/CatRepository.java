package ru.belyaeva.springapp1.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.belyaeva.springapp1.model.Cat;

@Repository
public interface CatRepository extends CrudRepository<Cat, Long> {

}
