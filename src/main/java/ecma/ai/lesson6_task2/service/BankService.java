package ecma.ai.lesson6_task2.service;

import ecma.ai.lesson6_task2.entity.Bank;
import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;

    public ApiResponse getById(Integer id) {
        Optional<Bank> byId = bankRepository.findById(id);
        return byId.map(bank -> new ApiResponse("Success", true, bank)).orElseGet(() -> new ApiResponse("Bank nor found!", false));
    }

    public ApiResponse getAll() {
        return new ApiResponse("success", true, bankRepository.findAll());
    }

    public ApiResponse addBank(Bank bank) {
        Bank bank1 = new Bank();
        bank1.setName(bank.getName());
        bankRepository.save(bank1);
        return new ApiResponse("Added success!!!", true);
    }

    public ApiResponse editBank(Integer id, Bank bank) {
        Optional<Bank> byId = bankRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("Bank not found!", false);
        Bank bank1 = byId.get();
        bank1.setName(bank.getName());
        bankRepository.save(bank1);
        return new ApiResponse("Edited success!!!", true);
    }

    public ApiResponse delete(Integer id) {
        Optional<Bank> byId = bankRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("Bank not found!", false);
        bankRepository.deleteById(id);
        return new ApiResponse("Delete success!!!", true);
    }


}
