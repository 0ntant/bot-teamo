package auto.reg.maker;

import integration.dto.reg.RegTeamoUserDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class RegPasser
{
    @Autowired
    DriverFactory driverFactory;
    int tryCounterLimit = 5;
    int secWaitLimit = 20;
    WebDriver driver;
    @Getter
    Cookie teamoCookie;

    public Cookie bypass(RegTeamoUserDto userToReg)
    {
        log.info("Start by pass registration name={} email={}",userToReg.getName(), userToReg.getEmail() );
        driver = driverFactory.create();
        try
        {
            bypass(driver, userToReg);
        }
        finally {
            driver.quit();
        }
        return teamoCookie;
    }

    private Cookie bypass(WebDriver driver,RegTeamoUserDto userToReg)
    {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMinutes(2));

        driver.manage().window().maximize();
        driver.get("https://teamo.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        driver.get("https://teamo.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        teamoCookie  = driver.manage().getCookieNamed("teamo");
        //get hCaptcha
        List<WebElement> iframes = driver.findElements(By.xpath("//iframe|//frame"));
        activateExt();
        waitHCaptchaResolved(iframes.get(0));

        //setFields
        setGender(userToReg);
        waitDriver();
        setInputFields(userToReg);
        waitDriver();
        clickSubmit();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        teamoCookie = driver.manage().getCookieNamed("teamo");

        return teamoCookie;
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
            synchronized (hCaptcha) {
                try
                {
                    hCaptcha.wait(1000L * secWaitLimit);
                    if (tryCount > tryCounterLimit)
                    {
                        break;
                    }
                }
                catch (InterruptedException e) {
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
            throw  new RuntimeException("Cant't resolved");
        }
        else
        {
            log.info("hCaptcha resolved");
        }
    }

    private void setGender(RegTeamoUserDto userDto)
    {
        if (userDto.getGender().equals("male"))
        {
            driver.findElements(By.tagName("select")).get(0).click();
            List<WebElement> gendersWEList = driver.findElements(By.tagName("option"));
            for(WebElement genderWE : gendersWEList )
            {
                if(genderWE.getAttribute("value").equals("1"))
                {
                    genderWE.click();
                    break;
                }
            }

        }
        else
        {
            driver.findElements(By.tagName("select")).get(0).click();
            List<WebElement> gendersWEList = driver.findElements(By.tagName("option"));
            for(WebElement genderWE : gendersWEList )
            {
                if(genderWE.getAttribute("value").equals("2"))
                {
                    genderWE.click();
                    break;
                }
            }
        }
    }

    private void setInputFields(RegTeamoUserDto userDto)
    {
        List<WebElement> inputField = driver.findElements(By.className("registration-page__form-field-input"));

        inputField.get(0).sendKeys(userDto.getName());
        inputField.get(1).sendKeys(userDto.getEmail());
        inputField.get(2).sendKeys(userDto.getPassword());
    }

    private void clickSubmit()
    {
        driver.findElement(By.className("registration-page__form-submit")).click();
    }

    private void waitDriver()
    {
        WebElement htmlBody = driver.findElement(By.tagName("body"));
        synchronized (htmlBody) {
            try {
                htmlBody.wait(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
                driver.quit();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}

