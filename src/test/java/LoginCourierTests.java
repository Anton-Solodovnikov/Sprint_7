import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import json.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginCourierTests {
    private CourierSteps steps = new CourierSteps();
    private Faker faker = new Faker();
    private String login = faker.random().hex(15);
    private String password = faker.random().hex(15);
    private String name = faker.name().firstName();
    private Courier createBody = new Courier(login, password, name);
    private Courier loginBody = new Courier(login, password);
    private Courier withoutPasswordBody = new Courier(login, "");
    private Courier withoutLoginBody = new Courier("",password);

    @Before
    public void createCourier() {
        steps.createCourier(createBody);
    }
    @After
    public void deleteCourier() {
        steps.deleteCourier(loginBody);
    }
    @Test
    @DisplayName("Авторизация курьера")
    @Description("Тестируем courierAPI")
    public void loginTest() {
        steps.loginCourier(loginBody);
        steps.checkLoginCourierBody();
        steps.checkCourierStatusCode(200);
    }
    @Test
    @DisplayName("Попытка авторизации без логина")
    @Description("Тестируем courierAPI")
    public void loginWithoutLoginTest(){
        steps.loginCourier(withoutLoginBody);
        steps.checkCourierBody("message","Недостаточно данных для входа");
        steps.checkCourierStatusCode(400);
    }
    @Test
    @DisplayName("Попытка авторизации без пароля")
    @Description("Тестируем courierAPI")
    public void loginWithoutPasswordTest(){
        steps.loginCourier(withoutPasswordBody);
        steps.checkCourierBody("message","Недостаточно данных для входа");
        steps.checkCourierStatusCode(400);
    }
    @Test
    @DisplayName("Попытка авторизации с несуществующим паролем и логином")
    @Description("Тестируем courierAPI")
    public void loginWithNonexistentDataTest() {
        String login = faker.random().hex(15);
        String password = faker.random().hex(15);
        Courier loginBody = new Courier(login, password);
        steps.loginCourier(loginBody);
        steps.checkCourierBody("message","Учетная запись не найдена");
        steps.checkCourierStatusCode(404);
    }
}
