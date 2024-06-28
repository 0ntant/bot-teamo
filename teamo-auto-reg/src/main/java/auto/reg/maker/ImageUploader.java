package auto.reg.maker;

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

@Component
public class ImageUploader
{
    @Autowired
    DriverFactory driverFactory;
    WebDriver driver;
    int waitSecLimit = 3;

    public void uploadAvaImg(Cookie teamoCookie, String avaPath)
    {
        driver = driverFactory.create();

        try
        {
            uploadAvaImg(driver, teamoCookie, avaPath);
        }
        finally
        {
            driver.quit();
        }
    }

    private void uploadAvaImg(WebDriver driver, Cookie teamoCookie, String avaPath)
    {
        driver.get("https://teamo.ru/me/upload");
        driver.manage().addCookie(teamoCookie);
        driver.get("https://teamo.ru/me/upload");
        driver.manage().window().maximize();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMinutes(1));

        //add-photo-block__file-field
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dropZone")));
        WebElement fileInput = driver.findElement(By.tagName("input"));

        waitWebElement(fileInput);
        fileInput.sendKeys(avaPath);
        waitWebElement(fileInput);

        driver.get("https://teamo.ru/me/upload");
    }

    private void waitWebElement(WebElement webElement)
    {
        synchronized (webElement) {
            try {
                webElement.wait(1000L * waitSecLimit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
