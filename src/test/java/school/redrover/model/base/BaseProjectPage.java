package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;

import java.time.Duration;
import java.util.List;

public abstract class BaseProjectPage<Self extends BaseProjectPage<?>> extends BaseJobPage<Self> {

    @FindBy(partialLinkText = "Delete ")
    private WebElement deleteButton;

    @FindBy(xpath = "//a[contains(@href, 'changes')]")
    private WebElement changes;

    @FindBy(xpath = "//form[@id='disable-project']/button")
    private WebElement disableButton;

    @FindBy(xpath = "//form[@id='enable-project']/button")
    private WebElement enableButton;

    @FindBy(css = "form#enable-project")
    private WebElement disableMessageText;

    @FindBy(css = "[href*='build?']")
    private WebElement buildNowButton;

    @FindBy(css = "#buildHistory>div>div>span>div>:nth-child(2)")
    private WebElement trend;

    @FindBy(xpath = "//a[contains(@href, 'build?')]")
    private WebElement buildWithParameters;

    @FindBy(css = ".build-icon")
    private WebElement build;

    @FindBy(xpath = "//ul[@class='permalinks-list']//li")
    private List<WebElement> permalinks;

    @FindBy(xpath = "//td[@class='build-row-cell']")
    private WebElement buildRowCell;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement buildButton;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    private void clickDeleteButton() {
        deleteButton.click();
    }

    public MainPage clickDeleteAcceptAlert() {
        clickDeleteButton();
        getDriver().switchTo().alert().accept();
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
        return new MainPage(getDriver());
    }

    public Self clickDeleteAndDismissAlert() {
        clickDeleteButton();
        getDriver().switchTo().alert().dismiss();
        return (Self)this;
    }

    public ChangesPage<Self> clickChangeOnLeftSideMenu() {
        changes.click();
        return new ChangesPage<>((Self)this);
    }

    public Self clickDisable() {
        disableButton.click();
        return (Self)this;
    }

    public Self clickEnable() {
        getWait5().until(ExpectedConditions.elementToBeClickable(enableButton)).click();
        return (Self)this;
    }

    public String getEnableButtonText(){
        return enableButton.getText();
    }

    public String getDisableButtonText() {
        return disableButton.getText();
    }

    public String getDisabledMessageText(){
        return disableMessageText.getText().trim().substring(0, 34);
    }

    public Self clickBuildNow() {
        buildNowButton.click();
        return (Self)this;
    }

    public TimelinePage clickTrend() {
        trend.click();
        return new TimelinePage(getDriver());
    }

    public ConsoleOutputPage openConsoleOutputForBuild(String jobName, int buildNumber) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/" + jobName + "/" + buildNumber + "/console')]"))).click();
        return new ConsoleOutputPage(getDriver());
    }

    public BuildPage selectBuildItemTheHistoryOnBuildPage(int buildNumber) {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'#" + buildNumber + "')]"))).click();
        return new BuildPage(getDriver());
    }

    public BuildPage clickBuildWithParameters() {
        buildWithParameters.click();
        return new BuildPage(getDriver());
    }

    public Self clickBuildIcon() {
        build.click();
        return (Self)this;
    }

    public int getSizeOfPermalinksList() {
        getWait2().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2")));
        return permalinks.size();
    }

    public BuildPage selectBuildNowAndOpenBuildRow() {
        clickBuildNow();
        getWait10().until(ExpectedConditions
                .elementToBeClickable(buildRowCell));
        return new BuildPage(getDriver());
    }

    public BuildPage selectBuildWitchParametersAndSubmitAndOpenBuildRow() {
        clickBuildNow();
        buildButton.click();
        getWait10().until(ExpectedConditions
                .elementToBeClickable(buildRowCell));
        return new BuildPage(getDriver());
    }
}
