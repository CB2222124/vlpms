package com.github.cb2222124.vlpms.backend.util;

/**
 * Regex expressions used to validate registrations.
 * Resource referenced: <a href="https://gist.github.com/danielrbradley/7567269">...</a>
 */
public class RegistrationRegex {
    public static final String ALL = "(^[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}$)|(^[A-Z][0-9]{1,3}[A-Z]{3}$)|(^[A-Z]{3}[0-9]{1,3}[A-Z]$)";
    public static final String CURRENT = "(^[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}$)";
    public static final String PREFIX = "(^[A-Z][0-9]{1,3}[A-Z]{3}$)";
    public static final String SUFFIX = "(^[A-Z]{3}[0-9]{1,3}[A-Z]$)";
}
