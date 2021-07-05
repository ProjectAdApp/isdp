package com.projectad.isdp.dao;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projectad.isdp.service.IsdpService;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Repository;

@Repository("actionCode")
public class WrapDao implements IsdpSuperDao {

    @Override
    public String callId() {
        WebDriver driver = null;
        try {

            // code is going here

            // void sign in with selenium

            // then getting the own session id

            // updating the session id to firebase via admin

            System.setProperty("webdriver.chrome.driver",  System.getenv("CHROMEDRIVER_PATH"));
                 // "/Users/valentinahrend/Downloads/chromedriver");
            ChromeOptions options = new ChromeOptions();

            // options.addArguments("start-maximized");
            options.setBinary(System.getenv("GOOGLE_CHROME_BIN"));
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");
            options.addArguments("--start-maximized");
            options.addArguments("--window-size=1920,1080");
            options.addArguments(
    "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.24 Safari/537.36");

            // options.addArguments("--disable-dev-shm-usage");
            // options.addArguments("--no-sandbox");
            // options.addArguments("user-agent=I LIKE CHOCOLATE");
            driver = new ChromeDriver(options);
            // DesiredCapabilities caps = DesiredCapabilities.htmlUnit();
            // caps.setCapability(ChromeOptions.CAPABILITY, options);

         

            driver.manage().window().maximize();        

            

            driver.get("https://www.instagram.com/accounts/login");

            driver.manage().addCookie(new Cookie.Builder("sessionid", "id").build());
            

           //driver.manage().addCookie(new Cookie("sessionid", "id"));

            
            final String username =  System.getenv("IN_USERNAME");
            final String password =  System.getenv("IN_PASSWORD");
                
                    
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@name='username']"))).get(0);
            
           // WebElement element = driver.findElement(By.xpath("//input[@name='username']"));

            element.sendKeys(username.substring(0, 1));

            element.sendKeys(username.substring(1));

            WebElement element2 = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//input[@name='password']"))).get(0);

            element2.sendKeys(password.substring(0, 1));

            element2.sendKeys(password.substring(1));

            element2.sendKeys(Keys.TAB);
            element2.sendKeys(Keys.ENTER);

            System.out.println("sleep");
              
            int args = 0;
            while(args < 40){
                System.out.println(driver.manage().getCookies().size());
                Thread.sleep(100);
                args++;
            }

            System.out.println(driver.manage().getCookieNamed("sessionid").getValue());
            
            
            

            //driver.manage().addCookie(new Cookie.Builder("sessionid", "id").build());

         //   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
            // receive token data

            // Set<Cookie> cookies = driver.manage().getCookies();


            /*
             * if (cookies.size() == 0) { // To support FF and IE String cookiesString =
             * (String) driver.executeScript("return document.cookie"); cookies =
             * parseBrowserCookies(cookiesString); }
             */

       //      Thread.sleep(5000);

             

           // WebElement element3 =  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='vgS-T']")));

            System.out.println("load cookies");
            System.out.println(driver.manage().getCookies().size());
            Cookie c = driver.manage().getCookieNamed("sessionid");

            System.out.println(c.getValue());

            // now getting the firebase admin

            driver.quit();

            //System.out.println(System.getenv("FIRE_KEY_PR"));

            FirebaseOptions option;
            try {
                option = new FirebaseOptions.Builder()
                        //private
                    
                        build();

                FirebaseApp app = FirebaseApp.initializeApp(option,UUID.randomUUID().toString());

                FirebaseDatabase database = FirebaseDatabase.getInstance(app);

                DatabaseReference ref = database.getReference().child("sessionId");

                ref.setValueAsync(c.getValue());

                IsdpService.inProgress = false;

                return "success";

            } catch (IOException e) {
                e.printStackTrace();
            }

            IsdpService.inProgress = false;

            return "fail";

        } catch (Exception e) {
            e.printStackTrace();

            if(driver.manage().getCookieNamed("sessionid")!=null){
                FirebaseOptions option;
            try {
                option = new FirebaseOptions.Builder()
                        //private
                    
                        build();

                FirebaseApp app = FirebaseApp.initializeApp(option,UUID.randomUUID().toString());

                FirebaseDatabase database = FirebaseDatabase.getInstance(app);

                DatabaseReference ref = database.getReference().child("sessionId");

                ref.setValueAsync(driver.manage().getCookieNamed("sessionid").getValue());

                IsdpService.inProgress = false;

                return "success";

            } catch (IOException es) {
                es.printStackTrace();
            }
            }

            IsdpService.inProgress = false;
            if (driver != null){
                driver.quit();
            }
            return "odd";
        }
    }

}
