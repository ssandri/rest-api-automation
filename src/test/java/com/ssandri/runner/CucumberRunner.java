package com.ssandri.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
    features = {"src/test/resources/features/"},
    tags = "@test",
    glue = {"com.ssandri.stepdefinitions"},
    plugin = {
        "pretty",
        "json:build/test-results/cucumber/cucumber.json",
        "json:build/test-results/cucumber/results.cucumber"
    })
public class CucumberRunner extends AbstractTestNGCucumberTests {

}
