package com.jaoromi.urlshortening.shrt.services;

import com.jaoromi.urlshortening.shrt.documents.RelativeWord;
import com.jaoromi.urlshortening.shrt.repositories.RelativeWordRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RelativeWordService  {

    @Inject
    private RelativeWordRepository repository;

    @PostConstruct
    public void init() {
        Stream.of(
                RelativeWord.builder()
                        .domain("facebook.com")
                        .relativeWord("fcbk")
                        .build(),
                RelativeWord.builder()
                        .domain("instagram.com")
                        .relativeWord("inst")
                        .build(),
                RelativeWord.builder()
                        .domain("kbstar.com")
                        .relativeWord("wrbk")
                        .build()
        )
                .forEach(relativeWord -> repository.save(relativeWord));
    }

    public void initRelativeWord(Collection<RelativeWord> initData) {
        initData.stream()
                .forEach(relativeWord -> repository.save(relativeWord));
    }

    public Optional<String> getRegistedRelativeWord(String domain) {
        RelativeWord exists = repository.findOne(DomainUtils.meaningful(domain));

        return exists == null ? Optional.empty() : Optional.of(exists.getRelativeWord());
    }


}
