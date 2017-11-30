package com.paw.trello.annotations;

import com.paw.trello.lane.LaneData;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidLane.Validator.class)
public @interface ValidLane {

    String message() default "{Invalid board: Check your name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<ValidLane, LaneData> {

        @Override
        public void initialize(final ValidLane hasId) {
        }

        @Override
        public boolean isValid(final LaneData lane, final
        ConstraintValidatorContext constraintValidatorContext) {
            if(StringUtils.isEmpty(lane.getTitle()))
                return false;
            return true;
        }
    }
}
