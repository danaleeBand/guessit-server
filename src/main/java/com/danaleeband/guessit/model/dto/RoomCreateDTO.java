package com.danaleeband.guessit.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCreateDTO {

    @NotBlank
    @Size(max = 25)
    private String title;

    @NotBlank
    @Size(max = 25)
    private String password;

    @NotNull
    private Boolean Locked;
}
