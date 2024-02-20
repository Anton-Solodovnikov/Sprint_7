import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import json.Orders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersSteps {
    OrderApi orderApi = new OrderApi();
    private ValidatableResponse response;

    @Step("Создание заказа")
    public void createOrder(Orders orders) {
        response = orderApi.createOrder(orders)
                .then();
    }
    @Step("Проверка кода ответа")
    public void checkOrderStatusCode(int expectedCode) {
        response.statusCode(equalTo(expectedCode));
    }
    @Step("Проверка что тело ответа не null")
    public void responseNotNull() {
        response.body("track", notNullValue());
    }
    @Step("Проверка тела ответа")
    public void checkOrderBody(String jsonField, Object expectedMessage) {
        response.assertThat()
                .body(jsonField,equalTo(expectedMessage));
    }
    @Step("Проверка данных созданного заказа")
    public void checkOrderData(String jsonField, Object expectedData) {
        orderApi
                .getOrderByTrack(response
                        .extract()
                        .path("track").toString())
                .then()
                .assertThat()
                .body(jsonField,equalTo(expectedData));
    }
    @Step("Проверка списка заказов")
    public void checkOrdersList() {
        orderApi
                .getOrders()
                .then()
                .assertThat()
                .body("orders", notNullValue())
                .and()
                .statusCode(equalTo(200));
    }
    @Step("Отмена заказа по треку")
    public void cancelOrder() {
        orderApi.cancelOrder(
                response
                        .extract()
                        .path("track")
                        .toString()
        );
    }
}
