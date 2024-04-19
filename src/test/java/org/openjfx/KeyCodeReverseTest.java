package org.openjfx;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class KeyCodeReverseTest {

    @Test
    public void reverseKeyCodeToUserDefaultCaseTest() {
        assertEquals(KeyCodeReverse.reverseKeyCodeToUser("1"), "1");
        assertEquals(KeyCodeReverse.reverseKeyCodeToUser("ABCDSF"), "Abcdsf");
        assertEquals(KeyCodeReverse.reverseKeyCodeToUser("AbcdefG"), "Abcdefg");
        assertEquals(KeyCodeReverse.reverseKeyCodeToUser("abcdefg"), "Abcdefg");
        assertEquals(KeyCodeReverse.reverseKeyCodeToUser(""), "");
    }
}
