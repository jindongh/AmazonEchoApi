package amazonechoapi;

import io.github.bonigarcia.wdm.ChromeDriverManager;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author jindongh
 */
public class AmazonEchoApi {

	private final String BASE_URL;
	private final String USERNAME;
	private final String PASSWORD;
	private final WebDriver driver;

	static {
		ChromeDriverManager.getInstance().setup();
	}

	public AmazonEchoApi(String base_url, String username, String password) {
		this.BASE_URL = base_url;
		this.USERNAME = username;
		this.PASSWORD = password;
		driver = new ChromeDriver();
	}
	public void quit() {
		driver.close();
	}

	public boolean httpLogin() {
		try {
			driver.get(BASE_URL);
			WebElement email = waitForElement(driver, By.name("email"));
			WebElement password = driver.findElement(By.name("password"));
			email.sendKeys(USERNAME);;
			password.sendKeys(PASSWORD);
			password.submit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	static WebElement waitForElement(WebDriver driver, By by) {
		for (int i = 0; i < 30; i++) {
			try {
				return driver.findElement(by);
			} catch (NoSuchElementException ne) {
			}
			try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(1));
			} catch (InterruptedException ie) {}
		}
		throw new RuntimeException("Can't find element " + by.toString());
	}
	public void playMusic(String name) throws Exception {
		waitForElement(driver, By.id("iMusicAndBooks")).click();
		waitForElement(driver, By.cssSelector("li.dee-music-image-text-item-list")).click();
		Thread.sleep(TimeUnit.SECONDS.toMillis(2));
		waitForElement(driver, By.id("dee-music-tracks-tab")).click();
		WebElement song = null;
		for (WebElement element : driver.findElements(By.className("dee-music-text"))) {
			if (song == null) {
				song = element;
			}
			if (element.getText().contains(name)) {
				song = element;
				break;
			}
		}
		song.click();
	}


	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage: main <username> <password>");
			System.exit(1);
		}
		String username = args[0];
		String password = args[1];
		AmazonEchoApi amazonEchoApi = new AmazonEchoApi(
				"https://pitangui.amazon.com", username, password);
		if (!amazonEchoApi.httpLogin()) {
			System.out.println("Failed to login as "+username+":"+password);
			System.exit(1);
		}
		amazonEchoApi.playMusic("金刚经");
		amazonEchoApi.quit();
	}

}
