import config.CredentialsConfig;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class Specs {

    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

    public static RequestSpecification request = with()
            .baseUri("https://apidata.mos.ru/v1/datasets")
            .formParam("api_key", config.apiKey())
            .log().uri()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification response = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}