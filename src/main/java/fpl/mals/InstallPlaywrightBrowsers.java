package fpl.mals;

import com.microsoft.playwright.*;

/**
 * Простий утилітарний клас для одноразової інсталяції браузерів Playwright.
 *
 * Виконує завантаження Chromium, Firefox і WebKit
 * у системну теку Playwright (зазвичай: %USERPROFILE%\AppData\Local\ms-playwright).
 *
 * Можна викликати з BAT-скрипта або вручну:
 *   java -cp target\* fpl.mals.InstallPlaywrightBrowsers
 */
public class InstallPlaywrightBrowsers {
    public static void main(String[] args) {
        System.out.println("🌐 Starting Playwright browser installation...");

        try (Playwright playwright = Playwright.create()) {
            // Завантажує Chromium — цього достатньо, щоб ініціалізувати всі потрібні компоненти
            playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            System.out.println("✅ Chromium browser installed successfully!");

            // Якщо потрібно, можна розкоментувати ці рядки для інших браузерів:
            // playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
            // playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(true));

            System.out.println("✅ Playwright setup complete!");
        } catch (Exception e) {
            System.err.println("❌ Failed to install browsers: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
