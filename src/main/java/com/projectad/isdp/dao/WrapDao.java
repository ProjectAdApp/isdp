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
            boolean containsCookie = false;
            while(args < 40){
                if(driver.manage().getCookies().stream().anyMatch(cookie -> cookie.getName().toLowerCase().equals("sessionid"))) {
                    containsCookie = true;
                    break;
                }
                Thread.sleep(100);
                args++;
            }

            if(!containsCookie){

                /// cockie was not found!!!

                return "fail";

            }

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

            Cookie c = driver.manage().getCookieNamed("sessionid");

            System.out.println(c.getValue());

            // now getting the firebase admin

            driver.quit();

            //System.out.println(System.getenv("FIRE_KEY_PR"));

            FirebaseOptions option;
            try {
                option = new FirebaseOptions.Builder()
                        .setCredentials(ServiceAccountCredentials.fromPkcs8(
                            /*System.getenv("FIRE_CLI")*/"103929167872765460709",
                                /*System.getenv("FIRE_CLEM")*/"firebase-adminsdk-yya78@project-ad-22e63.iam.gserviceaccount.com", 
                                /*System.getenv("FIRE_KEY_PR")*/"-----BEGIN PRIVATE KEY-----\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC1UWlwPKjGE5vJ\nuyE4MCfgNtHU66jqteBfHkBXGdBlsCb0M+Ub6sDL5mILyB08UfX26aFa4QHKCgRE\nMMC6wEo9CYjHnDZG0F2O1//7UBMLgy08dWEU0Fao4kwccOhS5oPtxunQF7aR68f0\neCK6Gd5MDIUXzKmK1q3OkszNNEXV64yqqzUYsWPFxPFyVIgzwkjQ1QQMweIQSgCu\nxDhkJAx7564yTYGtn7E0IaIeNW1oI31TlNQp094vH9dVMnzi1unQKl3Z+Vn9DNM+\nPhK0e/RB0D+1l9ceHvRkvnuL0SB/yYv7qdAZaW55EoeUQyjZ2y5w4fEOtYqxn2y5\nUc1SoZHBAgMBAAECggEAQlpDmpYoMSxV03IOixBcV1nYWfHPg+0A6e4ZH4bL05qt\n9qnTCu9SlIt/j84wnLVlrHD84lP7IqOdwhKRRQY/ZKzLiDirSCjSRAbZn29DOPBN\nKpXN7B79x5ngvnu3GTdQSkCDqeoznpzJNLaOBsqWpf/i6L21waAkRLdGCUY61Dhc\nMUPHq0D7R04EJJ/zTQSHr791OXUuWpkVneBpRR5kBz+tEzAetILZuun1JFWGcn9H\n6rCf6WlD2iJhdHgBL6FQRPZ3WoCw3QcIh6wSxyTslGiUj/Uz6+FBJbIrL23qON6s\nij7zzqBp8Sqr0M1eX9hIgbVdnQ4KHrAxI/stInZ+bQKBgQDbJD9DLvsTDqELftL3\n9+GpPV/MWpb8z4juW210P2gLRmKZ4vI8pOn/RmAE3J9MJKsshbF45msXPAf6wIMI\nc7BFuT2F13DySqM36yGEwy5VI/AO79Cf6AZ0KlM49ZQk/enkBx5NR4AEOJjAoWHB\nFuRqGAaNAqP7L+tP0e09NXjEMwKBgQDT0JCs+nl0sfXH4r8fWrIbtCFRhJAcTWNG\nXOGi5fZ/d7l8gKigfqdug0jClLMUrau34rUvHUS0xGFbq3WSebEWNydK4CaSZZdZ\nvBNRAwPSqLP6aeWd/hjJ7yxIjtx7AKnKH9LeHwJluEQYvYSfgAeNxRWYDChemanz\n7zE6tpg+OwKBgQDDsenguCq0yQKd/mKt2Con9UwAdQGSwE569SCYiCsErpVM8TL7\nXxYrxg/nJ5IF8FUATF/BJJuLwyspDRO2z4EhspuzU0JkzKxXOl3Tbm2I0LAG1rgK\npKxmvolVO1FRe4KMBKNVxb0Xh+c5VqSyRxwxdkUbVblUjmVoAYMHfyHaqwKBgQCT\nsgDPvS/noReis4dVU6aSDr6U2vyp34Rz0dw7/GIva8pY+FWc4/PX6Y9YrttYdekh\nJeH04lHAQpcolpw68MP8m6S4XyhGOLSai7xkZtfJkn09BY5o5n9ef3m/dm5jDolW\nOW6OF81SJHTHU48gsOOThXOTPBcCL+AECiunpx7tdQKBgQCzKD487K3LGWMaSJj8\nes5jm2OBijPqaxS+Dfbc9g3PwxstJP+vuL3DcOz1qMz0g1wwChEdXmOmHwWvgoFz\nIG4qEiZYiVWWb+U3HhUsDC2C52IipUoC71JHgDzpxa+/difsNFLzmNXMh24Eb8el\nREBr8GO39ZN6etWRW4Sc9SHt1A==\n-----END PRIVATE KEY-----\n",
                                /*System.getenv("FIRE_KEY_PR_ID")*/"be678b59edeba680341f0144502761745d705bf9", null))
                        .setDatabaseUrl("https://project-ad-22e63.firebaseio.com/").
                    
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
                        .setCredentials(ServiceAccountCredentials.fromPkcs8(
                            /*System.getenv("FIRE_CLI")*/"103929167872765460709",
                                /*System.getenv("FIRE_CLEM")*/"firebase-adminsdk-yya78@project-ad-22e63.iam.gserviceaccount.com", 
                                /*System.getenv("FIRE_KEY_PR")*/"-----BEGIN PRIVATE KEY-----\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC1UWlwPKjGE5vJ\nuyE4MCfgNtHU66jqteBfHkBXGdBlsCb0M+Ub6sDL5mILyB08UfX26aFa4QHKCgRE\nMMC6wEo9CYjHnDZG0F2O1//7UBMLgy08dWEU0Fao4kwccOhS5oPtxunQF7aR68f0\neCK6Gd5MDIUXzKmK1q3OkszNNEXV64yqqzUYsWPFxPFyVIgzwkjQ1QQMweIQSgCu\nxDhkJAx7564yTYGtn7E0IaIeNW1oI31TlNQp094vH9dVMnzi1unQKl3Z+Vn9DNM+\nPhK0e/RB0D+1l9ceHvRkvnuL0SB/yYv7qdAZaW55EoeUQyjZ2y5w4fEOtYqxn2y5\nUc1SoZHBAgMBAAECggEAQlpDmpYoMSxV03IOixBcV1nYWfHPg+0A6e4ZH4bL05qt\n9qnTCu9SlIt/j84wnLVlrHD84lP7IqOdwhKRRQY/ZKzLiDirSCjSRAbZn29DOPBN\nKpXN7B79x5ngvnu3GTdQSkCDqeoznpzJNLaOBsqWpf/i6L21waAkRLdGCUY61Dhc\nMUPHq0D7R04EJJ/zTQSHr791OXUuWpkVneBpRR5kBz+tEzAetILZuun1JFWGcn9H\n6rCf6WlD2iJhdHgBL6FQRPZ3WoCw3QcIh6wSxyTslGiUj/Uz6+FBJbIrL23qON6s\nij7zzqBp8Sqr0M1eX9hIgbVdnQ4KHrAxI/stInZ+bQKBgQDbJD9DLvsTDqELftL3\n9+GpPV/MWpb8z4juW210P2gLRmKZ4vI8pOn/RmAE3J9MJKsshbF45msXPAf6wIMI\nc7BFuT2F13DySqM36yGEwy5VI/AO79Cf6AZ0KlM49ZQk/enkBx5NR4AEOJjAoWHB\nFuRqGAaNAqP7L+tP0e09NXjEMwKBgQDT0JCs+nl0sfXH4r8fWrIbtCFRhJAcTWNG\nXOGi5fZ/d7l8gKigfqdug0jClLMUrau34rUvHUS0xGFbq3WSebEWNydK4CaSZZdZ\nvBNRAwPSqLP6aeWd/hjJ7yxIjtx7AKnKH9LeHwJluEQYvYSfgAeNxRWYDChemanz\n7zE6tpg+OwKBgQDDsenguCq0yQKd/mKt2Con9UwAdQGSwE569SCYiCsErpVM8TL7\nXxYrxg/nJ5IF8FUATF/BJJuLwyspDRO2z4EhspuzU0JkzKxXOl3Tbm2I0LAG1rgK\npKxmvolVO1FRe4KMBKNVxb0Xh+c5VqSyRxwxdkUbVblUjmVoAYMHfyHaqwKBgQCT\nsgDPvS/noReis4dVU6aSDr6U2vyp34Rz0dw7/GIva8pY+FWc4/PX6Y9YrttYdekh\nJeH04lHAQpcolpw68MP8m6S4XyhGOLSai7xkZtfJkn09BY5o5n9ef3m/dm5jDolW\nOW6OF81SJHTHU48gsOOThXOTPBcCL+AECiunpx7tdQKBgQCzKD487K3LGWMaSJj8\nes5jm2OBijPqaxS+Dfbc9g3PwxstJP+vuL3DcOz1qMz0g1wwChEdXmOmHwWvgoFz\nIG4qEiZYiVWWb+U3HhUsDC2C52IipUoC71JHgDzpxa+/difsNFLzmNXMh24Eb8el\nREBr8GO39ZN6etWRW4Sc9SHt1A==\n-----END PRIVATE KEY-----\n",
                                /*System.getenv("FIRE_KEY_PR_ID")*/"be678b59edeba680341f0144502761745d705bf9", null))
                        .setDatabaseUrl("https://project-ad-22e63.firebaseio.com/").
                    
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
