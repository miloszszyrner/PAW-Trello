package com.paw.trello.annotations;

import com.paw.trello.remark.RemarkData;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidRemark.Validator.class)
public @interface ValidRemark{

    String message() default "{Invalid remark: Check your name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ValidRemark, RemarkData> {

        @Override
        public void initialize(final ValidRemark hasId) {
        }

        @Override
        public boolean isValid(final RemarkData remark, final
        ConstraintValidatorContext constraintValidatorContext) {
            if(StringUtils.isEmpty(remark.getContent()))
                return false;
            return true;
        }
    }
}