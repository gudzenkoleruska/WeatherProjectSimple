import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ValeriiaHudzenkoTests {

    //    Тест кейс example:
    //    //1. Открыть страницу https://openweathermap.org/
    //    //2. Набрать в строке поиска город Paris
    //    //3. Нажать пункт меню Search
    //    //4. Из выпадающего списка выбрать Paris, FR
    //    //5. Подтвердить, что заголовок изменился на "Paris, FR"

    @Test
    public void testH2Tag_WhenSearchingCityCountry() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String cityName = "Paris";
        String expectedResult = "Paris, FR";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);                   //5 sec

        WebElement searchCityField = driver.findElement(
                By.xpath("//div[@id = 'weather-widget']//input[@placeholder = 'Search city']")
        );
        searchCityField.click();
        searchCityField.sendKeys(cityName);

        WebElement searchButton = driver.findElement(
                By.xpath("//div[@id = 'weather-widget']//button[@type = 'submit']")
        );
        searchButton.click();

        Thread.sleep(2000);                 //для отображения drop-down list 2 sec
        WebElement parisFRChoiceInDropdownMenu = driver.findElement(
                By.xpath("//ul[@class = 'search-dropdown-menu']/li/span[text() = 'Paris, FR ']")
        );
        parisFRChoiceInDropdownMenu.click();

        WebElement h2CityCountryHeader = driver.findElement(
                By.xpath("//div[@id = 'weather-widget']//h2")
        );
        Thread.sleep(2000);
        String actualResult = h2CityCountryHeader.getText();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();                         //closing every associated window

    }

//    TC_11_01
//1.  Открыть базовую ссылку
//2.  Нажать на пункт меню Guide
//3.  Подтвердить, что вы перешли на страницу со ссылкой https://openweathermap.org/guide и что title этой
//// страницы OpenWeatherMap API guide - OpenWeatherMap
    //title на стр не отображается...

    @Test
    public void testH1TagOnGuidePage() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String expectedResult1 = "https://openweathermap.org/guide";
        String expectedResult2 = "OpenWeatherMap API guide - OpenWeatherMap";

        driver.get(url);
        Thread.sleep(5000);

        WebElement guideLink = driver.findElement(
                By.xpath("//div[@id = 'desktop-menu']//li/a")
        );

        guideLink.click();
        Thread.sleep(2000);

        String actualResult1 = driver.getCurrentUrl();
        String actualResult2 = driver.getTitle();

        Assert.assertEquals(actualResult1, expectedResult1);
        Assert.assertEquals(actualResult2, expectedResult2);

        driver.quit();

    }

//    TC_11_02
//1.  Открыть базовую ссылку
//2.  Нажать на единицы измерения Imperial: °F, mph
//3.  Подтвердить, что температура для города показана в Фарингейтах

    @Test
    public void testTemperatureInCity() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String expectedResult = "°F";
        String fSymbol = "°F";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement switchTemperature = driver.findElement(
                By.xpath("//div[@class = 'switch-container']//div[contains(text(), 'Imperial: °F, mph')]")
        );
        switchTemperature.click();
        Thread.sleep(2000);

        WebElement tempF = driver.findElement(
                By.xpath("//div[@class = 'current-temp']/span"));

        String tempInF = tempF.getText();
        String actualResult = tempInF.substring((tempInF.length() - 2));     //обрезает строку на 2 элемента

        Assert.assertEquals(actualResult, expectedResult);
        Assert.assertTrue(tempF.getText().contains(fSymbol));

        driver.quit();
    }

//    TC_11_03
//1.  Открыть базовую ссылку
//2. Подтвердить, что внизу страницы есть панель с текстом “We use cookies which are essential for the site to work.
//    We also use non-essential cookies to help us improve our services. Any data collected is anonymised.
//    You can allow all cookies or manage them individually.”
//3. Подтвердить, что на панели внизу страницы есть 2 кнопки “Allow all” и “Manage cookies”

    @Test
    public void testPanelButtons() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String expectedResult = "We use cookies which are essential for the site to work. We also use non-essential " +
                "cookies to help us improve our services. Any data collected is anonymised. " +
                "You can allow all cookies or manage them individually.";
        String expectedResult1 = "Allow all";
        String expectedResult2 = "Manage cookies";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement textInPanel = driver.findElement(
                By.className("stick-footer-panel__description")
        );

        WebElement buttonAllowAll = driver.findElement(
                By.xpath("//div[@class = 'stick-footer-panel__btn-container']/button[(text() = 'Allow all')]")
        );

        WebElement buttonManageCookies = driver.findElement(
                By.xpath("//a[@href = '/cookies-settings']")
        );

        Assert.assertTrue(driver.findElement(By.className("stick-footer-panel__container")).isDisplayed());         //пороверяет есть ли такой элемент на стр

        Assert.assertEquals(driver.findElements(
                By.xpath("//div[@class = 'stick-footer-panel__btn-container']/*")).size(), 2);    //ожидаемое кол-во кнопок 2

        Assert.assertTrue(driver.findElement(
                By.xpath("//a[@href = '/cookies-settings']")).isDisplayed());
        Assert.assertEquals(buttonAllowAll.getText(), expectedResult1);

        Assert.assertTrue(driver.findElement(
                By.xpath("//div[@class = 'stick-footer-panel__btn-container']" +
                        "/button[(text() = 'Allow all')]")).isDisplayed());
        Assert.assertEquals(buttonManageCookies.getText(), expectedResult2);

        Assert.assertEquals(textInPanel.getText(), expectedResult);

        driver.quit();

    }

//    TC_11_04
//1.  Открыть базовую ссылку
//2.  Подтвердить, что в меню Support есть 3 подменю с названиями “FAQ”, “How to start” и “Ask a question”

    @Test
    public void testMenuAndText() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";

        String menu1 = "FAQ";
        String menu2 = "How to start";
        String menu3 = "Ask a question";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement supportDropdown = driver.findElement(
                By.xpath("//div[@id = 'support-dropdown']")
        );
        supportDropdown.click();

        WebElement FAG_button = driver.findElement(
                By.xpath("//ul[@id = 'support-dropdown-menu']/li/a [@href = '/faq']")
        );
        WebElement HowToStart_button = driver.findElement(
                By.xpath("//ul[@id = 'support-dropdown-menu']/li/a [@href = '/appid']")
        );
        WebElement AskQuestion_button = driver.findElement(
                By.xpath("//ul[@id = 'support-dropdown-menu']/li/a [text() = 'Ask a question']")
        );

        Assert.assertTrue(driver.findElement(
                By.xpath("//ul[@id = 'support-dropdown-menu']/li/a [@href = '/faq']")
        ).isDisplayed());

        Assert.assertTrue(driver.findElement(
                By.xpath("//ul[@id = 'support-dropdown-menu']/li/a [@href = '/appid']")
        ).isDisplayed());

        Assert.assertTrue(driver.findElement(
                By.xpath("//ul[@id = 'support-dropdown-menu']/li/a [text() = 'Ask a question']")
        ).isDisplayed());

        Assert.assertEquals(driver.findElements(
                By.xpath("//ul[@id = 'support-dropdown-menu']/li")).size(), 3);           //проверка элементов в меню

        Assert.assertEquals(FAG_button.getText(), menu1);
        Assert.assertEquals(HowToStart_button.getText(), menu2);
        Assert.assertEquals(AskQuestion_button.getText(), menu3);


        driver.quit();
    }

//    TC_11_05
//1. Открыть базовую ссылку
//2. Нажать пункт меню Support → Ask a question
//3. Заполнить поля Email, Subject, Message
//4. Не подтвердив CAPTCHA, нажать кнопку Submit
//5. Подтвердить, что пользователю будет показана ошибка “reCAPTCHA verification failed, please try again.”

    @Test
    public void testErrorMessageReCaptcha() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String email = "tester@test.com";
        String message = "Please help me";
        String expectedResult = "reCAPTCHA verification failed, please try again.";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement selectSupportMenu = driver.findElement(
                By.xpath("//div[@id = 'support-dropdown']")
        );
        selectSupportMenu.click();

        WebElement askQuestionOption = driver.findElement(By.linkText("Ask a question"));
        //или XPath //ul[@id = 'support-dropdown-menu']/li/a[contains(text(), 'Ask a question')]

        askQuestionOption.click();
        Thread.sleep(2000);

        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));


        WebElement emailField = driver.findElement(By.id("question_form_email"));
        emailField.click();
        emailField.sendKeys(email);

        WebElement searchTextSubjectField = driver.findElement(
                By.xpath("//select[@class = 'form-control select required']"));
        searchTextSubjectField.click();

        WebElement selectTextSubjectField = driver.findElement(
                By.xpath("//select[@class = 'form-control select required']//option[@value = 'Other']"));
        selectTextSubjectField.click();

        WebElement messageField = driver.findElement(By.id("question_form_message"));
        messageField.click();
        messageField.sendKeys(message);

        WebElement submitButton = driver.findElement(
                By.xpath("//input[@data-disable-with = 'Create Question form']"));
        submitButton.click();

        Thread.sleep(2000);

        WebElement captchaText = driver.findElement(
                By.xpath("//div[@class = 'help-block']"));

        Assert.assertEquals(captchaText.getText(), expectedResult);

        driver.quit();
    }

//    TC_11_06
//1.  Открыть базовую ссылку
//2.  Нажать пункт меню Support → Ask a question
//3.  Оставить значение по умолчанию в checkbox Are you an OpenWeather user?
//4. Оставить пустым поле Email
//5. Заполнить поля  Subject, Message
//6. Подтвердить CAPTCHA
//7. Нажать кнопку Submit
//8. Подтвердить, что в поле Email пользователю будет показана ошибка “can't be blank”

    @Test
    public void testErrorCantBeBlank() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";

        String message = "Please help me";
        String expectedResult = "can't be blank";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement selectSupportMenu = driver.findElement(
                By.xpath("//div[@id = 'support-dropdown']")
        );
        selectSupportMenu.click();

        WebElement askQuestionOption = driver.findElement(By.linkText("Ask a question"));

        askQuestionOption.click();
        Thread.sleep(2000);

        String mainWindow = driver.getWindowHandle();
        for (String windowsHandle : driver.getWindowHandles()) {               //WindowHandle - основное окно, getWindowHandles - второстепенные окна
            if (!mainWindow.contentEquals(windowsHandle)) {
                driver.switchTo().window(windowsHandle);
                break;
            }
        }

        WebElement searchSubjectField = driver.findElement(
                By.xpath("//select[@class = 'form-control select required']"));
        searchSubjectField.click();
        Thread.sleep(2000);

        WebElement searchTextSubjectField = driver.findElement(
                By.xpath("//select[@class = 'form-control select required']"));
        searchTextSubjectField.click();

        WebElement selectTextSubjectField = driver.findElement(
                By.xpath("//select[@class = 'form-control select required']//option[@value = 'Other']"));
        selectTextSubjectField.click();

        WebElement messageField = driver.findElement(By.id("question_form_message"));
        messageField.click();
        messageField.sendKeys(message);

        String window2 = driver.getWindowHandle();
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title = 'reCAPTCHA']")));

        WebElement enterCaptcha = driver.findElement(
                By.xpath("//span[@class = 'recaptcha-checkbox goog-inline-block recaptcha-checkbox-unchecked" +
                        " rc-anchor-checkbox']"));

        enterCaptcha.click();
        Thread.sleep(5000);

        driver.switchTo().window(window2);

        WebElement submitButton = driver.findElement(
                By.xpath("//input[@data-disable-with = 'Create Question form']"));
        submitButton.click();

        Thread.sleep(2000);

        WebElement confirmErrorEmail = driver.findElement(
                By.xpath("//span[@class = 'help-block']"));

        Assert.assertEquals(confirmErrorEmail.getText(), expectedResult);

        driver.quit();
    }

//    TC_11_07
//1.  Открыть базовую ссылку
//2.  Нажать на единицы измерения Imperial: °F, mph
//3.  Нажать на единицы измерения Metric: °C, m/s
//4.  Подтвердить, что в результате этих действий, единицы измерения температуры изменились с F на С

    @Test
    public void testchangedTemperature() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";

        String temperatureValue = "°C";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement switchTemperatureToF = driver.findElement(
                By.xpath("//div[@class = 'switch-container']//div[contains(text(), 'Imperial: °F, mph')]")
        );
        switchTemperatureToF.click();
        Thread.sleep(2000);

        WebElement switchTemperatureToC = driver.findElement(
                By.xpath("//div[@class = 'switch-container']//div[contains(text(), 'Metric: °C, m/s')]")
        );
        switchTemperatureToC.click();
        Thread.sleep(2000);

        String temperatureC = driver.findElement(By.xpath("//span[@class = 'heading'][contains (text(), '°C')]"))
                .getText();


        Boolean actualResult = temperatureC.contains(temperatureValue);

        Assert.assertTrue(actualResult);

        driver.quit();
    }

//    TC_11_08
//1.  Открыть базовую ссылку
//2.  Нажать на лого компании
//3.  Дождаться, когда произойдет перезагрузка сайта, и подтвердить, что текущая ссылка не изменилась

    @Test
    public void testcurrentLink() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        String url = "https://openweathermap.org/";
        String expectedUrl = "https://openweathermap.org/";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement imageBanner = driver.findElement(
                By.xpath("//li[@class = 'logo']/a" +
                        "/img[@src = '/themes/openweathermap/assets/img/logo_white_cropped.png']"));
        imageBanner.click();

        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class = 'own-loader-container']/div")));

        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        String actualResult = driver.getCurrentUrl();

        Assert.assertEquals(actualResult, expectedUrl);

        driver.quit();
    }

//    TC_11_09
//1.  Открыть базовую ссылку
//2.  В строке поиска в навигационной панели набрать “Rome”
//3.  Нажать клавишу Enter
//4.  Подтвердить, что вы перешли на страницу в ссылке которой содержатся слова “find” и “Rome”
//5. Подтвердить, что в строке поиска на новой странице вписано слово “Rome”

    @Test
    public void testFindRomeInLink() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String expectedResultCity = "Rome";
        String searchValue1 = "find";
        String searchValue2 = "Rome";

        String url = "https://openweathermap.org/";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement searchField = driver.findElement(
                By.xpath("//div[@id = 'desktop-menu']//input[@type= 'text']"));
        searchField.click();
        searchField.sendKeys(expectedResultCity);
        searchField.sendKeys(Keys.ENTER);

        Thread.sleep(2000);

        String strUrl = driver.getCurrentUrl();

        Boolean actualResult = (strUrl.contains(searchValue1) && strUrl.contains(searchValue2));

        Assert.assertTrue(actualResult);

        String actualResultSearchBar = driver.findElement(By.xpath("//input[@class]"))
                .getAttribute("value");

        Assert.assertEquals(actualResultSearchBar, expectedResultCity);

        driver.quit();
    }
//    TC_11_10
//1.  Открыть базовую ссылку
//2.  Нажать на пункт меню API
//3.  Подтвердить, что на открывшейся странице пользователь видит 30 оранжевых кнопок

    @Test
    public void testCheck30OrangeButtons() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        int expectedResultButtons = 30;

        String url = "https://openweathermap.org/";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement menuAPI = driver.findElement(
                By.xpath("//ul//a[@href ='/api']"));
        menuAPI.click();

        int actualResultOrangeButtons = driver.findElements(By.xpath("//a[contains(@class, 'orange')]"))
                .size();

        Assert.assertEquals(actualResultOrangeButtons, expectedResultButtons);

        driver.quit();
    }
}