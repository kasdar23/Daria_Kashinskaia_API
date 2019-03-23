import ServiceObjects.YandexSpellerCheckTextsSoap;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static ServiceObjects.constants.Options.FIND_REPEAT_WORDS;
import static ServiceObjects.constants.Options.IGNORE_CAPITALIZATION;
import static ServiceObjects.constants.YandexSpellerConstants.Language.*;
import static entites.TestText.*;

public class SoapYandexSpellerCheckTexts {

    @Test
    public void checkMissingLetters() {
        YandexSpellerCheckTextsSoap
                .with()
                .texts(TEXT_MISSING_LETTERS_RU.blockWrong,
                        TEXT_MISSING_LETTERS_EN.blockWrong,
                        TEXT_MISSING_LETTERS_UK.blockWrong)
                .language(EN, RU, UK)
                .callSoap()
                .then()
                .body((Matchers.stringContainsInOrder
                        (Arrays.asList(TEXT_MISSING_LETTERS_RU.blockCorr,
                                TEXT_MISSING_LETTERS_EN.blockCorr,
                                TEXT_MISSING_LETTERS_UK.blockCorr))));
    }

    @Test
    public void checkCapitalLetters() {
        YandexSpellerCheckTextsSoap
                .with()
                .texts(TEXT_CAPITAL_LETTERS_RU.blockWrong,
                        TEXT_CAPITAL_LETTERS_EN.blockWrong,
                        TEXT_CAPITAL_LETTERS_UK.blockWrong)
                .language(EN, RU, UK)
                .callSoap()
                .then()
                .body(Matchers.stringContainsInOrder(
                        Arrays.asList(TEXT_CAPITAL_LETTERS_RU.blockCorr,
                                TEXT_CAPITAL_LETTERS_EN.blockCorr,
                                TEXT_CAPITAL_LETTERS_UK.blockCorr)));
    }

    @Test
    public void checkRepeatWords() {
        YandexSpellerCheckTextsSoap
                .with()
                .texts(TEXT_REPEAT_WORDS_RU.blockWrong,
                        TEXT_REPEAT_WORDS_EN.blockWrong,
                        TEXT_REPEAT_WORDS_UK.blockWrong)
                .language(EN, RU, UK)
                .options(FIND_REPEAT_WORDS)
                .callSoap()
                .then()
                .body(Matchers.stringContainsInOrder(
                        Arrays.asList(TEXT_REPEAT_WORDS_RU.blockCorr,
                                TEXT_REPEAT_WORDS_EN.blockCorr,
                                TEXT_REPEAT_WORDS_UK.blockCorr)));
    }

    @Test
    public void checkUseTwoOptions() {
        YandexSpellerCheckTextsSoap
                .with()
                .texts(TEXT_FOR_TWO_OPTIONS.blockWrong)
                .language(EN, RU, UK)
                .options(FIND_REPEAT_WORDS)
                .callSoap()
                .then()
                .body(Matchers.stringContainsInOrder(
                        Arrays.asList("code=\"3\"")));
        YandexSpellerCheckTextsSoap
                .with()
                .texts(TEXT_FOR_TWO_OPTIONS.blockWrong)
                .language(EN, RU, UK)
                .options(FIND_REPEAT_WORDS,IGNORE_CAPITALIZATION)
                .callSoap()
                .then()
                .body(Matchers.not(Matchers.stringContainsInOrder(
                        Arrays.asList("code=\"3\""))));
    }

}