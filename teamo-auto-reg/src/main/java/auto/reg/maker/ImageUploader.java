package auto.reg.maker;

import auto.reg.integration.rest.contentStorageService.ImgAvaClient;
import auto.reg.service.TempFileService;
import integration.dto.reg.ImageAvaDto;
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

@Component
public class ImageUploader
{
    @Autowired
    DriverFactory driverFactory;
    WebDriver driver;
    int waitSecLimit = 5;

    @Autowired
    ImgAvaClient imgAvaClient;

    @Autowired
    TempFileService tempFileService;

    public void uploadAvaImg(List<Cookie> teamoCookies, ImageAvaDto imageAvaDto)
    {
        driver = driverFactory.create();

        String avaPath = tempFileService.saveFromImageAvaDto(imageAvaDto);

        try
        {
            uploadAvaImg(driver, teamoCookies, avaPath);
        }
        finally
        {
            driver.quit();
            tempFileService.remove(avaPath);
        }
    }

    public void uploadAvaImg(List<Cookie> teamoCookies, String gender)
    {
        driver = driverFactory.create();

        String avaPath = tempFileService.saveFromImgAvaDto(
                imgAvaClient.getImgAvaDtoByGender(gender)
        );

        try
        {
            uploadAvaImg(driver, teamoCookies, avaPath);
        }
        finally
        {
            driver.quit();
            tempFileService.remove(avaPath);
        }
    }

    private void uploadAvaImg(WebDriver driver, List<Cookie> teamoCookies, String avaPath)
    {
        driver.get("https://teamo.ru/me/upload");

        for(Cookie teamoCookie : teamoCookies)
        {
            driver.manage().addCookie(teamoCookie);
        }

        driver.get("https://teamo.ru/me/upload");
        driver.get("https://teamo.ru/me/upload");
        driver.manage().window().maximize();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMinutes(1));

        //add-photo-block__file-field
        wait.until(ExpectedConditions.elementToBeClickable(By.id("dropZone")));
        WebElement fileInputZone = driver.findElement(By.tagName("input"));

        waitWebElement();
        fileInputZone.sendKeys(avaPath);
        waitWebElement();

        driver.get("https://teamo.ru/me/upload");
    }

    public boolean hasImgByGender(String gender)
    {
        return imgAvaClient.getCountByGender(gender) >= 1;
    }

    private void waitWebElement()
    {
        synchronized (Thread.currentThread()) {
            try
            {
                Thread.currentThread().wait(1000L * waitSecLimit);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
