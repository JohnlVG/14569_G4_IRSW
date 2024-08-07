package ec.edu.espe.granjapiscicola;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;
import java.util.List;
import static org.junit.Assert.fail;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CotizacionStepDefinition extends BasicStepDefinition {

    @Given("Quiero cotizar productos")
    public void quiero_cotizar_productos() {
        createPDF("Cotizacion de productos");
        addText("Inicio de prueba: Cotizar productos.");
        addText("Como comprador");
        addText("Quiero poder cotizar productos");
        addText("Para enviar al vendedor");

        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get("file:///C:/Users/noobp/OneDrive/Escritorio/Requisitos%20(2)/Requisitos/Cotizar.html#");
            wait(1);
            captureScreenShot();
        } catch (Exception e) {
            addText("Error al cargar la página: " + e.getMessage());
            captureScreenShot();
            fail("No se pudo cargar la página correctamente.");
            driver.quit();
            closePDF();
        }
    }

    @When("Doy click en el bot1 {string}")
    public void doy_click_en_el_bot1(String bot1) {
        addText("Doy click en el primer producto " + bot1);
        captureScreenShot();

        try {
            // Crear una instancia de WebDriverWait con Duration
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Espera de hasta 10 segundos

            // Buscar el botón usando el texto de enlace (COTIZAR)
            List<WebElement> quoteButtons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".quote-button")));
            WebElement targetButton = null;

            for (WebElement button : quoteButtons) {
                if (button.getText().equals(bot1)) {
                    targetButton = button;
                    break;
                }
            }

            if (targetButton != null) {
                targetButton.click();
                addText("Se coloca en la lista:");
            } else {
                addText("Error: No se encontró el botón con el texto: " + bot1);
                fail("No se encontró el botón con el texto: " + bot1);
            }
        } catch (Exception e) {
            addText("Error: No se pudo hacer clic en el botón: " + e.getMessage());
            captureScreenShot();
            fail("No se pudo hacer clic en el botón.");
        }
        wait(1);
        captureScreenShot();
    }

    @Then("Se debe validar que los productos se ingresen correctamente en la lista")
    public void se_debe_validar_que_los_productos_se_ingresen_correctamente_en_la_lista() {
        try {
            WebElement quoteListContainer = driver.findElement(By.id("quoteList"));
            List<WebElement> quoteItems = quoteListContainer.findElements(By.className("quote-item"));

            if (quoteItems.size() > 0) {
                addText("La lista de productos cotizados contiene " + quoteItems.size() + " elementos.");
                captureScreenShot();
            } else {
                addText("Error: La lista de productos cotizados está vacía.");
                captureScreenShot();
                fail("No se ingresaron productos en la lista.");
            }
        } catch (Exception e) {
            addText("Error durante la validación de productos en la lista: " + e.getMessage());
            captureScreenShot();
            fail("Ocurrió un error al intentar validar la lista de productos.");
        } finally {
            driver.quit();
            closePDF();
        }
    }
}

