package com.nalstudio.springjms.api.controller;

import com.nalstudio.springjms.api.hateoas.TacoResource;
import com.nalstudio.springjms.api.hateoas.TacoResourceAssembler;
import com.nalstudio.springjms.data.TacoRepository;
import com.nalstudio.springjms.domain.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RepositoryRestController
public class RecentTacosController {
    private TacoRepository tacoRepository;

    public RecentTacosController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    //Hypertext Application Language (HAL)
    //API Response 메시지에 적용하면 그 메시지가 JSON 포맷이건 XML 포맷이건 API를 쉽게 찾을 수 있는 메타 정보들을 포함시킬 수 있다는 것
    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TacoResource>> reentTacos() {
        PageRequest page = PageRequest.of(
                0,12, Sort.by("createAt").descending());
        List<Taco> tacos = tacoRepository.findAll(page).getContent();

        CollectionModel<TacoResource> tacoResources = new TacoResourceAssembler().toCollectionModel(tacos);
        CollectionModel<TacoResource> recentResources = new CollectionModel<TacoResource>(tacoResources);

        recentResources.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RecentTacosController.class).reentTacos()).withRel("recent"));
        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }
}
