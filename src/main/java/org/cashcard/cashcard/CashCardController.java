package org.cashcard.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    private final CashCardRepository cashCardRepository;
    CashCardController(CashCardRepository cashCardRepository){
        this.cashCardRepository = cashCardRepository;
    }
    @GetMapping("/{requestId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestId) {
        Optional<CashCard> response = cashCardRepository.findById(requestId);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createNewCashCard(@RequestBody CashCard cashCard, UriComponentsBuilder uriBuilder) {
        CashCard createdCashCard = cashCardRepository.save(cashCard);
        URI locationOfNewCashCard = uriBuilder.path("/cashcards/{id}")
                .buildAndExpand(createdCashCard.id()).toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
}
