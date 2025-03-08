package com.danaleeband.guessit.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RoomCreateDTO {

    @NotBlank
    @Size(max = 25)
    private String title;

    private String password;

    @NotNull
    private Boolean locked;

    @NotNull
    private Long creatorId;
}
