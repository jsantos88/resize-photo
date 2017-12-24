package com.challenge.resizephoto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.challenge.resizephoto.resource.model.Imagem;

@Repository
public interface ImagemRepository extends MongoRepository<Imagem, String> {

}
