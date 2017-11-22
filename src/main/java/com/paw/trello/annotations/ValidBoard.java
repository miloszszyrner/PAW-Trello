package com.paw.trello.annotations;

import com.paw.trello.board.BoardData;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidBoard.Validator.class)
public @interface ValidBoard {

    String message() default "{Invalid board: Check your name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<ValidBoard, BoardData> {

        @Override
        public void initialize(final ValidBoard hasId) {
        }

        @Override
        public boolean isValid(final BoardData board, final
        ConstraintValidatorContext constraintValidatorContext) {
            if(StringUtils.isEmpty(board.getName()))
                return false;
            return true;
        }
    }
}
