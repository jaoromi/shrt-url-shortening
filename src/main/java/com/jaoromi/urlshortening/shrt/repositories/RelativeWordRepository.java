package com.jaoromi.urlshortening.shrt.repositories;

import com.jaoromi.urlshortening.shrt.documents.RelativeWord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelativeWordRepository extends MongoRepository<RelativeWord, String> {
}
