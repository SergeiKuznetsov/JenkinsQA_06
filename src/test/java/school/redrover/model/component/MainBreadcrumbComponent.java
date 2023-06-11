package school.redrover.model.component;

import school.redrover.model.*;
import school.redrover.model.base.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainBreadcrumbComponent<Page extends BasePage<?, ?>> extends BaseComponent<Page> {

    public MainBreadcrumbComponent(Page page) {
        super(page);
    }

    public String getFullBreadcrumbPath() {

        return getWait5()
            .until(ExpectedConditions.visibilityOfElementLocated
                    (By.xpath("//div[@id='breadcrumbBar']")))
            .getText()
            .replaceAll("\\n", " > ")
            .trim()
        ;

    }

    public int countBreadcrumbItems()  {

        return this
            .getFullBreadcrumbPath()
            .replaceAll("[^>]", "")
            .trim()
            .length()
            + 1
        ;

    }


    public WebElement getListItem(String listItemName) {

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[@class='jenkins-breadcrumbs__list-item']" +
                        "/a[text() = '%s']".formatted(listItemName)
                )
            )
        );

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
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//div[@id='breadcrumb-menu']//span[text() = '%s']"
                    .formatted(optionText)))
            ).click();

        return returnedPage;

    }

}


