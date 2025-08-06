package org.partiteweb.matchweb.components;

import org.partiteweb.matchweb.config.SecurityConfig;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class Validator {

    private final int MIN_AGE = 18;
    private final String CURRENT_AVAILABLE_SPORT = "cricket";
    private final String AVAILABLE_PW = "utente!" + SecurityConfig.TEAM_ID;

    public boolean isOldEnough(LocalDate userBd) {
        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusYears(MIN_AGE);
        return !userBd.isAfter(minDate);
    }

    public boolean isAvailableSport(String sport) {
        return Objects.equals(sport, CURRENT_AVAILABLE_SPORT);
    }

    public boolean isValidPw(String pw) {return Objects.equals(pw, AVAILABLE_PW);}
}

