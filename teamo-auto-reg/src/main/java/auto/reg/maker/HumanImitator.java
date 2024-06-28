package auto.reg.maker;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Component
public class HumanImitator
{
    @Autowired
    DriverFactory driverFactory;
    WebDriver driver;

    public void userDoSomeStuff(Cookie teamoCookie)
    {
        driver = driverFactory.create();
        try
        {
            userDoSomeStuff(driver, teamoCookie);
        }
        finally
        {
            driver.quit();
        }
    }

    private void userDoSomeStuff(WebDriver driver, Cookie teamoCookie)
    {
        driver.get("https://teamo.ru/faces");
        driver.manage().addCookie(teamoCookie);
        driver.get("https://teamo.ru/faces");
        driver.manage().window().maximize();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMinutes(1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        driver.get("https://teamo.ru/faces");
        wait.until(ExpectedConditions.elementToBeClickable(By.className("main-menu__item-title")));

        List<WebElement> titlesWE = driver.findElements(By.className("main-menu__item-title"));

        titlesWE.get(1).click();
        waitWebElement(titlesWE.get(1));

        titlesWE.get(3).click();
        waitWebElement(titlesWE.get(3));

        titlesWE.get(4).click();
        waitWebElement(titlesWE.get(4));

        driver.get("https://teamo.ru/me/about");
        wait.until(ExpectedConditions.elementToBeClickable(By.className("profile-photos__photo")));
    }


    private void waitWebElement(WebElement webElement)
    {
        Random random = new Random();
        int secWait = random.nextInt(4, 22);
        synchronized (webElement) {
            try {
                webElement.wait(1000L * secWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
