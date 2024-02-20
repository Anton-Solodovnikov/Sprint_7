import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import json.Courier;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierSteps {
    private CourierApi courierApi = new CourierApi();
    private ValidatableResponse response;
    @Step("Создание курьера")
    public void createCourier(Courier courier) {
        response = courierApi.createCourier(courier).then();
    }
    @Step("Проверка кода ответа")
    public void checkCourierStatusCode(int expectedCode) {
        response.statusCode(equalTo(expectedCode));
    }
    @Step("Проверка тела ответа")
    public void checkCourierBody(String jsonField, Object expectedMessage) {
        response.assertThat()
                .body(jsonField,equalTo(expectedMessage));
    }
    @Step("Удаление курьера")
    public void deleteCourier(Courier courier) {
        ValidatableResponse searchCourier = courierApi.loginCourier(courier).then();
        if (searchCourier.extract().statusCode() == 200){
            courierApi
                .deleteCourier(searchCourier
                        .extract()
                        .path("id"));
        }
    }
    @Step("Логин курьера в системе")
    public void loginCourier(Courier courier) {
        response = courierApi.loginCourier(courier)
                .then();
    }
    @Step("Проверка тела ответа после авторизации")
    public void checkLoginCourierBody() {
        response.assertThat()
                .body("id",notNullValue());
    }
    @Step("Логин с несуществующим логином и паролем")
    public void loginWithNonexistentData(Courier courier) {

    }






}
