package com.paw.trello.annotations;

import com.paw.trello.board.BoardData;
import com.paw.trello.card.CardData;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCard.Validator.class)
public @interface ValidCard {

    String message() default "{Invalid board: Check your name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<ValidCard, CardData> {

        @Override
        public void initialize(final ValidCard hasId) {
        }

        @Override
        public boolean isValid(final CardData card, final
        ConstraintValidatorContext constraintValidatorContext) {
            if(card.getTitle() == null)
                return false;
            return true;
        }
    }
}