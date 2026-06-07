package com.mycompany.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        
        try {
            System.out.print("11111111111");
            webDriver.get("https://www.calculator.net/password-generator.html");
            System.out.print("22222222222");
            System.out.print("Это моя работа");
            System.out.print("Она не списана. Делаю уже 3й раз");
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }

        Task2.task2();
        Task3.task3();

        webDriver.quit();
    }
}