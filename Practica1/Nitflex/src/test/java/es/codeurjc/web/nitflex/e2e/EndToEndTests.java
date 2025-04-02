package es.codeurjc.web.nitflex.e2e;

import es.codeurjc.web.nitflex.model.User;
import es.codeurjc.web.nitflex.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EndToEndTests  {

    @Autowired
    private UserRepository userRepository;

    private WebDriver driver;

    @BeforeEach
    public void setupTest() {
        userRepository.deleteAll();
        userRepository.save(new User("FAKE USERX", "fakeUserX@gmail.com"));
        this.driver = new ChromeDriver();
        driver.get("http://localhost:8080/films/new");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAddMovie() {
        WebElement titleInput = driver.findElement(By.xpath("//input[@name='title']"));
        titleInput.sendKeys("Nueva Pelicula");

        WebElement saveButton = driver.findElement(By.id("Save"));
        saveButton.click();

        WebElement movieList = driver.findElement(By.id("film-title"));
        assertTrue(movieList.getText().contains("Nueva Pelicula"));

        List<WebElement> movies = driver.findElements(By.id("film-title"));
        assertTrue(!movies.isEmpty());
    }
    
    @Test
    public void testAddMovieWithoutTitle() {
        driver.get("http://localhost:8080");
        List<WebElement> movies = driver.findElements(By.id("film-title"));
        Integer numBefore = movies.size();

        driver.get("http://localhost:8080/films/new");
        driver.findElement(By.xpath("//input[@name='releaseYear']")).sendKeys("1234");
        WebElement saveButton = driver.findElement(By.id("Save"));
        saveButton.click();

        WebElement errorMessageElement = driver.findElement(By.id("error-list"));
        String errorMessage = errorMessageElement.getText();

        assertEquals("The title is empty", errorMessage, "El mensaje de error no coincide con el esperado.");
        
        driver.get("http://localhost:8080");
        
        List<WebElement> moviesAfter = driver.findElements(By.id("film-title"));
        Integer numAfter = moviesAfter.size();
        Boolean sameMovies = numAfter == numBefore;
        assertTrue(sameMovies, "La pelicula sin titulo no se deberia guardar ni aparecer en la pantalla principal.");
    }
}