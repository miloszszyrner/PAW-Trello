package com.paw.trello.annotations;

import com.paw.trello.User.UserData;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidUser.Validator.class)
public @interface ValidUser {

    String message() default "{Invalid user: Check your username, password or passwordConfirmation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<ValidUser, UserData> {

        @Override
        public void initialize(final ValidUser hasId) {
        }

        @Override
        public boolean isValid(final UserData user, final
        ConstraintValidatorContext constraintValidatorContext) {
            if(StringUtils.isEmpty(user.getUsername()))
                return false;
            if(StringUtils.isEmpty(user.getPassword()))
                return false;
            if(StringUtils.isEmpty(user.getPasswordConfirmation()))
                return false;
            if(!user.getPassword().equals(user.getPasswordConfirmation()))
                return false;
            return true;
        }
    }
}