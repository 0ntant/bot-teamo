package auto.reg.maker;

import integration.dto.UserTeamoDto;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class LocalStorageReceiver
{
    @Autowired
    DriverFactory driverFactory;

    public UserTeamoDto receive(List<Cookie> teamoCookies)
    {
        WebDriver driver = driverFactory.create();
        UserTeamoDto userTeamoDto;

        try
        {
            userTeamoDto = receive(driver, teamoCookies);
        }
        finally
        {
            driver.quit();
        }

        return userTeamoDto;
    }

    private UserTeamoDto receive(WebDriver driver, List<Cookie> teamoCookies)
    {
        UserTeamoDto userTeamoDto = new UserTeamoDto();
        driver.get("https://teamo.ru/");
        driver.get("https://teamo.ru/");

        for (Cookie temoCookie : teamoCookies)
        {
            driver.manage().addCookie(temoCookie);
        }
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMinutes(1));

        driver.get("https://teamo.ru/me/signup-complete");
        driver.get("https://teamo.ru/me/signup-complete");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        JavascriptExecutor js = ((JavascriptExecutor)driver);
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
