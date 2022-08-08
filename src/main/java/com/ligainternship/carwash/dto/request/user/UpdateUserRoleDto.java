package com.ligainternship.carwash.dto.request.user;

import com.ligainternship.carwash.dto.validate.role.ValidRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class UpdateUserRoleDto {

    @NotNull(message = "User id should not be null")
    @Positive(message = "User id should not be negative")
    private Long userId;

    @NotNull(message = "User id should not be null")
    @Positive(message = "User id should not be negative")
    @ValidRole
    private Long roleId;

}
