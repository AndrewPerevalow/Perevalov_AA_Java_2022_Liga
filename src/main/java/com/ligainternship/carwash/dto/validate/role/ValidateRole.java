package com.ligainternship.carwash.dto.validate.role;

import com.ligainternship.carwash.exception.InvalidRoleException;
import com.ligainternship.carwash.model.entitiy.Role;
import com.ligainternship.carwash.model.enums.Roles;
import com.ligainternship.carwash.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Slf4j
public class ValidateRole implements ConstraintValidator<ValidRole, Long> {

    private final RoleService roleService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        try {
            isRoleValid(value);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isRoleValid(Long id) {
        Role role = roleService.findById(id);
        if (!role.getName().equals(Roles.OPERATOR.getRole()) &&
                !role.getName().equals(Roles.ADMIN.getRole())) {
            String message = String.format("Roles should be: %s or %s", Roles.OPERATOR.getRole(), Roles.ADMIN.getRole());
            log.error(message);
            throw new InvalidRoleException(message);
        }
        return true;
    }
}
