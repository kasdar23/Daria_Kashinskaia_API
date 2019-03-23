import ServiceObjects.YandexSpellerCheckTextsRest;
import beans.YandexSpellerAnswer;
import com.jayway.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.List;

import static ServiceObjects.YandexSpellerCheckTextsRest.successResponseSpecification;
import static ServiceObjects.constants.Options.*;
import static ServiceObjects.constants.Options.IGNORE_CAPITALIZATION;
import static ServiceObjects.constants.SpellerErrors.ERROR_UNKNOWN_WORD;
import static ServiceObjects.constants.YandexSpellerConstants.Language.RU;
import static ServiceObjects.constants.YandexSpellerConstants.Language.UK;
import static ServiceObjects.constants.YandexSpellerConstants.PARAM_TEXT;
import static entites.TestText.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestYandexSpellerCheckTexts {

    @Test
    public void checkWrongContext(){
        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerCheckTextsRest.getYandexSpellerAnswer(
                        YandexSpellerCheckTextsRest
                                .with()
                                .language(RU)
                                .texts(TEXT_WRONG_CONTEXT.blockWrong)
                                .callRest());
        assertThat("Doesn't contain the expected value.", answers.get(0).get(0).getS().get(0),
                equalTo(TEXT_WRONG_CONTEXT.blockCorr));
    }

    //service doesn't work correct.
    //Ukrainian is selected, but it recognizes Russian and English words
    @Test
    public void checkWrongLanguage(){
        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerCheckTextsRest.getYandexSpellerAnswer(
                        YandexSpellerCheckTextsRest
                                .with()
                                .language(UK)
                                .texts(TEXT_MISSING_LETTERS_EN.blockCorr,
                                        TEXT_MISSING_LETTERS_RU.blockCorr)
                                .callRest());
        assertThat("Expected number of answers is wrong.", answers.get(0).size(),
                greaterThan(0));
    }

    @Test
    public void checkIgnoreDigits() {
        List<List<YandexSpellerAnswer>> answersIgnoreDigitsOff =
                YandexSpellerCheckTextsRest.getYandexSpellerAnswer(
                        YandexSpellerCheckTextsRest
                                .with()
                                .language(RU)
                                .texts(TEXT_WITH_DIGITS.blockWrong)
                                .callRest());
        assertThat("Option Ignore Digits is off. Expected number of answers is wrong.",
                answersIgnoreDigitsOff.get(0).size(), greaterThan(0));

        List<List<YandexSpellerAnswer>> answersIgnoreDigitsOn =
                YandexSpellerCheckTextsRest.getYandexSpellerAnswer(
                        YandexSpellerCheckTextsRest
                                .with()
                                .language(RU)
                                .options(IGNORE_DIGITS)
                                .texts(TEXT_WITH_DIGITS.blockWrong)
                                .callRest());
        assertThat("Option Ignore Digits is on. Expected number of answers is wrong.",
                answersIgnoreDigitsOn.get(0).size(), equalTo(0));
    }

    //service doesn't work correct.
    //Doesn't consider URI as an error even with the option Ignore Urls turned off
    @Test
    public void checkIgnoreUrls() {
        List<List<YandexSpellerAnswer>> answersIgnoreUrlsOff =
                YandexSpellerCheckTextsRest.getYandexSpellerAnswer(
                        YandexSpellerCheckTextsRest
                                .with()
                                .texts(TEXT_WITH_URLS.blockWrong)
                                .callRest());
        assertThat("Option Ignore Urls is off. Expected number of answers is wrong.",
                answersIgnoreUrlsOff.get(0).size(), greaterThan(0));

        List<List<YandexSpellerAnswer>> answersIgnoreUrlsOn =
                YandexSpellerCheckTextsRest.getYandexSpellerAnswer(
                        YandexSpellerCheckTextsRest
                                .with()
                                .options(IGNORE_URLS)
                                .texts(TEXT_WITH_URLS.blockWrong)
                                .callRest());
        assertThat("Option Ignore Urls is on. Expected number of answers is wrong.",
                answersIgnoreUrlsOn.get(0).size(), equalTo(0));
    }

    //service doesn't work correct.
    //Doesn't consider wrong capitalization as an error even with the option Ignore Capitalization turned off
    @Test
    public void checkIgnoreCapitalization() {
        List<List<YandexSpellerAnswer>> answersIgnoreCapitalizationOff =
                YandexSpellerCheckTextsRest.getYandexSpellerAnswer(
                        YandexSpellerCheckTextsRest
                                .with()
                                .texts(TEXT_CAPITAL_LETTERS_EN.blockWrong)
                                .callRest());
        assertThat("Option Ignore Capitalization is off. Expected number of answers is wrong.",
                answersIgnoreCapitalizationOff.get(0).size(), greaterThan(0));

        List<List<YandexSpellerAnswer>> answersIgnoreCapitalizationOn =
                YandexSpellerCheckTextsRest.getYandexSpellerAnswer(
                        YandexSpellerCheckTextsRest
                                .with()
                                .options(IGNORE_CAPITALIZATION)
                                .texts(TEXT_CAPITAL_LETTERS_EN.blockWrong)
                                .callRest());
        assertThat("Option Ignore Capitalization is on. Expected number of answers is wrong.",
                answersIgnoreCapitalizationOn.get(0).size(), equalTo(0));
    }

    @Test
    public void checkErrorUnknownWord() {
        RestAssured
                .given(YandexSpellerCheckTextsRest.requestSpecification())
                .param(PARAM_TEXT, TEXT_MISSING_LETTERS_EN.blockWrong)
                .log().all()
                .get().prettyPeek()
                .then().specification(successResponseSpecification())
                .body(Matchers.stringContainsInOrder("\"code\":" + ERROR_UNKNOWN_WORD.code));
    }

}
