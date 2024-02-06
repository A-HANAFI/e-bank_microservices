package net.ahmed.bank.cards.repositorie;

import net.ahmed.bank.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<Cards, Long> {
    Optional<Cards> findByMobileNumber (String mobileNumber);
    Optional<Cards> findByCardNumber(String cardNumber);
}
