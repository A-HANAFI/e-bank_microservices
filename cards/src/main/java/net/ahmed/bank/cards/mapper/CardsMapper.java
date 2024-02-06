package net.ahmed.bank.cards.mapper;

import net.ahmed.bank.cards.DTO.CardsDto;
import net.ahmed.bank.cards.entity.Cards;

public class CardsMapper {

    public static Cards toCards(CardsDto cardsDto, Cards cards){
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setAvailableAmount(cards.getAvailableAmount());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        return cards;
    }
    public static CardsDto toCardsDto(Cards cards, CardsDto cardsDto){
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        return cardsDto;
    }
}
