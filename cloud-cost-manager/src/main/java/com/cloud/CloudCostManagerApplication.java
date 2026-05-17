package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.awt.Desktop;
import java.net.URI;

import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Main Spring Boot Application for Cloud Cost Manager
 */
@SpringBootApplication
@ServletComponentScan
public class CloudCostManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudCostManagerApplication.class, args);
        openHomePage();
    }

    private static void openHomePage() {
        try {
            

            Thread.sleep(1000);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:8080"));
            } else {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("rundll32 url.dll,FileProtocolHandler http://localhost:8080");
            }
        } catch (Exception e) {
            System.out.println("Could not open browser automatically: " + e.getMessage());
        }
    }
}
