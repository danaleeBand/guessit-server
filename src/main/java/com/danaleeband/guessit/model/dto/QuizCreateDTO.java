package com.danaleeband.guessit.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuizCreateDTO {

    @NotBlank
    @Size(max = 25)
    private String answer;

    @NotBlank
    @Size(max = 25)
    private String hint1;

    @NotBlank
    @Size(max = 25)
    private String hint2;

    @NotBlank
    @Size(max = 25)
    private String hint3;

    @NotBlank
    @Size(max = 25)
    private String hint4;

    @NotBlank
    @Size(max = 25)
    private String hint5;

    @NotBlank
    @Size(max = 25)
    private String hint6;

    // Getter, Setter
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHint1() {
        return hint1;
    }

    public void setHint1(String hint1) {
        this.hint1 = hint1;
    }

    public String getHint2() {
        return hint2;
    }

    public void setHint2(String hint2) {
        this.hint2 = hint2;
    }

    public String getHint3() {
        return hint3;
    }

    public void setHint3(String hint3) {
        this.hint3 = hint3;
    }

    public String getHint4() {
        return hint4;
    }

    public void setHint4(String hint4) {
        this.hint4 = hint4;
    }

    public String getHint5() {
        return hint5;
    }

    public void setHint5(String hint5) {
        this.hint5 = hint5;
    }

    public String getHint6() {
        return hint6;
    }

    public void setHint6(String hint6) {
        this.hint6 = hint6;
    }
}
