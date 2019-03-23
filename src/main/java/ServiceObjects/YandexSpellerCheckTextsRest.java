package ServiceObjects;

import ServiceObjects.constants.Options;
import ServiceObjects.constants.YandexSpellerConstants;
import beans.YandexSpellerAnswer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static ServiceObjects.constants.YandexSpellerConstants.PARAM_LANG;
import static ServiceObjects.constants.YandexSpellerConstants.PARAM_OPTIONS;
import static ServiceObjects.constants.YandexSpellerConstants.PARAM_TEXT;
import static org.hamcrest.Matchers.lessThan;

public class YandexSpellerCheckTextsRest {

    private YandexSpellerCheckTextsRest() {
    }

    public static final String YANDEX_SPELLER_URI =
            "https://speller.yandex.net/services/spellservice.json/checkTexts";

    private HashMap<String, String> params = new HashMap<String, String>();

    public static class RESTBuilder {

        YandexSpellerCheckTextsRest spellerApi;

        private RESTBuilder(YandexSpellerCheckTextsRest spellerApi) {
            this.spellerApi = spellerApi;
        }

        public RESTBuilder texts(String... text) {
            String result = Arrays.asList(text).toString();
            spellerApi.params.put(PARAM_TEXT, result.substring(1, result.length() - 1));
            return this;
        }

        public RESTBuilder options(Options... options) {
            Integer result = 0;
            for (Options i : options) {
                result += i.code;
            }
            spellerApi.params.put(PARAM_OPTIONS, result.toString());
            return this;
        }

        public RESTBuilder language(YandexSpellerConstants.Language... language) {
            StringBuilder result = new StringBuilder();
            for (YandexSpellerConstants.Language item :
                    language) {
                result.append(item.languageCode);
                result.append(", ");
            }
            result.delete(result.length() - 2, result.length());
            spellerApi.params.put(PARAM_LANG, result.toString());
            return this;

        }

        public Response callRest() {
            return RestAssured
                    .with()
                    .queryParams(spellerApi.params)
                    .log()
                    .all()
                    .get(YANDEX_SPELLER_URI).prettyPeek();
        }


    }

    public static RESTBuilder with() {
        YandexSpellerCheckTextsRest rest = new YandexSpellerCheckTextsRest();
        return new RESTBuilder(rest);
    }

    public static List<List<YandexSpellerAnswer>> getYandexSpellerAnswer(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<List<YandexSpellerAnswer>>>() {
        }.getType());
    }

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.XML)
                .setRelaxedHTTPSValidation()
                .addHeader("custom header2", "header2.value")
                .addQueryParam("requestID", new Random().nextLong())
                .setBaseUri(YANDEX_SPELLER_URI)
                .build();
    }

    public static ResponseSpecification responseSpecificationErrore1() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alives")
                .expectResponseTime(lessThan(10000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }


    public static ResponseSpecification successResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(10000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification badResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.TEXT)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(Matchers.lessThan(10000L))
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }

}
