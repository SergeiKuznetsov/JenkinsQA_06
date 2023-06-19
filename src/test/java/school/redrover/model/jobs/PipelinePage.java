package school.redrover.model.jobs;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import school.redrover.model.*;
import school.redrover.model.jobsconfig.PipelineConfigPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BaseProjectPage;

public class PipelinePage extends BaseProjectPage<PipelinePage> {

    @FindBy(xpath = "//div[@id='main-panel']")
    private WebElement mainPanel;

    @FindBy(css = ".stage-header-name-0")
    private WebElement stage;

    @FindBy(css = ".console-output")
    private WebElement consoleOutput;

    public PipelinePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public PipelineConfigPage clickConfigure() {
        setupClickConfigure();
        return new PipelineConfigPage(this);
    }

    public String getProjectNameSubtitle() {
        String projectName = mainPanel.getText();
        String subStr = projectName.substring(projectName.indexOf(':') + 2);
        return subStr.substring(0, subStr.indexOf("Add")).trim();
    }

    public String getStage() {
        return stage.getText();
    }

    public String getConsoleOutputField() {
        return consoleOutput.getText();
    }
}
