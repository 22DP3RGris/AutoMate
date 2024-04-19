package org.openjfx;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void validateUsernameTest(){
        assertTrue(Validator.validateUsername("John"));
        assertTrue(Validator.validateUsername("12345"));
        assertTrue(Validator.validateUsername("john-doe"));
        assertTrue(Validator.validateUsername("123"));
        assertTrue(Validator.validateUsername("abcdefghabcdefgh"));
        assertFalse(Validator.validateUsername(""));
        assertFalse(Validator.validateUsername("abcdefghabcdefgha"));
        assertFalse(Validator.validateUsername("@fsdfdsff32f3f2f3f3fsf3"));
        assertFalse(Validator.validateUsername("Jane@fdfd"));
        assertFalse(Validator.validateUsername("a"));
    }

    @Test
    public void validatePasswordTest(){
        assertTrue(Validator.validatePassword("123456"));
        assertTrue(Validator.validatePassword("abcdef"));
        assertTrue(Validator.validatePassword("T@12#6"));
        assertTrue(Validator.validatePassword("23523SFfs0f&#"));
        assertTrue(Validator.validatePassword("12345678901234567890"));
        assertFalse(Validator.validatePassword(""));
        assertFalse(Validator.validatePassword("12345"));
        assertFalse(Validator.validatePassword("123456789012345678901"));
    }

    @Test
    public void validateEmailTest(){
        assertTrue(Validator.validateEmail("test@gmail.com"));
        assertTrue(Validator.validateEmail("test.test@gmail.com"));
        assertTrue(Validator.validateEmail("test.test.test@gmail.com"));
        assertTrue(Validator.validateEmail("test.test12@gmail.com"));
        assertTrue(Validator.validateEmail("test@rvt.lv"));
        assertTrue(Validator.validateEmail("test@inbox.lv"));
        assertTrue(Validator.validateEmail("t@gmail.com"));
        assertTrue(Validator.validateEmail("abcdefghijklmnoprstuzh0123456789abcdefgh@gmail.com"));
        assertFalse(Validator.validateEmail("abcdefghijklmnoprstuzh0123456789abcdefghi@gmail.com"));
        assertFalse(Validator.validateEmail(""));
        assertFalse(Validator.validateEmail("test@"));
        assertFalse(Validator.validateEmail("test@."));
        assertFalse(Validator.validateEmail("test@.com"));
        assertFalse(Validator.validateEmail("test@com"));
        assertFalse(Validator.validateEmail("test@com."));
        assertFalse(Validator.validateEmail("@gmail.com"));
        assertFalse(Validator.validateEmail("@..."));

    }
}
