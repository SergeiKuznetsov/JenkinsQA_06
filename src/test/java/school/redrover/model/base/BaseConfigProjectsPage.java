package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.CreateItemErrorPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseConfigProjectsPage<Self extends BaseConfigPage<?, ?>, ProjectPage extends BaseMainHeaderPage<?>> extends BaseConfigPage<Self, ProjectPage> {

    @FindBy(xpath = "//button[contains(text(), 'Add build step')]")
    private WebElement addBuildStepDropDown;

    @FindBy(xpath = "//button[contains(text(), 'Add post-build action')]")
    private WebElement addPostBuildActionDropDown;

    @FindBy(xpath = "//a[contains(text(), 'Execute shell')]")
    private WebElement executeShellOption;

    @FindBy(xpath = "//div[@class='CodeMirror']")
    private WebElement executeShellCommandField;
    @FindBy(xpath = "//div[@class='CodeMirror']//textarea")
    private WebElement executeShellCommandTextArea;

    @FindBy(className = "CodeMirror-lines")
    private List<WebElement> executeShellCodeLines;

    @FindBy(xpath = "//span[@class='jenkins-checkbox']//input[@id='cb4']")
    private WebElement discardOldBuildsCheckBox;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement daysToKeepBuildsField;

    @FindBy(xpath = "//div[text()='Days to keep builds']")
    private WebElement daysToKeepBuildsFieldName;

    @FindBy(xpath = "//input[@name='_.numToKeepStr']")
    private WebElement maxNumOfBuildsToKeepField;

    @FindBy(xpath = "//span[text() = 'Enabled']")
    private WebElement disableJobSwitch;

    @FindBy(xpath = "//span[text() = 'Disabled']")
    private WebElement enableJobSwitch;

    @FindBy(xpath = "//label[text()='GitHub project']")
    private WebElement gitHubProjectCheckBox;

    @FindBy(css = "[name='_.projectUrlStr']")
    private WebElement gitHubProjectUrlField;

    @FindBy(xpath = "//label[text()='This project is parameterized']")
    private WebElement projectIsParametrizedCheckBox;

    @FindBy(xpath = "//button[@class='hetero-list-add']")
    private WebElement addParametersDropDown;

    @FindBy(xpath = "//div[@id='yui-gen6']//li/a")
    private List<WebElement> addParameterDropDownOptions;

    @FindBy(xpath = "//input[@name='parameter.name']")
    private WebElement parameterNameField;

    @FindBy(xpath = "//textarea[@name='parameter.choices']")
    private WebElement parameterChoicesField;

    @FindBy(xpath = "//textarea[@name='parameter.description']")
    private WebElement parameterDescriptionField;

    @FindBy(xpath = "//label[normalize-space(text())='Set by Default']")
    private WebElement setByDefaultCheckBox;

    @FindBy(xpath = "//div[div[@id='build-steps']]//li/a")
    private List<WebElement> addBuildStepDropDownOptions;

    @FindBy(xpath = "//label[normalize-space(text())='Throttle builds']")
    private WebElement throttleBuilds;

    @FindBy(xpath = "//select[@name='_.durationName']")
    private WebElement getTimePeriod;

    public BaseConfigProjectsPage(ProjectPage projectPage) {
        super(projectPage);
    }

    public Self addExecuteShellBuildStep(String command) {
        getWait5().until(ExpectedConditions.elementToBeClickable(addBuildStepDropDown));
        Actions actions = new Actions(getDriver());
        actions.scrollToElement(addPostBuildActionDropDown).click().perform();
        getWait2().until(ExpectedConditions.elementToBeClickable(addBuildStepDropDown)).click();

        executeShellOption.click();
        getWait5().until(ExpectedConditions.visibilityOf(executeShellCommandField));
        actions.scrollToElement(addPostBuildActionDropDown).click().perform();
        executeShellCodeLines.get(0).click();
        executeShellCommandTextArea.sendKeys(command);

        return (Self) this;
    }

    public Self clickOldBuildCheckBox() {
        TestUtils.clickByJavaScript(this, discardOldBuildsCheckBox);

        return (Self) this;
    }

    public Self enterDaysToKeepBuilds(int number) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", daysToKeepBuildsFieldName);
        TestUtils.sendTextToInput(this, daysToKeepBuildsField, String.valueOf(number));

        return (Self) this;
    }

    public Self enterMaxNumOfBuildsToKeep(int number) {
        TestUtils.sendTextToInput(this, maxNumOfBuildsToKeepField, String.valueOf(number));

        return (Self) this;
    }

    public Self switchCheckboxDisable() {
        getWait2().until(ExpectedConditions.elementToBeClickable(disableJobSwitch)).click();

        return (Self) this;
    }

    public Self switchCheckboxEnabled() {
        getWait2().until(ExpectedConditions.elementToBeClickable(enableJobSwitch)).click();

        return (Self) this;
    }

    public String getTextDisable() {
        return getWait10().until(ExpectedConditions.elementToBeClickable(enableJobSwitch)).getText();
    }

    public String getTextEnabled() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(disableJobSwitch)).getText();
    }

    public String getDaysToKeepBuilds(String attribute) {
        return daysToKeepBuildsField.getAttribute(attribute);
    }

    public String getMaxNumOfBuildsToKeep(String attribute) {
        return maxNumOfBuildsToKeepField.getAttribute(attribute);
    }

    public Self clickGitHubProjectCheckbox() {
        gitHubProjectCheckBox.click();

        return (Self) this;
    }

    public Self inputGitHubProjectUrl(String text) {
        gitHubProjectUrlField.sendKeys(text);

        return (Self) this;
    }

    public CreateItemErrorPage getErrorPage() {
        return new CreateItemErrorPage(getDriver());
    }

    public Self checkProjectIsParametrized() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", projectIsParametrizedCheckBox);

        return (Self) this;
    }

    public Self selectParameterInDropDownByType(String type) {
        for (WebElement element :
                getWait5().until(ExpectedConditions.visibilityOfAllElements(addParameterDropDownOptions))) {
            if (element.getText().contains(type)) {
                element.click();
                break;
            }
        }

        return (Self) this;
    }

    public Self openAddParameterDropDown() {
        getWait5().until(ExpectedConditions.elementToBeClickable(addParametersDropDown));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();",
                getWait5().until(ExpectedConditions.elementToBeClickable(projectIsParametrizedCheckBox)));
        addParametersDropDown.click();

        return (Self) this;
    }

    public Self inputParameterName(String name) {
        parameterNameField.sendKeys(name);

        return (Self) this;
    }

    public Self inputParameterChoices(List<String> parameterChoices) {
        for (String element : parameterChoices) {
            parameterChoicesField.sendKeys(element + "\n");
        }

        return (Self) this;
    }

    public Self inputParameterDescription(String description) {
        parameterDescriptionField.sendKeys(description);

        return (Self) this;
    }

    public Self selectCheckboxSetByDefault() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", setByDefaultCheckBox);

        return (Self) this;
    }

    public Self openBuildStepOptionsDropdown() {
        TestUtils.scrollToElementByJavaScript(this, addBuildStepDropDown);
        getWait5().until(ExpectedConditions.elementToBeClickable(addBuildStepDropDown)).click();

        return (Self) this;
    }

    public List<String> getOptionsInBuildStepDropdown() {
        return TestUtils.getTexts(addBuildStepDropDownOptions);
    }

    public Self checkThrottleBuilds() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", throttleBuilds);
        js.executeScript("arguments[0].click();", throttleBuilds);
        return (Self) this;
    }

    public Self selectTimePeriod(String timePeriod) {
        new Select(getDriver()
                .findElement(By.xpath("//select[@name='_.durationName']"))).selectByValue(timePeriod.toLowerCase());
        return (Self) this;
    }

    public String getTimePeriodText() {
        return new Select(getDriver().findElement(By.xpath("//select[@name='_.durationName']"))).getFirstSelectedOption().getText();
    }
}