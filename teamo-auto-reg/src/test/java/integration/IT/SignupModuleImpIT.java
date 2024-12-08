package integration.IT;

import auto.reg.TeamoAutoReg;
import auto.reg.maker.DriverFactory;
import auto.reg.maker.SignupModuleImp;
import integration.dto.reg.RegTeamoUserDto;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TeamoAutoReg.class)
public class SignupModuleImpIT
{
    @Autowired
    DriverFactory driverFactory;

    @Autowired
    SignupModuleImp signupModule;

    @Test
    void clickCreateAccount_success()
    {
        //given
        WebDriver driver = driverFactory.create();
        signupModule.setDriver(driver);
        //then
        try
        {
            waitDriver(driver, 4);
            driver.get("https://teamo.ru/signup?channel%5Bentry_point_url%5D=%2F");
            driver.manage().window().maximize();
            waitDriver(driver, 6);

            signupModule.clickAuthFormButton();
            System.out.println("Button [CLICKED]");
            waitDriver(driver, 5);
        }
        finally
        {
            driver.quit();
        }
        //expected
    }

    @Test
    void bypass_activateExt()
    {
        //given
        WebDriver driver = driverFactory.create();
        signupModule.setDriver(driver);
        //then
        try {
            waitDriver(driver, 4);
            driver.get("https://teamo.ru/signup?channel%5Bentry_point_url%5D=%2F");
            driver.manage().window().maximize();
            waitDriver(driver, 6);
            signupModule.bypass(new RegTeamoUserDto());
        }finally {
            driver.quit();
        }
        //expected
    }
    private void waitDriver(WebDriver driver, int sec)
    {
        WebElement htmlBody = driver.findElement(By.tagName("body"));
        synchronized (htmlBody)
        {
            try
            {
                htmlBody.wait(1000L * sec);
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
