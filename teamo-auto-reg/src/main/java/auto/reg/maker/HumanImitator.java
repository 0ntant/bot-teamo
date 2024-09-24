package auto.reg.maker;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class HumanImitator
{
    @Autowired
    DriverFactory driverFactory;
    WebDriver driver;

    public void userDoSomeStuff(List<Cookie> teamoCookies)
    {
        driver = driverFactory.create();
        try
        {
            userDoSomeStuff(driver, teamoCookies);
        }
        finally
        {
            driver.quit();
        }
    }

    private void userDoSomeStuff(WebDriver driver, List<Cookie> teamoCookies)
    {
        driver.get("https://teamo.ru/faces");
        for(Cookie teamoCookie : teamoCookies)
        {
            driver.manage().addCookie(teamoCookie);
        }
        driver.get("https://teamo.ru/faces");
        driver.get("https://teamo.ru/faces");
        driver.manage().window().maximize();

        waitWebElement();

        driver.get("https://teamo.ru/faces");
        waitWebElement();

        driver.get("https://teamo.ru/selection");
        waitWebElement();

        driver.get("https://teamo.ru/search");
        waitWebElement();

        driver.get("https://teamo.ru/notifications/faces/income");
        waitWebElement();

        driver.get("https://teamo.ru/me/about");
        waitWebElement();
    }


    private void waitWebElement()
    {
        Random random = new Random();
        int secWait = random.nextInt(4, 22);
        synchronized (Thread.currentThread()) {
            try
            {
                Thread.currentThread().wait(1000L * secWait);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
