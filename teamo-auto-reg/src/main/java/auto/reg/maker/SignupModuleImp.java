package auto.reg.maker;


import integration.dto.reg.RegTeamoUserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SignupModuleImp
{
    @Autowired
    DriverFactory driverFactory;
    int tryCounterLimit = 5;
    int secWaitLimit = 20;
    @Setter
    WebDriver driver;
    @Getter
    Map<String, String> teamoCookieValue;

    public void bypass(RegTeamoUserDto userToReg)
    {
        teamoCookieValue = new HashMap<>();
        log.info("Start by pass registration name={} email={}",userToReg.getName(), userToReg.getEmail() );
        driver = driverFactory.create();
        try
        {
            bypass(driver, userToReg);
        }
        finally
        {
            driver.quit();
        }
    }

    private Map<String, String> bypass(WebDriver driver,RegTeamoUserDto userToReg)
    {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMinutes(2));

        driver.manage().window().maximize();
        driver.get("https://teamo.ru/signup");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        driver.get("https://teamo.ru/signup");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        //get hCaptcha
        List<WebElement> iframes = driver.findElements(By.xpath("//iframe|//frame"));
        activateExt();
        waitHCaptchaResolved(iframes.get(0));

        //setFields
        waitDriver();
        setEmailPasswordFields(userToReg);
        waitDriver();
        clickAuthFormButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        waitDriver();

        teamoCookieValue.put("teamo", driver.manage().getCookieNamed("teamo").getValue());
        teamoCookieValue.put("teamo_remember_v2", driver.manage().getCookieNamed("teamo_remember_v2").getValue());

        return teamoCookieValue;
    }


    private void activateExt()
    {
        String winHandler = driver.getWindowHandle();

        //activate antiCaptcha
        driver.switchTo().newWindow(WindowType.TAB);
        //chrome-extension://hlifkpholllijblknnmbfagnkjneagid/popup/popup.html#/
        driver.get("chrome-extension://hlifkpholllijblknnmbfagnkjneagid/popup/popup.html#/");
        waitDriver();
        driver.close();
        driver.switchTo().window(winHandler);
        log.info("Activated ext anti captcha");
    }

    private void waitHCaptchaResolved(WebElement hCaptcha)
    {
        int tryCount = 0;
        while (hCaptcha.getAttribute("data-hcaptcha-response").isEmpty())
        {
            synchronized (hCaptcha)
            {
                try
                {
                    hCaptcha.wait(1000L * secWaitLimit);
                    if (tryCount > tryCounterLimit)
                    {
                        break;
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                    driver.close();
                    throw new RuntimeException(e.getMessage());
                }
            }
            tryCount++;
        }
        if (hCaptcha.getAttribute("data-hcaptcha-response").isEmpty())
        {
            log.warn("Can't resolved after {} sec {} try count limit",
                    secWaitLimit,
                    tryCount
            );
            driver.quit();
            throw new RuntimeException("Cant't resolved");
        }
        else
        {
            log.info("hCaptcha resolved");
        }
    }

    private void setEmailPasswordFields(RegTeamoUserDto userDto)
    {
        WebElement emailInputField = driver.findElement(By.id("user_email"));
        WebElement passwordInputField = driver.findElement(By.id("password"));

        emailInputField.sendKeys(userDto.getEmail());
        passwordInputField.sendKeys(userDto.getPassword());
    }

    public void clickAuthFormButton()
    {
        ((JavascriptExecutor) driver).executeScript(
                "document.getElementsByClassName('auth-form__button')[0].click()"
        );
    }

    private void waitDriver()
    {
        WebElement htmlBody = driver.findElement(By.tagName("body"));
        synchronized (htmlBody) {
            try
            {
                htmlBody.wait(1000 * 3);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                driver.quit();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
