package auto.reg.maker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;

@Component
public class DriverFactory
{
    @Value("${web-driver.selenium-dir}")
    String seleniumDir;

    @Value("${web-driver.type}")
    String type;

    public  WebDriver create()
    {
        return switch (type) {
            case "local" -> createLocalDriver();
            case "node" -> createNodeDriver();
            default -> throw new RuntimeException(String.format("Unexpected driver type=%s ", type));
        };
    }

    private WebDriver createNodeDriver()
    {
        File file = new File(".");
        ChromeOptions options = new ChromeOptions();
        options.addExtensions (new File(seleniumDir + "/capchaRes.crx"));
        URL hubUrl = null;
        try
        {
            hubUrl = new URL("http://localhost:4444/wd/hub");
            return new RemoteWebDriver(hubUrl, options);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("Error creating node webDriver");
    }

    private WebDriver createLocalDriver()
    {
        File file = new File(".");
        System.setProperty(
                "webdriver.chrome.driver",
                 seleniumDir + "/chromedriver"
        );

        //add extension
        ChromeOptions options = new ChromeOptions ();
        options.addExtensions (new File(seleniumDir + "/capchaRes.crx"));
        DesiredCapabilities capabilities = new DesiredCapabilities ();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return new ChromeDriver(options);
    }
}
