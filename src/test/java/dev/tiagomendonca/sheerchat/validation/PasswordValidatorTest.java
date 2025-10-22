package dev.tiagomendonca.sheerchat.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }

    @Test
    void testValidPassword_WithLettersNumbersAndSymbols() {
        assertTrue(validator.isValid("Password123!", null));
        assertTrue(validator.isValid("Abc1@def", null));
        assertTrue(validator.isValid("Test123#", null));
        assertTrue(validator.isValid("Senha@123", null));
    }

    @Test
    void testInvalidPassword_MissingLetters() {
        assertFalse(validator.isValid("12345678!", null));
        assertFalse(validator.isValid("987654@#", null));
    }

    @Test
    void testInvalidPassword_MissingNumbers() {
        assertFalse(validator.isValid("Password!", null));
        assertFalse(validator.isValid("Abcdefgh@", null));
    }

    @Test
    void testInvalidPassword_MissingSymbols() {
        assertFalse(validator.isValid("Password123", null));
        assertFalse(validator.isValid("Abc123def", null));
    }

    @Test
    void testValidPassword_NullOrEmpty() {
        // Null and empty are handled by @NotBlank
        assertTrue(validator.isValid(null, null));
        assertTrue(validator.isValid("", null));
    }
}
