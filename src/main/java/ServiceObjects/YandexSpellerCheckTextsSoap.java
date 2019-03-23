package ServiceObjects;

import ServiceObjects.constants.Options;
import ServiceObjects.constants.SoapAction;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.HashMap;

import static ServiceObjects.constants.YandexSpellerConstants.*;
import static ServiceObjects.constants.YandexSpellerConstants.PARAM_OPTIONS;
import static ServiceObjects.constants.YandexSpellerConstants.PARAM_TEXT;
import static entites.TestText.TEXT_MISSING_LETTERS_RU;

public class YandexSpellerCheckTextsSoap {

    static RequestSpecification spellerSoapReqSpec = new RequestSpecBuilder()
            .addHeader("Accept-Encoding", "gzip,deflate")
            .setContentType("text/xml;charset=UTF-8")
            .addHeader("Host", "speller.yandex.net")
            .setBaseUri("http://speller.yandex.net/services/spellservice")
            .build();

    private YandexSpellerCheckTextsSoap() {
    }

    private HashMap<String, String> params = new HashMap<String, String>();

    private SoapAction action = SoapAction.CHECK_TEXTS;

    public static class SOAPBuilder {
        YandexSpellerCheckTextsSoap soapReq;

        private SOAPBuilder(YandexSpellerCheckTextsSoap soapReq) {
            this.soapReq = soapReq;
        }

        public YandexSpellerCheckTextsSoap.SOAPBuilder action(SoapAction action) {
            soapReq.action = action;
            return this;
        }

        public YandexSpellerCheckTextsSoap.SOAPBuilder texts(String... text) {
            String result = Arrays.asList(text).toString();
            soapReq.params.put(PARAM_TEXT, result.substring(1, result.length() - 1));
            return this;
        }

        public YandexSpellerCheckTextsSoap.SOAPBuilder options(Options... options) {
            Integer result = 0;
            for (Options i : options) {
                result += i.code;
            }
            soapReq.params.put(PARAM_OPTIONS, result.toString());
            return this;
        }

        public YandexSpellerCheckTextsSoap.SOAPBuilder language(Language... language) {
            StringBuilder result = new StringBuilder();
            for (Language item :
                    language) {
                result.append(item.languageCode);
                result.append(", ");
            }
            result.delete(result.length() - 2, result.length());
            soapReq.params.put(PARAM_LANG, result.toString());
            return this;
        }

        public Response callSoap() {
            String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:spel=\"http://speller.yandex.net/services/spellservice\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <spel:" + soapReq.action.getReqName() + " lang=" + QUOTES +
                    (soapReq.params.getOrDefault(PARAM_LANG, "en")) + QUOTES
                    + " options=" + QUOTES + (soapReq.params.getOrDefault(
                    PARAM_OPTIONS, "0")) + QUOTES
                    + " format=\"\">\n" +
                    "         <spel:text>" + (soapReq.params.getOrDefault(
                    PARAM_TEXT, TEXT_MISSING_LETTERS_RU.blockWrong)) + "</spel:text>\n" +
                    "      </spel:" + soapReq.action.getReqName() + ">\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            return RestAssured.with()
                    .spec(spellerSoapReqSpec)
                    .header("SOAPAction", "http://speller.yandex.net/services/spellservice/" + soapReq.action.getMethod())
                    .body(soapBody)
                    .log().all().with()
                    .post().prettyPeek();
        }
    }

    public static SOAPBuilder with() {
        YandexSpellerCheckTextsSoap soap = new YandexSpellerCheckTextsSoap();
        return new SOAPBuilder(soap);
    }
}






















