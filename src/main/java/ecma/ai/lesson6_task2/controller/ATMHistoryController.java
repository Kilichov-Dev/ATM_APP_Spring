package ecma.ai.lesson6_task2.controller;

import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.repository.ATMManagerHistoryRepository;
import ecma.ai.lesson6_task2.service.ATMHistoryService;
import ecma.ai.lesson6_task2.service.ATMService;
import ecma.ai.lesson6_task2.service.AtmManagerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize(value = "hasAnyRole('DIRECTOR')")
@RestController
@RequestMapping("/api/atmHistory")
public class ATMHistoryController {

    //Director uchun
    @Autowired
    ATMService atmService;

    @Autowired
    ATMHistoryService atmHistoryService;

    @Autowired
    ATMManagerHistoryRepository atmManagerHistoryRepository;

    @Autowired
    AtmManagerHistoryService atmManagerHistoryService;

    @GetMapping("/id")
    public HttpEntity<?> getATMAllHistory(@PathVariable Integer id) {
        ApiResponse apiResponse = atmHistoryService.getATMAllHistory(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/income/id")
    public HttpEntity<?> getDayIncome(@PathVariable Integer id) {
        ApiResponse apiResponse = atmHistoryService.getByDayIncome(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/outcome/id")
    public HttpEntity<?> getDayOutcome(@PathVariable Integer id) {
        ApiResponse apiResponse = atmHistoryService.getByDayOutcome(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/manager/id")
    public HttpEntity<?> getManagerMoney(@PathVariable Integer id) {
        ApiResponse apiResponse = atmManagerHistoryService.getManagerPullMoney(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/banknotes/{id}")
    public HttpEntity<?> getBanknotesAtmId(@PathVariable Integer id) {
        ApiResponse bankNotes = atmService.getBankNotes(id);
        return ResponseEntity.status(bankNotes.isSuccess() ? 200 : 409).body(bankNotes);

    }

}
