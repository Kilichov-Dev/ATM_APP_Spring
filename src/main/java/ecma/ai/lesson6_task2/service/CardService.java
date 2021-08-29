package ecma.ai.lesson6_task2.service;

import ecma.ai.lesson6_task2.entity.Card;
import ecma.ai.lesson6_task2.entity.User;
import ecma.ai.lesson6_task2.payload.ApiResponse;
import ecma.ai.lesson6_task2.payload.CardDto;
import ecma.ai.lesson6_task2.payload.ClientDto;
import ecma.ai.lesson6_task2.repository.CardRepository;
import ecma.ai.lesson6_task2.repository.RoleRepository;
import ecma.ai.lesson6_task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    public List<Card> getCardList() {
        return cardRepository.findAllByActiveIsFalse();
    }

    public ApiResponse getById(Integer id) {
        Optional<Card> byId = cardRepository.findById(id);
        return byId.map(card -> new ApiResponse("Success card found!", true, card)).orElseGet(() -> new ApiResponse("Card not found!", false));
    }

    public ApiResponse addCardToClient(ClientDto clientDto) {
        List<Card> allByActiveIsFalse = cardRepository.findAllByActiveIsFalse();
        Card card = allByActiveIsFalse.get(0);
        User user = new User();
        user.setFullName(clientDto.getFullName());
        user.setRole(roleRepository.getOne(3));
        card.setUser(user);
        card.setActive(true);
        card.setFullName(clientDto.getFullName());
        userRepository.save(user);
        cardRepository.save(card);
        return new ApiResponse("Card to client added successfully!!!", true);
    }

    public ApiResponse addCard(CardDto cardDto) {
        boolean byBankAndUserAndCardType = cardRepository.findByBankAndUserAndCardType(cardDto.getBank(), cardDto.getUser(), cardDto.getCardType());
        if (byBankAndUserAndCardType) return new ApiResponse("Sizga bunday karta berolmaymiz!!!", false);
        Optional<Card> byNumber = cardRepository.findByNumber(cardDto.getNumber());
        if (byNumber.isPresent()) return new ApiResponse("Bunday numberli card ro'yxatdan o'tgan!", false);
        Card card = new Card();
        card.setCardType(cardDto.getCardType());
        card.setBank(cardDto.getBank());
        card.setCvv(cardDto.getCvv());
        card.setExpireDate(cardDto.getExpireDate());
        card.setFullName(cardDto.getFullName());
        card.setActive(true);
        card.setBlocked(true);
        card.setNumber(card.getNumber());
        card.setPinCode(card.getPinCode());
        card.setUser(cardDto.getUser());
        cardRepository.save(card);
        return new ApiResponse("Card added successfully!", true);

    }

    public ApiResponse editCard(Integer id, CardDto cardDto) {
        Optional<Card> byId = cardRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("This is id not found!", false);
        boolean byBankAndUserAndCardType = cardRepository.findByBankAndUserAndCardType(cardDto.getBank(), cardDto.getUser(), cardDto.getCardType());
        if (byBankAndUserAndCardType) return new ApiResponse("Sizga bunday karta berolmaymiz!!!", false);
        Optional<Card> byNumber = cardRepository.findByNumber(cardDto.getNumber());
        if (byNumber.isPresent()) return new ApiResponse("Bunday numberli card ro'yxatdan o'tgan!", false);
        Card card = byId.get();
        card.setCardType(cardDto.getCardType());
        card.setBank(cardDto.getBank());
        card.setCvv(cardDto.getCvv());
        card.setExpireDate(cardDto.getExpireDate());
        card.setFullName(cardDto.getFullName());
        card.setActive(true);
        card.setBlocked(true);
        card.setNumber(card.getNumber());
        card.setPinCode(card.getPinCode());
        card.setUser(cardDto.getUser());
        cardRepository.save(card);
        return new ApiResponse("Card edit successfully!", true);
    }

    public ApiResponse deletedCard(Integer id) {
        Optional<Card> byId = cardRepository.findById(id);
        if (!byId.isPresent()) return new ApiResponse("Card not found!", false);
        cardRepository.deleteById(id);
        return new ApiResponse("Card deleted!!!", true);
    }


}
