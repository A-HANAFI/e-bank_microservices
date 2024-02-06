package net.ahmed.bank.cards.service.impl;

import lombok.AllArgsConstructor;
import net.ahmed.bank.cards.DTO.CardsDto;
import net.ahmed.bank.cards.constants.CardsConstants;
import net.ahmed.bank.cards.entity.Cards;
import net.ahmed.bank.cards.exception.CardAlreadyExistsException;
import net.ahmed.bank.cards.exception.ResourceNotFoundException;
import net.ahmed.bank.cards.mapper.CardsMapper;
import net.ahmed.bank.cards.repositorie.CardsRepository;
import net.ahmed.bank.cards.service.ICardsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service @AllArgsConstructor
public class CardsServiceImpl implements ICardsService {
    CardsRepository cardsRepository;
    /**
     * @param mobileNumber
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> cards =cardsRepository.findByMobileNumber(mobileNumber);
        if (cards.isPresent()){
            throw new CardAlreadyExistsException("cards found with same phone number");
        }
        cardsRepository.save(createNewCard(mobileNumber));

    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("cards","phone number",mobileNumber)
        );
        return CardsMapper.toCardsDto(cards,new CardsDto());
    }

    /**
     * @param cardsDto
     * @return
     */
    @Override
    public boolean updateCards(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("cards","phone number", cardsDto.getMobileNumber())
        );
        CardsMapper.toCards(cardsDto,cards);
        cardsRepository.save(cards);
        return true;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("card","phone number",mobileNumber)
        );
        cardsRepository.delete(cards);
        return true;
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }
}
