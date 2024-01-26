package com.rhoopoe.site.utility;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"test.PNG","TEST_/FILE.JPEG",
            "AaAAAAaaaaaaaAAA.jpeg", "452874.png"})
    void givenValidFilename_thenReturnExtension(String input) {
        String extension = FileUtils.getFileExtension(input);
        assertNotNull(extension);
        assertFalse(extension.isEmpty());
        assertTrue(extension.length() > 2 && extension.length() < 5);
        assertEquals(input.split("\\.")[1].toLowerCase(), extension);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test.a.PNG", "test.invalid", "test.cs",
            "", " ", ".PNG"})
    void givenInvalidFilename_thenThrowException(String input){
        assertThrows(InvalidFileNameException.class,
                () -> FileUtils.getFileExtension(input)
                );
    }
}