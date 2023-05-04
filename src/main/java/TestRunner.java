import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/featureFiles/automation"},
        glue = {"stepDefinitions"},
        plugin = { "pretty", "html:target/cucumber-reports/index.html" })
public class TestRunner {
}