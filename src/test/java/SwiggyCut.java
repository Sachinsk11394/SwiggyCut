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
        /**
         * Run this on terminal
         * Google\ Chrome --remote-debugging-port=9222 --user-data-dir="~//Users/user_name/Library/Application Support/Google/Chrome"
         */
        options.addArguments("user-data-dir=/Users/sachin.sampathkumar/Library/Application Support/Google/Chrome/");
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        WebDriver driver = new ChromeDriver(options);

        // Load swiggy orders page directly. We need to have prior chrome setup to run on a pre-defined profile
        driver.get("https://www.swiggy.com/my-account/orders");

        //Todo: Use a better way to make browser wait for feedback element to load
        Thread.sleep(2000);

        //Remove feedback popup
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return document.getElementById('nps-nudge').remove();");

        // More order element. Class name might change with time
        WebElement more = driver.findElement(By.className("_2pWZz"));

        int numberOfOrders = -1;
        int lastCountOfOrders = 0;
        while (numberOfOrders != lastCountOfOrders) {
            numberOfOrders = lastCountOfOrders;
            System.out.println("Order number " + numberOfOrders + " and counting...");
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", more);
            Thread.sleep(400);
            lastCountOfOrders = driver.findElements(By.className("_3Hghg")).size();
        }

        // Calculate total cost
        int totalCost = 0;
        List<WebElement> costs = driver.findElements(By.className("_3Hghg"));
        for (WebElement cost : costs) {
            totalCost = totalCost + Integer.parseInt(cost.getText());
        }

        // Display first order on swiggy
        List<WebElement> orderTimes = driver.findElements(By.className("_2uT6l"));
        String firstOrderTime = orderTimes.get(orderTimes.size() - 1).getText().split("\\|")[1];

        System.out.println("Total number of orders on Swiggy : " + numberOfOrders);
        System.out.println("Total money spent on Swiggy : " + totalCost);
        System.out.println("First order on Swiggy :" + firstOrderTime);
    }

}
