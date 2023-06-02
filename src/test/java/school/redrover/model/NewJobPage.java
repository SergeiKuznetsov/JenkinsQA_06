package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseConfigPage;
import school.redrover.model.base.BaseMainHeaderPage;

public class NewJobPage<ConfigPage extends BaseConfigPage<?,?>> extends BaseMainHeaderPage<NewJobPage<?>> {

    private final ConfigPage configPage;

    public NewJobPage(WebDriver driver, ConfigPage configPage) {
        super(driver);
        this.configPage = configPage;
    }

    public NewJobPage<ConfigPage> enterItemName(String nameJob) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']"))).sendKeys(nameJob);
        return this;
    }

    public NewJobPage<MultiConfigurationProjectConfigPage> selectMultiConfigurationProject() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='hudson_matrix_MatrixProject']"))).click();
        return new NewJobPage<>(getDriver(), new MultiConfigurationProjectConfigPage(new MultiConfigurationProjectPage(getDriver())));
    }

    public NewJobPage<FolderConfigPage> selectFolder() {
        getDriver().findElement(By.xpath("//li[contains(@class, 'folder_Folder')]")).click();
        return new NewJobPage<>(getDriver(), new FolderConfigPage(new FolderPage(getDriver())));
    }

    public NewJobPage<MultibranchPipelineConfigPage> selectMultibranchPipeline() {
        getDriver().findElement(By.xpath("//li[contains(@class, 'WorkflowMultiBranchProject')]")).click();
        return new NewJobPage<>(getDriver(), new MultibranchPipelineConfigPage(new MultibranchPipelinePage(getDriver())));
    }

    public NewJobPage<OrganizationFolderConfigPage> selectOrganizationFolder() {
        getDriver().findElement(By.xpath("//li[contains(@class, 'OrganizationFolder')]")).click();
        return new NewJobPage<>(getDriver(), new OrganizationFolderConfigPage(new OrganizationFolderPage(getDriver())));
    }

    public NewJobPage<FreestyleProjectConfigPage> selectFreestyleProject() {
        getWait5().until(ExpectedConditions.elementToBeClickable(getFreestyleProject())).click();
        return new NewJobPage<>(getDriver(), new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())));
    }

    public NewJobPage<PipelineConfigPage> selectPipelineProject() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Pipeline']"))).click();
        return new NewJobPage<>(getDriver(), new PipelineConfigPage(new PipelinePage(getDriver())));
    }

    public ConfigPage clickOkButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        return configPage;
    }

    public NewJobPage<ConfigPage>copyFrom(String typeToAutocomplete) {
        getDriver().findElement(By.xpath("//input[contains(@autocompleteurl, 'autoCompleteCopyNewItemFrom')]"))
                .sendKeys(typeToAutocomplete);
        return this;
    }

    public String getItemInvalidMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(getItemInvalidNameMessage())).getText();
    }

    public boolean isOkButtonEnabled() {
        return getOkButton().isEnabled();
    }

    public CreateItemErrorPage clickOkToCreateWithExistingName() {
        getOkButton().click();
        return new CreateItemErrorPage(getDriver());
    }

    public String getItemNameRequiredMessage() {
        return getDriver().findElement(By.id("itemname-required")).getText();
    }

    public WebElement getOkButton() {
        return getDriver().findElement(By.xpath("//button[@id='ok-button']"));
    }

    private WebElement getFreestyleProject() {
        return getDriver().findElement(By.className("hudson_model_FreeStyleProject"));
    }

    private WebElement getItemInvalidNameMessage() {
        return getDriver().findElement(By.id("itemname-invalid"));
    }

    public String getItemNameRequiredErrorText() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-required"))).getText();
    }

    public NewJobPage<ConfigPage> clickButtonOk() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button")))
                .click();
        return this;
    }

    public FolderConfigPage copyFromFolder(String typeToAutocomplete) {
        getDriver().findElement(By.id("from"))
                .sendKeys(typeToAutocomplete);
        clickOkButton();
        return new FolderConfigPage(new FolderPage(getDriver()));
    }
}
