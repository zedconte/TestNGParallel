package com.finningphase2.basepages.basicpages;

import com.BaseTest;
import tellerium.mvcpages.seleniumutils.StaleElementUtils;
import com.finningphase2.util.FunctionUtil;
import com.finningphase2.util.PageManager;
import com.finningphase2.util.SystemUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SiteAdminBasicPage extends BasicPage implements IWebPage {

	StaleElementUtils staleElementUtils;
	public String pageName = SystemUtil.resource.getProperty("SiteAdminURL");
	public String pageUrl = BaseTest.URL+pageName;
	public int timeout = 15;

	public SiteAdminBasicPage(PageManager pm) {
		super(pm);
	}

	public SiteAdminBasicPage(PageManager pm, String authoringPageUrl) {
		super(pm);
		pageUrl = authoringPageUrl;
		WebDriver driver = pm.getDriver();
		ArrayList<String> tabs = new ArrayList<String> (pm.getDriver().getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		driver.close();
		driver.switchTo().window(tabs.get(1));
		pageManager.navigate(pageUrl);
	}

	@Override
	public void open() throws Exception {
		pageManager.navigate(pageUrl);
	}

	public void open(String authoringPageUrl) throws Exception {
		pageManager.navigate(authoringPageUrl);
	}

	@FindBy(xpath="//input[@id='username']")
	public WebElement userName;

	@FindBy(xpath="//input[@id='password']")
	public WebElement passWord;

	@FindBy(xpath="//button[@id='submit-button']")
	public WebElement loginButton;


	/**
	 * Objective: Verify to login to CWS with CWS ID and password
	 * @throws Exception
	 */

	public void loginWithCWSID(String environment)throws Exception{
		
		Properties properties = SystemUtil.loadPropertiesResources("/testdata_credentials1.properties");

		String devUserName = properties.getProperty("devUserName");
		String devPassword = properties.getProperty("devPassword");
		String qaUserName = properties.getProperty("qaUserName");
		String qaPassword = properties.getProperty("qaPassword");
		
//		String devUserName = SystemUtil.resource.getProperty("devUserName");
//		String devPassword = SystemUtil.resource.getProperty("devPassword");
//		String qaUserName = SystemUtil.resource.getProperty("qaUserName");
//		String qaPassword = SystemUtil.resource.getProperty("qaPassword");

		FindElementWait(loginButton);
		StaleElementUtils.refreshElement(loginButton);
		if(environment.contains("DEV")){
			FunctionUtil.input(userName, devUserName);
			FunctionUtil.input(passWord, devPassword);
		}else{
			FunctionUtil.input(userName, qaUserName);
			FunctionUtil.input(passWord, qaPassword);
		}
		loginButton.click();
	}

	@FindBy(xpath="//input[@type='email']")
	public WebElement uATUserName;

	@FindBy(xpath="//input[@type='submit']")
	public WebElement uATNextButton;

	@FindBy(xpath="//input[@type='password']")
	public WebElement uATPassword;

	public By uATPasswordBy = new By.ByXPath("//input[@type='password']");

	@FindBy(xpath="//input[@type='submit']")
	public WebElement uATSignIn;

	@FindBy(name="DontShowAgain")
	public WebElement dontShowCheckBox;

	@FindBy(xpath="//input[@type='submit']")
	public WebElement yesButton;

	/*Login to UAT environment*/

	public void loginToUAT()throws Exception{
		
		Properties properties = SystemUtil.loadPropertiesResources("/testdata_credentials1.properties");

		String inputUserName = properties.getProperty("uATUserName");
		String inputPassword = properties.getProperty("uATPassword");
		
//		String inputUserName = SystemUtil.resource.getProperty("uATUserName");
//		String inputPassword = SystemUtil.resource.getProperty("uATPassword");

		FindElementWait(uATUserName, timeout);
		FunctionUtil.input(uATUserName, inputUserName);
		pageManager.click(uATNextButton);
		FindElementWait(uATPassword, timeout);
		FunctionUtil.input(uATPassword, inputPassword);
		FindElementWait(uATSignIn, timeout);

		pageManager.click(uATSignIn);
		FindElementWait(dontShowCheckBox, timeout);

		pageManager.click(dontShowCheckBox);
		pageManager.click(yesButton);
	}
	
	
	

	@FindBy(xpath="//img[contains(@class,'foundation-collection-item-thumbnail')]")
	public List<WebElement> templateImg;

	@FindBy(xpath="//coral-button-label[contains(text(),'Delete')]")
	public WebElement deleteButton;
	
	@FindBy(xpath="//coral-icon[@icon='more']")
	public WebElement moreButton;
	
	@FindBy(xpath="(//coral-button-label[contains(text(),'Delete')])[2]")
	public WebElement deleteConButton;

	public void waitForElementPresent(final By by, int timeout){
		WebDriverWait wait = (WebDriverWait)new WebDriverWait(pageManager.getDriver(),timeout)
				.ignoring(StaleElementReferenceException.class);
		wait.until(new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver webDriver) {
				WebElement element = webDriver.findElement(by);
				return element != null && element.isDisplayed();
			}
		}
		);
	}

	private String getLocatorFromWebElement(WebElement element) {

		return element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
	}
	private By getByFromElement(WebElement element) {

		By by = null;
		String[] pathVariables = (element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "")).split(":");

		String selector = pathVariables[0].trim();
		String value = pathVariables[1].trim();

		switch (selector) {
			case "id":
				by = By.id(value);
				break;
			case "className":
				by = By.className(value);
				break;
			case "tagName":
				by = By.tagName(value);
				break;
			case "xpath":
				by = By.xpath(value);
				break;
			case "cssSelector":
				by = By.cssSelector(value);
				break;
			case "linkText":
				by = By.linkText(value);
				break;
			case "name":
				by = By.name(value);
				break;
			case "partialLinkText":
				by = By.partialLinkText(value);
				break;
			default:
				throw new IllegalStateException("locator : " + selector + " not found!!!");
		}
		return by;
	}

	public void deletePageByPageNameTouchUI(String pageName) throws Exception{

		for(int i = 0;i<templateImg.size();i++){	
			if(templateImg.get(i).getAttribute("src").contains(pageName)){
				templateImg.get(i).click();
			}
		}
		if(moreButton.isDisplayed()){
		moreButton.click();
		}
		FindElementWait(deleteButton);
		deleteButton.click();

		FindElementWait(deleteConButton);
		if(deleteConButton.isDisplayed()){
			deleteConButton.click();
		}
	}

	@FindBy(xpath="//coral-button-label[contains(text(),'Create')]")
	public WebElement createButton;

	@FindBy(xpath="//coral-anchorlist[@class='coral3-BasicList coral3-AnchorList']")
	public WebElement popList;

	@FindBy(xpath="//coral-list-item-content[contains(text(),'Page')]")
	public WebElement pageCreate;

	@FindBy(xpath="//coral-masonry-item[@class='foundation-collection-item coral3-Masonry-item is-managed']")
	public List<WebElement> contentPageTemplate;

	@FindBy(xpath="//coral-button-label[contains(text(),'Next')]")
	public WebElement nextButton;

	@FindBy(xpath="(//input[@class='coral-Form-field coral3-Textfield'])[1]")
	public WebElement titleInput;

	@FindBy(xpath="//label[contains(text(),'Description *')]/following-sibling::textarea")
	public WebElement desInput;

	@FindBy(xpath="//button[@type='submit']")
	public WebElement createPageButton;

	@FindBy(xpath="//coral-button-label[contains(text(),'Open')]")
	public WebElement openButton;

	@FindBy(xpath="//coral-button-label[text()='Done']")
	public WebElement DoneButton;




	/**
	 * Objective: Verify to login to CWS with CWS ID and password
	 * @throws Exception
	 */
	public void createTemplatePage(int templateIndex, String titleContent, String desContent) throws Exception{
		/**
		 * templateIndex 
		 * 0: ProductSubCategory template
		 * 1: BranchDetail Template
		 * 2: ContentPage template
		 * 3: FormPage Template
		 * 4: Campaign Template
		 * 5: ProductCamparison Template
		 * 6: SearchResultPage Template
		 */
		FunctionUtil.waitUtilElementVisible(createButton); 
		createButton.click();
		Thread.sleep(3000);
		FunctionUtil.waitUtilElementVisible(popList); 
		pageCreate.click();
		Thread.sleep(3000);
		FunctionUtil.waitUtilElementVisible(contentPageTemplate.get(templateIndex)); 
		contentPageTemplate.get(templateIndex).click();
		Thread.sleep(3000);
		if(nextButton.isEnabled()){
			nextButton.click();
		}
		Thread.sleep(1000);
		FunctionUtil.waitUtilElementVisible(titleInput);
		FunctionUtil.input(titleInput, titleContent);
		Thread.sleep(5000);
		desInput.click();
		FunctionUtil.input(desInput, desContent);
		Thread.sleep(1000);
		if(createPageButton.isEnabled()){
			createPageButton.click();
		}
		Thread.sleep(10000);
		FunctionUtil.waitUtilElementVisible(openButton);

		openButton.click();
		//		FunctionUtil.waitUtilElementVisible(componentEdit.get(1));
	}

	@FindBy(xpath="//label[contains(text(), 'Title')]/following-sibling::input[@name='./jcr:title']")
	public WebElement titleText;

	@FindBy(xpath="//label[text()='Name']/following-sibling::input")
	public WebElement nameText;

	@FindBy(xpath="//label[text()='Description *']/following-sibling::textarea")
	public WebElement descriptionText;

	@FindBy(xpath="//coral-button-label[contains(text(),'Create')]")
	public WebElement CreateButton;

	@FindBy(xpath="//div[@data-text='Drag components here']")
	public List<WebElement> componentEdit;

	@FindBy(xpath="//button[@data-action='INSERT']")
	public WebElement insertButton;

	@FindBy(xpath="//input[@class='coral3-Textfield coral-DecoratedTextfield-input coral3-Search-input']")
	public WebElement searchText;

	//	@FindBy(xpath="//coral-selectlist-item[@class='coral3-SelectList-item' and @tabindex='0']")
	//	public List<WebElement> filterList;

	@FindBy(xpath="//coral-selectlist-item[@class='coral3-SelectList-item']")
	public List<WebElement> filterList;

	/**
	 * Objective: Verify to login to CWS with CWS ID and password
	 * @throws Exception
	 */
	public void addComponent(int sleepTime, String componentName) throws Exception{
		addComponent(sleepTime, componentName, 1);
	}
	public void addComponent(int sleepTime, String componentName, int timeout) throws Exception{
		FindElementWait(componentEdit.get(1), timeout);
		WebElement parsys = componentEdit.get(1);
		pageManager.scrollToViewElement(parsys, "Drag component here");
		FindElementWait(parsys, timeout);
		parsys.click();
		FindElementWait(insertButton, timeout);
		insertButton.click();
		FindElementWait(searchText, timeout);
		FunctionUtil.input(searchText, componentName);
		Thread.sleep(500);
		FindElementsWait(filterList, timeout);
		for(int i = 0; i< filterList.size(); i++){
			if(filterList.get(i).getText().equals(componentName)){
				filterList.get(i).click();
			}
		}
	}
	
	public void addComponentRollo(int sleepTime, String componentName) throws Exception{
		Thread.sleep(sleepTime);
		FunctionUtil.waitUtilElementVisible(componentEdit.get(0));
		WebElement parsys = componentEdit.get(0);
		pageManager.scrollToViewElement(parsys, "Drag component here");
		parsys.click();
		Thread.sleep(4000);
		insertButton.click();
		Thread.sleep(9000);	
		FunctionUtil.input(searchText, componentName);
		Thread.sleep(2000);

		for(int i = 0; i< filterList.size(); i++){
			if(filterList.get(i).getText().equals(componentName)){
				filterList.get(i).click();
			}
		}
		Thread.sleep(5000);
	} 

	/*Text and Image component under CAT is working. Hence created a separate method*/

	@FindBy(xpath="//coral-selectlist-group[2]/coral-selectlist-item[2]")
	public WebElement textAndImage;
	
	@FindBy(xpath="//coral-icon[@icon='railLeft']")
	public WebElement toggleBar;

	public void clickToggleBar() throws Exception {
		toggleBar = StaleElementUtils.refreshElement(toggleBar);
		FindElementWait(toggleBar, 30);
		((JavascriptExecutor) pageManager.getDriver()).executeScript("document.evaluate(\"//coral-icon[@icon='railLeft']\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();");

	}
	
	public void addCATComponent(int sleepTime, String componentName) throws Exception{
		Thread.sleep(sleepTime);
		FunctionUtil.waitUtilElementVisible(componentEdit.get(1));
		WebElement parsys = componentEdit.get(1);
		pageManager.scrollToViewElement(parsys, "Drag component here");
		parsys.click();
		Thread.sleep(4000);
		insertButton.click();
		Thread.sleep(4000);	
		FunctionUtil.input(searchText, componentName);
		Thread.sleep(2000);

		for(int i = 0; i< filterList.size(); i++){
			if(filterList.get(i).getText().equals(componentName))
				pageManager.getDriver().findElements(By.xpath("//coral-selectlist-group[@label='CAT']/coral-selectlist-item[text()='"+componentName+" ']")).get(0).click();
		}
		Thread.sleep(5000);
	} 

	@FindBy(xpath="//button[@class='cq-dialog-header-action cq-dialog-submit coral-Button coral-Button--minimal']")
	public WebElement submitButton;

	@FindBy(xpath="//a[@id='pageinfo-trigger']")
	public WebElement publishLink;

	@FindBy(xpath="//coral-button-label[text()='Publish Page']")
	public WebElement publishButton;

	@FindBy(xpath="//button[@title='View as Published']")
	public WebElement viewPublished;

	public void publishPage() throws Exception{
		FindElementWait(publishLink, timeout);
		publishLink.click();
		FindElementWait(publishButton, timeout);
		publishButton.click();
		FindElementWait(publishLink, timeout);
		publishLink.click();
		FindElementWait(viewPublished, timeout);
		viewPublished = StaleElementUtils.refreshElement(viewPublished);
		if(viewPublished.isDisplayed()){
			viewPublished.click();
		}else{
			FindElementWait(publishLink, timeout);
			publishLink.click();
		}
	}
	
	public void publishPageonly() throws Exception{
		Thread.sleep(5000);
		FunctionUtil.waitUtilElementVisible(publishLink);
		publishLink.click();
		Thread.sleep(3000);
		FunctionUtil.waitUtilElementVisible(publishButton);
		publishButton.click();
		Thread.sleep(5000);
		publishLink.click();
	}

	//Verify Cookie popup in published page
	public void verifyCookiePopUp() throws Exception{
		if(pageManager.isElementPresent(By.xpath("//a[@id='cookieMsg']/strong[text()='Continue']"), "Continue button"))
			pageManager.getDriver().findElement(By.xpath("//a[@id='cookieMsg']/strong[text()='Continue']")).click();
		Thread.sleep(2000);
	}

	public void verifyCookiePopUpRollo() throws Exception{
		if(pageManager.isElementPresent(By.xpath("//p[@class='cookie-banner-text']//following-sibling::a[text()='Continue']"), "Continue button"))
			pageManager.getDriver().findElement(By.xpath("//p[@class='cookie-banner-text']//following-sibling::a[text()='Continue']")).click();
		Thread.sleep(2000);
	}
	
	@FindBy(xpath="//a[@title='Select another mode']")
	public WebElement selectMode;

	@FindBy(xpath="//coral-list-item-content[text()='Design']")
	public WebElement designMode;

	@FindBy(xpath="//coral-list-item-content[text()='Edit']")
	public WebElement editMode;

	@FindBy(xpath="//coral-icon[@icon='selectContainer']")
	public WebElement selectContainer;

	@FindBy(xpath="//button[@data-action='CONFIGURE']")
	public WebElement configureButton;

	@FindBy(xpath="//coral-accordion-item-label/coral-checkbox[@name='./components']")
	public List<WebElement> designFilterList;

	@FindBy(xpath="//input[@class='coral-Textfield coral-DecoratedTextfield-input coral-Search-input']")
	public List<WebElement> searchDesignText;

	@FindBy(xpath="//button[@title='Done']")
	public WebElement doneButton;

	public void switchToDesignMode() throws Exception {
		pageManager.click(selectMode);
		Thread.sleep(5000);
		pageManager.click(designMode);
	}
	
	//Add missing components through Design mode
	public void addComponentDesignMode(int sleepTime, List<WebElement> element, String componentName, int index) throws Exception{
		Thread.sleep(sleepTime);
		pageManager.click(selectMode);
		pageManager.click(designMode);
		Thread.sleep(sleepTime);
		FunctionUtil.waitUtilElementVisible(componentEdit.get(index));
		WebElement parsys = element.get(index);
		pageManager.scrollToViewElement(parsys, "Drag component here");
		parsys.click();
		Thread.sleep(4000);
		selectContainer.click();
		Thread.sleep(4000);	
		pageManager.clickByJS(configureButton, "Configure Button");
		Thread.sleep(4000);	
		pageManager.sendKeys(searchText, componentName);
		Thread.sleep(2000);

		for(int i = 0; i< designFilterList.size(); i++){
			if(designFilterList.get(i).isDisplayed()){
				if(componentName.equals("Footer"))
					pageManager.getDriver().findElement(By.xpath("//input[@value='group:DXM']")).click();
				designFilterList.get(i).click();
			}
		}
		pageManager.click(doneButton);
		Thread.sleep(5000);
		pageManager.click(selectMode);
		pageManager.click(editMode);
	}

	@FindBy(xpath="//input[@name='pageName']")
	public WebElement pageNameInput;


	public void createTemplatePagewithName(int templateIndex, String titleContent, String pageNameContent, String desContent) throws Exception{
		FunctionUtil.waitUtilElementVisible(createButton); 
		createButton.click();
		Thread.sleep(3000);
		FunctionUtil.waitUtilElementVisible(popList); 
		pageCreate.click();
		Thread.sleep(3000);
		FunctionUtil.waitUtilElementVisible(contentPageTemplate.get(templateIndex)); 
		contentPageTemplate.get(templateIndex).click();
		Thread.sleep(3000);
		if(nextButton.isEnabled()){
			nextButton.click();
		}
		Thread.sleep(1000);
		FunctionUtil.waitUtilElementVisible(titleInput);
		FunctionUtil.input(titleInput, titleContent);
		
		FunctionUtil.input(pageNameInput, pageNameContent);		
		FunctionUtil.input(desInput, desContent);
		Thread.sleep(1000);

			createPageButton.click();
		
		Thread.sleep(10000);
		FunctionUtil.waitUtilElementVisible(openButton);

		openButton.click();
		//		FunctionUtil.waitUtilElementVisible(componentEdit.get(1));
	}
	
	@FindBy(xpath="//coral-button-label[contains(text(),'Done')]")
	public WebElement donePageButton;

	public void createTemplatePagewithoutOpen(int templateIndex, String titleContent, String pageNameContent, String desContent) throws Exception{
		FunctionUtil.waitUtilElementVisible(createButton); 
		createButton.click();
		Thread.sleep(3000);
		FunctionUtil.waitUtilElementVisible(popList); 
		pageCreate.click();
		Thread.sleep(3000);
		FunctionUtil.waitUtilElementVisible(contentPageTemplate.get(templateIndex)); 
		contentPageTemplate.get(templateIndex).click();
		Thread.sleep(3000);
		if(nextButton.isEnabled()){
			nextButton.click();
		}
		Thread.sleep(1000);
		FunctionUtil.waitUtilElementVisible(titleInput);
		FunctionUtil.input(titleInput, titleContent);
		FunctionUtil.input(pageNameInput, pageNameContent);		
		FunctionUtil.input(desInput, desContent);
		Thread.sleep(10000);
//		pageManager.customAssertion.assertTrue(createPageButton.get(1).isEnabled(), "Create Page Button should be enabled");
		Thread.sleep(10000);
		if(createPageButton.isEnabled()){
			pageManager.click(createPageButton);
		}
		
		Thread.sleep(6000);
		if(donePageButton.isDisplayed()){
			donePageButton.click();
		}
	}

//	@FindBy(xpath="//coral-card-info[contains(@data-asset-resource, 'globe-news-wire.xml')]")
//	public WebElement xmlFile;
	@FindBy(xpath="//coral-card-info")
	public WebElement xmlFile;

	@FindBy(xpath="//coral-button-label[contains(text(), 'Download')]")
	public WebElement downlaodLink;

	@FindBy(xpath="//coral-dialog-footer//coral-button-label[contains(text(), 'Download')]")
	public WebElement downlaodButton;

	@FindBy(xpath="//input[@id='dam-asset-download-title']")
	public WebElement fileNameText;

	@FindBy(xpath="//coral-anchorbutton-label[contains(text(), 'Close')]")
	public WebElement closeLink;

	//coral-button-label[contains(text(), 'Create')]


	@FindBy(xpath="//coral-list-item-content[contains(text(), 'Files')]")
	public WebElement FileMenu;

	@FindBy(xpath="//coral-button-label[contains(text(), 'Upload')]")
	public WebElement UploadButton;

	@FindBy(xpath="//coral-button-label[contains(text(), 'Replace')]")
	public WebElement ReplaceButton;

	@FindBy(xpath="//coral-button-label[contains(text(), 'Publish')]")
	public WebElement PublishIcon;

	@FindBy(xpath="//coral-list-item-content[contains(text(), 'Publish')]")
	public WebElement Publishmenu;
	
	public void createTemplate(String templateName, String titleContent, String nameContent, String desContent, String successButton) throws Exception{
		StaleElementUtils.refreshElement(createButton);
		FindElementWait(createButton,timeout);
		createButton.click();
		FindElementWait(popList);
		pageCreate.click();
		WebElement template = pageManager.findElement("//coral-card-title[contains(text(), '"+templateName+"')]");
		FindElementWait(template, timeout);
		pageManager.click(template);
		FindElementWait(nextButton, timeout);
		StaleElementUtils.refreshElement(nextButton);
		if(nextButton.isEnabled()){
			nextButton.click();
		}
		StaleElementUtils.refreshElement(titleInput);
		FindElementWait(titleInput, timeout);
		titleText = StaleElementUtils.refreshElement(titleText);
		FunctionUtil.input(titleInput, titleContent);
		nameText = StaleElementUtils.refreshElement(nameText);
		if (nameContent != null){
			FindElementWait(nameText, timeout);
			FunctionUtil.input(nameText, nameContent);
		}
		desInput = StaleElementUtils.refreshElement(desInput);
		if (desContent != null)
		{
			//((JavascriptExecutor) pageManager.getDriver()).executeScript("let textarea = document.evaluate(\"//label[contains(text(),'Description *')]/following-sibling::textarea\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\n"+
			//		"textarea.value = '"+desContent+"';\n" );
			desInput.sendKeys(desContent);
			desInput.sendKeys(Keys.ENTER);
		}
		CreateButton = StaleElementUtils.refreshElement(CreateButton);
		FindElementWait(CreateButton, timeout);
		if(CreateButton.isEnabled()){
			CreateButton.click();
		}
		if (successButton.equalsIgnoreCase("Open"))
		{
			FindElementWait(openButton, 10);
			openButton.click();
		}
		else if (successButton.equalsIgnoreCase("Done"))
		{
			FindElementWait(DoneButton, 10);
			DoneButton.click();
			pageManager.scrollToViewElement(pageManager.getDriver().findElement(By.xpath("//div[@title='"+titleContent+"']")), titleContent+" page select");
			WebElement title = pageManager.findElement("//div[@title='"+titleContent+"']");
			FindElementWait(title, 10);
			pageManager.click(title);
		}

	}

}
