package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class UserPage extends BaseMainHeaderPage<UserPage> {

    @FindBy(xpath = "//a[@href='addUser']")
    private WebElement createUser;

    @FindBy(xpath = "//span[contains(text(), 'Configure')]")
    private WebElement configureInDropDownMenu;

    @FindBy(xpath = "//a[@class ='jenkins-table__link model-link inside']")
    private List<WebElement> users;

    @FindBy(xpath = "//a[@class='jenkins-table__button'][1]")
    private List<WebElement> configureUserButton;

    @FindBy(xpath = "//a[@class='jenkins-table__button jenkins-!-destructive-color']")
    private WebElement deleteButton;

    @FindBy(name = "_.description")
    private WebElement addEditDescriptionButton;

    public UserPage(WebDriver driver) {
        super(driver);
    }

    public UserDeletePage clickDeleteUserBtnFromUserPage(String newUserName) {
        TestUtils.click(this, getDriver().
                findElement(By.xpath("//a[@href='/user/" + newUserName + "/delete']")));
        return new UserDeletePage(getDriver());
    }

    public ViewPage clickMyViewsDropDownMenuUser() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/my-views')]")).click();
        return new ViewPage(getDriver());
    }


    public CreateUserPage clickCreateUser() {
        createUser.click();

        return new CreateUserPage(getDriver());
    }
    public String getButtonText() {
        return createUser.getText().trim();
    }

    public UserPage clickUserIDName(String userName) {
        WebElement userIDNameLink = getWait2()
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@href='user/" + userName + "/']")));
        userIDNameLink.click();

        return this;
    }

    public UserPage openUserIDDropDownMenu(String username){
        getDriver()
                .findElement(By.xpath("//a[@href='user/" + username + "/']/button[@class='jenkins-menu-dropdown-chevron']"))
                .sendKeys(Keys.ENTER);
        return this;
    }

    public UserPage selectConfigureUserIDDropDownMenu() {
        configureInDropDownMenu.click();

        return this;
    }

    public boolean isUserExist(String userName) {
        for (WebElement el : users) {
            if (el.getText().equals(userName)) {
                return true;
            }
        }

        return false;
    }

    public DeletePage<UserPage> clickDeleteUser() {
        deleteButton.click();

        return new DeletePage<>(getDriver(), this);
    }

    public boolean getUserDeleted(String username) {
        List<WebElement> userList = getDriver().findElements(By.id("people"));

        for (WebElement user : userList) {
            if (user.getText().equals(username)) {
                break;
            }
        }

        return false;
    }

    public UserConfigPage clickUserConfigureButton(String username) {
        getDriver().findElement(By.xpath("//a[@href='user/" + username + "/']")).click();

        return new UserConfigPage(new StatusUserPage(getDriver()));
    }
}


