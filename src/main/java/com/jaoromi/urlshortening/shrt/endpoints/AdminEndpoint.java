package com.jaoromi.urlshortening.shrt.endpoints;

import com.jaoromi.urlshortening.shrt.documents.RelativeWord;
import com.jaoromi.urlshortening.shrt.services.RelativeWordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class AdminEndpoint {

    @Inject
    private RelativeWordService relativeWordService;

    @RequestMapping(
            value = {"/admin/relative-words-init"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    public ResponseEntity<Void> init(@RequestBody List<RelativeWord> initData) {
        relativeWordService.initRelativeWord(initData);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
