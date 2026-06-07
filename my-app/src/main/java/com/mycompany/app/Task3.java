package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3
{
    public static void task3()
    {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        try {
            // как в README
            webDriver.get("https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms");
            System.out.println(webDriver.getPageSource());
            WebElement tsyplakov_elem = webDriver.findElement(By.tagName("pre"));

            // как в README
            String tsyplakov_json_str = tsyplakov_elem.getText();
            JSONParser tsyplakov_parser = new JSONParser();
            JSONObject tsyplakov_obj = (JSONObject) tsyplakov_parser.parse(tsyplakov_json_str);
            
            // тут получаем данные 
            JSONObject tsyplakov_currentWeather = (JSONObject) tsyplakov_obj.get("current");
            String tsyplakov_currentTime = (String) tsyplakov_currentWeather.get("time");
            Long tsyplakov_cloudCover = (Long) tsyplakov_currentWeather.get("cloud_cover");
            JSONObject tsyplakov_hourly = (JSONObject) tsyplakov_obj.get("hourly");
            JSONArray tsyplakov_times = (JSONArray) tsyplakov_hourly.get("time");
            JSONArray temperatures = (JSONArray) tsyplakov_hourly.get("temperature_2m");
            JSONArray tsyplakov_rain = (JSONArray) tsyplakov_hourly.get("rain");
            JSONObject tsyplakov_hourlyUnits = (JSONObject) tsyplakov_obj.get("hourly_units");
            String tsyplakov_tempUnit = (String) tsyplakov_hourlyUnits.get("temperature_2m");
            String tsyplakov_rainUnit = (String) tsyplakov_hourlyUnits.get("rain");
            
            // абсолютный путь к директории проекта
            String projectPath = System.getProperty("user.dir");
            String filePath = projectPath + "/../result/forecast.txt";

            // запись в файл
            PrintWriter fileWriter = new PrintWriter(new FileWriter(filePath));
            fileWriter.println("Прогноз погоды: ");
            fileWriter.println("Часовой пояс (TimeZone): " + tsyplakov_obj.get("timezone"));
            fileWriter.println("Текущая погода на время " + tsyplakov_currentTime + ":");
            fileWriter.println("Облачность: " + tsyplakov_cloudCover + "%");
            fileWriter.println();
            fileWriter.println("Прогноз на сегодня по часам:");
            fileWriter.println("+----+----------+---------------+----------+");
            fileWriter.printf("| %-2s | %-8s | %-13s | %-8s |\n", "№", "Время", "Температура(" + tsyplakov_tempUnit + ")", "Осадки(" + tsyplakov_rainUnit + ")");
            fileWriter.println("+----+----------+---------------+----------+");

            // тут выводим шапку таблицы на экран
            System.out.println("Прогноз погоды: ");
            System.out.println("Часовой пояс (TimeZone): " + tsyplakov_obj.get("timezone"));
            System.out.println("Текущая погода на время " + tsyplakov_currentTime + ":");
            System.out.println("Облачность: " + tsyplakov_cloudCover + "%");
            System.out.println();
            System.out.println("Прогноз на сегодня по часам:");
            
            // с форматированием (красивым выводом на экран) помог DeepSeek
            System.out.println("+----------+---------------+----------+");
            System.out.printf("| %-2s | %-8s | %-13s | %-8s |\n", "№", "Время", "Температура(" + tsyplakov_tempUnit + ")", "Осадки(" + tsyplakov_rainUnit + ")");
            System.out.println("+----------+---------------+----------+");
            
            // выводим то, что напарсили (погода)
            for (int i = 0; i < tsyplakov_times.size(); ++i) {
                String time = (String) tsyplakov_times.get(i);
                String hourOnly = time.substring(11, 16);
                Double temperature = (Double) temperatures.get(i);
                Double rain_ = (Double) tsyplakov_rain.get(i);
                
                System.out.printf("| %-2d | %-8s | %-13.1f | %-8.2f |\n", (i + 1), hourOnly, temperature, rain_);
                fileWriter.printf("| %-2d | %-8s | %-13.1f | %-8.2f |\n", (i + 1), hourOnly, temperature, rain_);
            }
            System.out.println("+----------+---------------+----------+");
            fileWriter.println("+----+----------+---------------+----------+");

            fileWriter.close();
            
            // тут обработка ошибок
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        } finally {
            // тут выходим
            webDriver.quit();
        }
    }
}