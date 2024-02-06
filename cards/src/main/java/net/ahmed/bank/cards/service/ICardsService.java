package net.ahmed.bank.cards.service;

import net.ahmed.bank.cards.DTO.CardsDto;

public interface ICardsService {

     void createCard(String mobileNumber);

     CardsDto fetchCard(String mobileNumber);

     boolean updateCards(CardsDto cardsDto);

     boolean deleteCard(String mobileNumber);
}
