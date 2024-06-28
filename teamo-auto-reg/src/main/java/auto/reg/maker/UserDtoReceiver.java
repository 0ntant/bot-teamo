package auto.reg.maker;

import integration.dto.UserTeamoDto;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class UserDtoReceiver
{
    @Autowired
    DriverFactory driverFactory;

    public UserTeamoDto receive(Cookie teamoCookie)
    {
        WebDriver driver = driverFactory.create();
        UserTeamoDto userTeamoDto = new UserTeamoDto();

        try
        {
            userTeamoDto = receive(driver, teamoCookie);
        }
        finally
        {
            driver.quit();
        }

        return userTeamoDto;
    }

    private UserTeamoDto receive(WebDriver driver, Cookie teamoCookie)
    {
        UserTeamoDto userTeamoDto = new UserTeamoDto();
        driver.get("https://teamo.ru/");

        driver.manage().addCookie(teamoCookie);
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMinutes(1));

        driver.get("https://teamo.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        JavascriptExecutor js =  ((JavascriptExecutor)driver);
        userTeamoDto.setUserId(Long.valueOf(getItem(js, "user_id")));
        userTeamoDto.setToken(getItem(js, "token"));

        return userTeamoDto;
    }

    String getItem(JavascriptExecutor js, String itemName)
    {
        return (String) js.executeScript(String.format(
                "return window.localStorage.getItem('%s');", itemName));
    }
}
