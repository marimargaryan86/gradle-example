package com.kineto.tests

import spock.lang.Specification
import spock.lang.Shared
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import io.github.bonigarcia.wdm.WebDriverManager

/**
 * Base specification class for Kineto.app tests
 * Contains shared configuration and utility methods
 */
abstract class BaseSpec extends Specification {

    @Shared
    static String BASE_URL = System.getProperty('kineto.base.url', 'https://kineto.app')

    @Shared
    static String TEST_EMAIL = System.getProperty('kineto.test.email', '')

    @Shared
    static String TEST_PASSWORD = System.getProperty('kineto.test.password', '')

    @Shared
    WebDriver driver

    /**
     * Initialize WebDriver before all tests
     */
    def setupSpec() {
        WebDriverManager.chromedriver().setup()

        ChromeOptions options = new ChromeOptions()
        options.addArguments('--headless')  // Run headless for CI
        options.addArguments('--no-sandbox')
        options.addArguments('--disable-dev-shm-usage')
        options.addArguments('--window-size=1920,1080')

        driver = new ChromeDriver(options)
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10))
    }

    /**
     * Cleanup WebDriver after all tests
     */
    def cleanupSpec() {
        if (driver != null) {
            driver.quit()
        }
    }

    /**
     * Navigate to the base URL
     */
    void navigateToApp() {
        driver.get(BASE_URL)
    }

    /**
     * Navigate to a specific path
     */
    void navigateTo(String path) {
        driver.get(BASE_URL + path)
    }

    /**
     * Check if user is logged in
     */
    boolean isLoggedIn() {
        // Implementation depends on Kineto.app's UI
        // This is a placeholder - adjust based on actual app behavior
        try {
            return !driver.currentUrl.contains('/login')
        } catch (Exception e) {
            return false
        }
    }

    /**
     * Wait for specified milliseconds
     */
    void waitFor(long milliseconds) {
        Thread.sleep(milliseconds)
    }

    /**
     * Log test information
     */
    void logInfo(String message) {
        println "[INFO] ${new Date().format('HH:mm:ss')} - ${message}"
    }

    /**
     * Log test step
     */
    void logStep(String step) {
        println "[STEP] ${step}"
    }
}
