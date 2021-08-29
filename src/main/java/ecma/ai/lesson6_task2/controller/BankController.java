package ecma.ai.lesson6_task2.controller;

import ecma.ai.lesson6_task2.entity.Bank;
import ecma.ai.lesson6_task2.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize(value = "hasAnyRole('DIRECTOR')")
@RestController
@RequestMapping("api/bank")
public class BankController {
    @Autowired
    BankService bankService;

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok().body(bankService.getAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(bankService.getById(id));
    }

    @PostMapping
    public HttpEntity<?> addBank(@Valid @RequestBody Bank dto) {
        return ResponseEntity.ok().body(bankService.addBank(dto));
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editBank(@Valid @RequestBody Bank dto, @PathVariable Integer id) {
        return ResponseEntity.ok().body(bankService.editBank(id, dto));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteBank(@PathVariable Integer id) {
        return ResponseEntity.ok().body(bankService.delete(id));
    }
}
