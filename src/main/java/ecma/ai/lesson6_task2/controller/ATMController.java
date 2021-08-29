package ecma.ai.lesson6_task2.controller;

import ecma.ai.lesson6_task2.payload.*;
import ecma.ai.lesson6_task2.service.ATMService;
import ecma.ai.lesson6_task2.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/atm")
public class ATMController {
    @Autowired
    ATMService atmService;
    @Autowired
    MainService mainService;


    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @PostMapping
    public HttpEntity<?> addATM(@RequestBody ATMDto atmDto) {
        ApiResponse apiResponse = atmService.addAtm(atmDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        ApiResponse byId = atmService.getById(id);
        return ResponseEntity.status(byId.isSuccess() ? 200 : 409).body(byId);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @GetMapping
    public HttpEntity<?> getAllAtm() {
        ApiResponse all = atmService.getAll();
        return ResponseEntity.ok().body(all);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> editAtm(@PathVariable Integer id, @RequestBody ATMDto atmDto) {
        ApiResponse apiResponse = atmService.editAtm(atmDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteAtm(@PathVariable Integer id) {
        ApiResponse delete = atmService.delete(id);
        return ResponseEntity.status(delete.isSuccess() ? 200 : 409).body(delete);
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER','USER')")
    @PostMapping("/withdraw")
    public HttpEntity<?> withdrawl(@Valid @RequestBody AtmWithDrawDto withdrawalDTO) throws MessagingException {
        return ResponseEntity.ok().body(mainService.withDraw(withdrawalDTO));
    }

    @PreAuthorize(value = "hasAnyRole('DIRECTOR','MANAGER','USER')")
    @PostMapping("/pulToldir")
    public HttpEntity<?> pulToldir(@Valid @RequestBody PulQoyishDto pulQoyishDto) {
        return ResponseEntity.ok().body(mainService.pulQoyish(pulQoyishDto));
    }

}
