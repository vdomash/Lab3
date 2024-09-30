package org.translation;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONTranslationExampleTest {

    private JSONTranslationExample jsonTranslationExample = new JSONTranslationExample();

    @Test
    public void getCountryNameTranslation() {
        String expected = jsonTranslationExample.getCanadaCountryNameSpanishTranslation();
        String result = jsonTranslationExample.getCountryNameTranslation("can", "es");
        assertEquals("Translating 'can' to 'es' should be " + expected + " but was " + result, expected, result);
    }

    @Test
    public void getItalyRussianTranslation() {
        String expected = jsonTranslationExample.getItalyCountryNameRussianTranslation();
        String result = jsonTranslationExample.getCountryNameTranslation("ita", "ru");
        assertEquals("Translating 'ita' to 'ru' should be " + expected + " but was " + result, expected, result);
    }

    @Test
    public void getNoCountryRussianTranslation() {
        String expected = "Country not found";
        String result = jsonTranslationExample.getCountryNameTranslation("", "ru");
        assertEquals(expected, result);
    }

    @Test
    public void getItalyNoTranslation() {
        String expected = "Country not found";
        String result = jsonTranslationExample.getCountryNameTranslation("ITA", "");
        assertEquals(expected, result);
    }
}