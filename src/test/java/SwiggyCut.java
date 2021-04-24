import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.Logs;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SwiggyCut {

    ChromeOptions options = new ChromeOptions();
    String chromeProfilePath = "/private/var/folders/cp/vv3crv0j67g33tpt_tz5zqn00000gp/T/.com.google.Chrome.u4vlj6/Default";

    @Test
    public void SwiggyCutTest() throws InterruptedException {
        options.addArguments("user-data-dir=/Users/sachin.sampathkumar/Library/Application Support/Google/Chrome/");
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        WebDriver driver = new ChromeDriver(options);
        Actions actions = new Actions(driver);
        driver.get("https://www.swiggy.com/my-account/orders");
        Thread.sleep(2000);

        JavascriptExecutor js = (JavascriptExecutor) driver;;
        js.executeScript("return document.getElementById('nps-nudge').remove();");

        WebElement more = driver.findElement (By.className ("_2pWZz"));
        Integer numberOfOrders = 0;

        while (numberOfOrders != driver.findElements(By.className("_3Hghg")).size()) {
            numberOfOrders = driver.findElements(By.className("_3Hghg")).size();
            System.out.println("Order number " + numberOfOrders + " and counting...");
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", more);
            Thread.sleep(400);
        }

        List<WebElement> costs = driver.findElements(By.className("_3Hghg"));
        Integer totalCost = 0;
        for (WebElement cost : costs) {
            totalCost = totalCost + Integer.parseInt(cost.getText());
        }

        List<WebElement> orderTimes = driver.findElements(By.className("_2uT6l"));
        String firstOrderTime = orderTimes.get(orderTimes.size() - 1).getText().split("\\|")[1];

        System.out.println("Total number of orders on Swiggy : " + numberOfOrders);
        System.out.println("Total money spent on Swiggy : " + totalCost);
        System.out.println("First order on Swiggy :" + firstOrderTime);
    }

}
