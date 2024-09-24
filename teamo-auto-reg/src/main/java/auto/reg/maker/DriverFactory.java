package auto.reg.maker;

import auto.reg.service.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;

@Slf4j
@Component
public class DriverFactory
{
    @Value("${web-driver.selenium-dir}")
    String seleniumDir;

    @Value("${web-driver.type}")
    String type;

    @Value("${web-driver.remote-url}")
    String remoteUrl;

    @Autowired
    ProxyService proxyService;

    public  WebDriver create()
    {
        return switch (type)
        {
            case "local" -> createLocalDriver();
            case "remote" -> createRemoteDriver();
            default -> throw new RuntimeException(String.format("Unexpected driver type=%s ", type));
        };
    }

    private WebDriver createRemoteDriver()
    {
//      ChromeOptions options = setProxyChromeOption(new ChromeOptions());
        ChromeOptions options = new ChromeOptions();
        options.addExtensions (new File(seleniumDir + "/capchaRes.crx"));
        try
        {
            URL remoteDriverUrl = new URL(remoteUrl);
            RemoteWebDriver driver =  new RemoteWebDriver(remoteDriverUrl, options);
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        throw new RuntimeException("Error creating node webDriver");
    }

    private WebDriver createLocalDriver()
    {
        System.setProperty(
                "webdriver.chrome.driver",
                 seleniumDir + "/chromedriver"
        );

        //add extension
        //ChromeOptions options = setProxyChromeOption(new ChromeOptions ());
        ChromeOptions options = new ChromeOptions();
        options.setBinary(seleniumDir + "/chromium");
        options.addExtensions (new File(seleniumDir + "/capchaRes.crx"));

        DesiredCapabilities capabilities = new DesiredCapabilities ();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return new ChromeDriver(options);
    }

    private ChromeOptions setProxyChromeOption(ChromeOptions chromeOptions)
    {
        if (proxyService.isProxyEnable())
        {
            Proxy proxy= new Proxy();
            String proxyValue = String.format("%s:%s",
                    proxyService.getProxyIpValue(),
                    proxyService.getProxyPort()
            );
            log.info("Set proxy value={}", proxyValue);
            proxy.setSslProxy(proxyValue);
            chromeOptions.setCapability("proxy", proxy);
        }
        return  chromeOptions ;
    }
}
