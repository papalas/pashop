package cz.mcity.pashop.controller;

import cz.mcity.pashop.model.User;

import java.io.Serializable;

/**
 * DTO for {@link cz.mcity.pashop.model.User}
 */
public record UserDto(
        Long id,
        String username,
        String email
) implements Serializable {

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

}