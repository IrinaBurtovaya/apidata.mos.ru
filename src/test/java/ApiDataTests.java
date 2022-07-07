import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ApiDataTests extends TestBase{

    @Test
    @DisplayName("Проверка общего количества наборов данных")
    void checkTotalCount() {
        given()
                .spec(Specs.request)
                .formParam("$inlinecount", "allpages")
                .get()
                .then()
                .spec(Specs.response)
                .body("Count", is(1017));
    }

    @Test
    @DisplayName("Проверка ограничения количества наборов на вывод")
    void checkDatasetCount() {
        List<Dataset> datasets =
                given()
                        .spec(Specs.request)
                        .formParam("$top", 2)
                        .get()
                        .then()
                        .spec(Specs.response)
                        .extract().jsonPath().getList(".", Dataset.class);
        Assertions.assertEquals(2, datasets.size());
    }

    @Test
    @DisplayName("Проверка сортировки данных по Caption")
    void checkOrderBy() {
        List<Dataset> datasets =
                given()
                        .spec(Specs.request)
                        .formParam("$orderby", "Caption")
                        .get()
                        .then()
                        .spec(Specs.response)
                        .extract().jsonPath().getList(".", Dataset.class);
        Assertions.assertEquals(3181, datasets.stream().findFirst().get().getId());
    }

    @Test
    @DisplayName("Проверка набора данных по id")
    void checkSpecificDataset() {
        given()
                .spec(Specs.request)
                .basePath("/658")
                .get()
                .then()
                .spec(Specs.response)
                .body("Caption", is("Тарифы на проезд в городском пассажирском транспорте"))
                .body("Description", is("Тарифы на проезд, действительные в городском пассажирском транспорте" +
                        " города Москвы"));
    }

    @Test
    @DisplayName("Проверка количества строк конкретного набора данных")
    void checkRowsCount() {
        given()
                .spec(Specs.request)
                .basePath("/493/count")
                .get()
                .then()
                .spec(Specs.response)
                .body(equalTo("78"));
    }

    @Test
    @DisplayName("Проверка наличия конкретного значения среди всех наборов данных")
    void checkDatasetsList() {
        List<Dataset> datasets =
                given()
                        .spec(Specs.request)
                        .get()
                        .then()
                        .spec(Specs.response)
                        .extract().jsonPath().getList(".", Dataset.class);
        Assertions.assertTrue(datasets.stream().anyMatch(x -> x.getCaption().contains("Дома культуры и клубы")));
    }

}
