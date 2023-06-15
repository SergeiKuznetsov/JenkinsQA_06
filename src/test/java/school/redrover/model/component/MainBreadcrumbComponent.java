package school.redrover.model.component;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.MainPage;
import school.redrover.model.base.BaseComponent;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;

import java.time.Duration;

public class MainBreadcrumbComponent<Page extends BasePage<?, ?>> extends BaseComponent<Page> {

    public MainBreadcrumbComponent(Page page) {
        super(page);
    }

    public String getFullBreadcrumbPath() {

        return getWait5()
            .until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//div[@id='breadcrumbBar']")))
            .getText()
            .replaceAll("\\n", " > ")
            .trim();
    }

    public int countBreadcrumbItems() {

        return this
            .getFullBreadcrumbPath()
            .trim()
            .split("\\s>\\s")
            .length + 1;
    }

    private WebElement getListItem(String listItemName) {

        return getWait5()
            .until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//li[@class='jenkins-breadcrumbs__list-item']/a[text() = '%s']"
                .formatted(listItemName))));
    }

    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickListItem(String listItemName, ReturnedPage returnedPage){

        getListItem(listItemName).click();
        return returnedPage;
    }

    public MainPage navigateToDashboardByBreadcrumb() {

        getListItem("Dashboard").click();
        return new MainPage(getDriver());
    }

    public MainBreadcrumbComponent<Page> openDropdownOfListItem(String listItemName) {

        Actions actions = new Actions(getDriver());
        final WebElement listItem = this.getListItem(listItemName);
        final WebElement chevron = listItem.findElement(By.xpath("./button"));

        actions.moveToElement(listItem).perform();
        actions.moveToElement(chevron).perform();
        chevron.click();

        return this;
    }


    public <ReturnedPage extends BaseMainHeaderPage<?>> ReturnedPage clickDropdownOption(String optionText, ReturnedPage returnedPage) {

        getWait5()
        .until(ExpectedConditions.visibilityOfElementLocated(By
            .xpath("//div[@id='breadcrumb-menu']//span[text() = '%s']".formatted(optionText))))
        .click();

        return returnedPage;
    }

    private void hoverOver(By locator) {
        new Actions(getDriver())
                .moveToElement(getDriver().findElement(locator))
                .pause(Duration.ofMillis(300))
                .perform();
    }

    public MainBreadcrumbComponent<Page> openDashboardDropdownMenu() {
        hoverOver(By.xpath("//a[text()='Dashboard']"));
        getDriver().findElement(By.xpath("//a[text()='Dashboard']/button")).sendKeys(Keys.RETURN);

        return this;
    }

    public <PageFromSubMenu extends BaseMainHeaderPage<?>> PageFromSubMenu selectAnOptionFromDashboardManageJenkinsSubmenuList(
            String menuItem, PageFromSubMenu pageFromSubMenu) {

        openDashboardDropdownMenu();

        new Actions(getDriver())
                .moveToElement(getDriver().findElement(By.xpath("//a[contains(span, 'Manage Jenkins')]")))
                .pause(500)
                .moveToElement(getDriver().findElement(By.xpath("//span[contains(text(), '" + menuItem + "')]")))
                .click()
                .perform();

        return pageFromSubMenu;
    }
}
