import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import json.Courier;
import org.junit.After;
import org.junit.Test;

public class CreateCourierTests {
    private CourierSteps steps = new CourierSteps();
    private Faker faker = new Faker();
    private String login = faker.random().hex(15);
    private String password = faker.random().hex(15);
    private String name = faker.name().firstName();
    private Courier createBody = new Courier(login, password, name);
    private Courier loginBody = new Courier(login, password);
    private Courier withoutPasswordBody = new Courier(login);
    private Courier withoutLoginBody = new Courier(password);


    @After
    public void deleteCourier() {
        steps.deleteCourier(loginBody);
    }
    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Тестируем courierAPI")
    public void createCourierTest() {
        steps.createCourier(createBody);
        steps.checkCourierStatusCode(201);
        steps.checkCourierBody("ok",true);
    }
    @Test
    @DisplayName("Попытка создать два одинаковых курьера")
    @Description("Тестируем courierAPI")
    public void twoIdenticalCouriersTest() {
        steps.createCourier(createBody);
        steps.createCourier(createBody);
        steps.checkCourierStatusCode(409);
        steps.checkCourierBody("message","Этот логин уже используется. Попробуйте другой.");
    }
    @Test
    @DisplayName("Попытка создать курьера без пароля")
    @Description("Тестируем courierAPI")
    public void createCourierWithoutPasswordTest() {
        steps.createCourier(withoutPasswordBody);
        steps.checkCourierStatusCode(400);
        steps.checkCourierBody("message", "Недостаточно данных для создания учетной записи");
    }
    @Test
    @DisplayName("Попытка создать курьера без логина")
    @Description("Тестируем courierAPI")
    public void createCourierWithoutLoginTest() {
        steps.createCourier(withoutLoginBody);
        steps.checkCourierStatusCode(400);
        steps.checkCourierBody("message", "Недостаточно данных для создания учетной записи");
    }

}
