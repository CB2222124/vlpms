package com.github.cb2222124.vlpms.backend.service;

import com.github.cb2222124.vlpms.backend.exception.TransferableException;
import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.repository.RegistrationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransferableService {

    private final RegistrationRepository registrationRepository;

    public TransferableService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public boolean transferable(String inputRegistration, int year) {
        Registration registration = registrationRepository.findById(inputRegistration).orElseThrow(() -> {
            throw new TransferableException(HttpStatus.NOT_FOUND, "Provided registration does not exist");
        });
        return switch (registration.getStyle()) {
            case "Current" -> currentRegNewerThanDate(registration.getRegistration(), LocalDate.ofYearDay(year, 1));
            case "Prefix" -> prefixRegNewerThanDate(registration.getRegistration(), LocalDate.ofYearDay(year, 1));
            case "Suffix" -> suffixRegNewerThanDate(registration.getRegistration(), LocalDate.ofYearDay(year, 1));
            default -> throw new TransferableException(HttpStatus.BAD_REQUEST,
                    "Provided registration style cannot be checked with this service");
        };
    }

    private boolean currentRegNewerThanDate(String registration, LocalDate date) {
        int identifier = Integer.parseInt(registration.substring(2, 4));
        if (identifier > 50) identifier -= 50;
        int year = 2000 + identifier;
        return date.isAfter(LocalDate.ofYearDay(year, 1));
    }

    private boolean prefixRegNewerThanDate(String registration, LocalDate date) {
        int year = switch (registration.charAt(0)) {
            case 'A' -> 1983;
            case 'B' -> 1984;
            case 'C' -> 1985;
            case 'D' -> 1986;
            case 'E' -> 1987;
            case 'F' -> 1988;
            case 'G' -> 1989;
            case 'H' -> 1990;
            case 'J' -> 1991;
            case 'K' -> 1992;
            case 'L' -> 1993;
            case 'M' -> 1994;
            case 'N' -> 1995;
            case 'P' -> 1996;
            case 'R' -> 1997;
            case 'S' -> 1998;
            case 'T', 'V' -> 1999;
            case 'W', 'X' -> 2000;
            case 'Y' -> 2001;
            default -> throw new TransferableException(HttpStatus.BAD_REQUEST,
                    "Provided registration style (Prefix) does not contain date information");
        };
        return date.isAfter(LocalDate.ofYearDay(year, 1));
    }

    private boolean suffixRegNewerThanDate(String registration, LocalDate date) {
        int year = switch (registration.charAt(registration.length() - 1)) {
            case 'A' -> 1963;
            case 'B' -> 1964;
            case 'C' -> 1965;
            case 'D' -> 1966;
            case 'E', 'F' -> 1967;
            case 'G' -> 1968;
            case 'H' -> 1969;
            case 'J' -> 1970;
            case 'K' -> 1971;
            case 'L' -> 1972;
            case 'M' -> 1973;
            case 'N' -> 1974;
            case 'P' -> 1975;
            case 'R' -> 1976;
            case 'S' -> 1977;
            case 'T' -> 1978;
            case 'V' -> 1979;
            case 'W' -> 1980;
            case 'X' -> 1981;
            case 'Y' -> 1982;
            default -> throw new TransferableException(HttpStatus.BAD_REQUEST,
                    "Provided registration style (Suffix) does not contain date information");
        };
        return date.isAfter(LocalDate.ofYearDay(year, 1));
    }
}
