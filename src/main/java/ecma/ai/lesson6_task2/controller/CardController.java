package ecma.ai.lesson6_task2.controller;

import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.payload.CardDto;
import ecma.ai.lesson6_task2.payload.ClientDto;
import ecma.ai.lesson6_task2.repository.CardRepository;
import ecma.ai.lesson6_task2.repository.RoleRepository;
import ecma.ai.lesson6_task2.repository.UserRepository;
import ecma.ai.lesson6_task2.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CardService cardService;

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @PostMapping("/cardAddToClient")
    public HttpEntity<?> cardAddToClient(@RequestBody ClientDto clientDto) {
        ApiResponse apiResponse = cardService.addCardToClient(clientDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(cardService.getById(id));
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @PostMapping()
    public HttpEntity<?> addCard(@RequestBody CardDto cardDto) {
        ApiResponse apiResponse = cardService.addCard(cardDto);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @PutMapping("/{id}")
    public HttpEntity<?> editCard(@PathVariable Integer id, @RequestBody CardDto cardDto) {
        ApiResponse apiResponse = cardService.editCard(id, cardDto);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(cardService.deletedCard(id));
    }

}
