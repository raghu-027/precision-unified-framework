Precision Unified Automation Framework
Complete Technical Documentation
Common  |  UI Module  |  API Module  |  Hybrid Module
Application Under Test: https://automationexercise.com

1. Common Module
The common module is the shared foundation of the entire framework. All other modules (ui-module, api-module, hybrid-module) depend on it. It contains centralized configuration, browser management, test data utilities, logging, reporting, and screenshot capture.

1.1 Module Structure
common/
  pom.xml
  src/main/java/com/precision/common/
    config/
      ConfigReader.java
    driver/
      DriverManager.java
    logger/
      LogManager.java
    reports/
      ExtentReportManager.java
      ExtentTestManager.java
    utils/
      ExcelUtils.java
      WaitUtils.java
      ScreenshotUtils.java
  src/main/resources/
    config.properties
    log4j2.xml
    testdata/
      testdata.xlsx

1.2 ConfigReader.java
Package: com.precision.common.config
Reads all configuration values from config.properties using the classpath-based loading strategy. All modules call ConfigReader.get(key) to fetch values — ensuring zero hardcoding across the entire framework.

config.properties Keys:
Key	Value	Used By
base.url	https://automationexercise.com	All modules
browser	chrome	DriverManager
explicit.wait	10	WaitUtils
short.wait	5	WaitUtils
page.load.wait	20	WaitUtils
report.path	target/extent-reports/AutomationReport.html	ExtentReportManager
screenshot.path	target/screenshots/	ScreenshotUtils

1.3 DriverManager.java
Package: com.precision.common.driver
Manages browser lifecycle using ThreadLocal<WebDriver> to ensure parallel test execution safety. Each test thread gets its own WebDriver instance with no sharing between threads.

Key Features:
•	ThreadLocal<WebDriver> — parallel execution safe
•	Chrome ad blocking via --host-resolver-rules at DNS level
•	Password save popup disabled via Chrome preferences
•	Supports Chrome, Firefox, and Edge browsers
•	Browser configured via config.properties — no hardcoding

Chrome Ad Blocking Configuration:
options.addArguments("--host-resolver-rules=" +
    "MAP doubleclick.net 0.0.0.0," +
    "MAP googleads.g.doubleclick.net 0.0.0.0," +
    "MAP pagead2.googlesyndication.com 0.0.0.0"

Chrome Preferences — Disable Popups:
prefs.put("credentials_enable_service", false);
prefs.put("profile.password_manager_enabled", false);
prefs.put("profile.default_content_setting_values.notifications", 2);

1.4 ExcelUtils.java
Package: com.precision.common.utils
Reads test data from testdata.xlsx using Apache POI. Uses getClassLoader().getResourceAsStream() for classpath-based loading that works consistently in both IntelliJ and Maven execution.

Method:
ExcelUtils.getTestData(String sheetName, String testCase)
// Returns Map<String, String> of column headers to cell values

Excel Sheets:
Sheet	Test Cases	Columns
LoginData	TC2_ValidLogin, TC3_InvalidLogin	testCase, name, email, password, expectedResult
RegisterData	TC1_RegisterUser	testCase, title, name, email, password, day, month, year, firstName, lastName, address, country, state, city, zipcode, mobile
CartData	TC4_AddToCart, TC5_RemoveFromCart	testCase, productIndex, expectedProductName, expectedQuantity

1.5 WaitUtils.java
Package: com.precision.common.utils
Provides explicit wait strategies reading timeout values from config.properties. Avoids hardcoded wait times across the framework.

Method	Timeout Key	Use Case
waitForVisibility(element)	explicit.wait (10s)	Normal element interactions
waitForClickability(element)	explicit.wait (10s)	Button and link clicks
waitForInvisibility(element)	explicit.wait (10s)	Loader and modal dismissal
waitForVisibilityShort(element)	short.wait (5s)	Error messages, alerts
waitForVisibilityLong(element)	page.load.wait (20s)	Slow page loads

1.6 ExtentReportManager.java & ExtentTestManager.java
Package: com.precision.common.reports
Manages the Extent HTML report lifecycle. ExtentReportManager initializes and flushes the report. ExtentTestManager uses ThreadLocal<ExtentTest> to maintain one test instance per thread — required for parallel execution.

•	Report generated at: target/extent-reports/AutomationReport.html
•	Screenshots embedded as base64 directly in HTML — no broken image paths
•	Pass/Fail status logged per scenario with timestamps
•	Thread-safe via ThreadLocal — works in parallel execution

1.7 ScreenshotUtils.java
Package: com.precision.common.utils
Captures browser screenshots on test failure. Saves PNG to disk and also encodes as base64 for embedding directly into the Extent HTML report.

// Base64 encoding for report embedding
String base64 = ((TakesScreenshot) DriverManager.getDriver())
    .getScreenshotAs(OutputType.BASE64);
ExtentTestManager.attachScreenshotBase64(base64);

1.8 LogManager.java
Package: com.precision.common.logger
Wraps Log4j2 to provide a consistent logger across all modules. Each class gets its own named logger via LogManager.getLogger(ClassName.class). Logs are written to both console and file (logs/test.log) as configured in log4j2.xml.


 
2. UI Module
The ui-module implements all Selenium WebDriver UI automation using the Page Object Model pattern combined with Cucumber BDD. It depends on the common module for utilities, configuration, and reporting.

2.1 Module Structure
ui-module/
  src/main/java/com/precision/ui/
    pages/
      BasePage.java
      HomePage.java
      LoginPage.java
      RegisterPage.java
      ProductPage.java
      CartPage.java
    hooks/
      Hooks.java
  src/test/java/com/precision/ui/
    stepdefs/
      CommonSteps.java
      LoginSteps.java
      RegisterSteps.java
      CartSteps.java
    runners/
      MasterTestRunner.java
      RegisterTestRunner.java
      LoginTestRunner.java
      CartTestRunner.java
  src/test/resources/
    features/
      register.feature
      login.feature
      cart.feature
    testng-ui.xml

2.2 Page Object Model
Key Rule: No Selenium commands inside Step Definitions. All browser interactions happen only inside Page classes.

BasePage.java
Abstract base class extended by all page classes. Provides reusable browser interaction methods using explicit waits from WaitUtils.

Method	Description
click(WebElement)	Waits for clickability then clicks
clearAndType(WebElement, String)	Clears field and types text
getText(WebElement)	Waits for visibility then returns text
waitForVisibility(WebElement)	Delegates to WaitUtils.waitForVisibility
waitForClickability(WebElement)	Delegates to WaitUtils.waitForClickability
scrollToElement(WebElement)	JavaScript scroll into view
jsClick(WebElement)	JavaScript click — bypasses ad overlays
dismissAdsIfPresent()	Removes ad elements via JavaScript
waitForPageLoad()	Waits for document.readyState = complete

Page Classes Overview
Page Class	Locators	Key Methods
HomePage	signupLoginBtn, cartBtn, loggedInUsername, logoutBtn	navigateToLoginPage(), navigateToCartPage(), navigateToProductsPage(), isUserLoggedIn()
LoginPage	emailField, passwordField, loginButton, errorMessage	loginAs(), enterEmail(), enterPassword(), clickLoginButtonExpectingFailure(), isErrorMessageDisplayed()
RegisterPage	signupNameField, signupEmailField, signupButton, all form fields	enterSignupName(), enterSignupEmail(), clickSignupButton(), fillRegistrationForm(Map), clickCreateAccount(), isAccountCreated(), clickContinue()
ProductPage	productNames, productCards, addToCartButtons, modalTitle, continueShoppingBtn	addProductToCart(index), getProductName(index), clickContinueShopping(), clickViewCart()
CartPage	cartHeading, cartItems, deleteButtons, emptyCartMessage, itemQuantities	isCartPageLoaded(), getCartItemCount(), removeItemFromCart(index), isCartEmpty(), getItemQuantity(index)

2.3 Hooks.java
Package: com.precision.ui.hooks
Cucumber @Before and @After hooks that manage browser lifecycle and reporting for each scenario.

@Before — setUp():
•	Creates Extent test instance for the scenario
•	Initializes WebDriver via DriverManager.initDriver()
•	Logs scenario start to Extent report

@After — tearDown():
•	On failure: captures base64 screenshot and logs to Extent report
•	On pass: logs pass status to Extent report
•	Always: quits driver, removes ThreadLocal, flushes report

2.4 Step Definitions
Key Rule: Step definitions only call Page Object methods. No direct Selenium usage.

Step Class	Owns Steps For	Shared State
CommonSteps	Given/When shared steps (launch app, click signup/login, verify logged in)	Holds homePage and loginPage as public fields for injection
LoginSteps	TC2 valid login, TC3 invalid login, error message verification	Receives CommonSteps via PicoContainer constructor injection
RegisterSteps	TC1 full registration flow including form fill and account creation	Receives CommonSteps via PicoContainer constructor injection
CartSteps	TC4 add to cart, TC5 remove from cart, cart page verification	Receives CommonSteps via PicoContainer constructor injection

2.5 Test Runners
Runner	Features	Tags	Parallel
MasterTestRunner	register.feature, login.feature, cart.feature	@regression	false
RegisterTestRunner	register.feature	@register	false
LoginTestRunner	login.feature	@login	false
CartTestRunner	cart.feature	@cart	false

2.6 UI Test Cases
TC ID	Scenario	Feature File	Steps
TC1	Register New User and validate	register.feature	Launch → Signup/Login → Enter name/email → Signup → Fill form → Create → Verify account created → Continue → Verify logged in
TC2	Valid Login	login.feature	Launch → Signup/Login → Enter valid credentials → Login → Verify logged in as username
TC3	Invalid Login	login.feature	Launch → Signup/Login → Enter invalid credentials → Login → Verify error message displayed
TC4	Add Product to Cart and validate	cart.feature	Login → Navigate to Products → Add first product → Continue shopping → View Cart → Verify product in cart
TC5	Remove Item from Cart and validate	cart.feature	Login → Navigate to Products → Add product → View Cart → Remove item → Verify cart is empty


 
3. API Module
The api-module implements REST API automation using RestAssured following a layered Client → RequestBuilder → Validator design pattern. It validates HTTP responses including status codes and JSON schemas. All API tests use Cucumber BDD with feature files.

3.1 Module Structure
api-module/
  pom.xml
  src/main/java/com/precision/api/
    client/
      ApiClient.java
    endpoints/
      Endpoints.java
    requestbuilder/
      ProductRequestBuilder.java
      UserRequestBuilder.java
      SearchRequestBuilder.java
    validator/
      ResponseValidator.java
      SchemaValidator.java
  src/test/java/com/precision/api/
    stepdefs/
      ProductApiSteps.java
      UserApiSteps.java
    runners/
      ApiTestRunner.java
      RegressionApiRunner.java
  src/test/resources/
    features/
      products_api.feature
      user_api.feature
    schemas/
      products_schema.json
      user_schema.json
    testng-api.xml

3.2 Layered API Design
Pattern: ApiClient → RequestBuilder → Validator — strict separation of REST setup, request construction, and response validation.

Layer 1 — ApiClient.java
Package: com.precision.api.client
Base RestAssured configuration. Sets the base URI from config.properties and defines common request specifications reused by all request builders.

protected static RequestSpecification getBaseSpec() {
    return RestAssured.given()
        .baseUri(ConfigReader.get("api.base.url"))
        .contentType(ContentType.URLENC)
        .log().all();
}

Layer 2 — Request Builders
Package: com.precision.api.requestbuilder
Each request builder class handles one API domain. Methods construct and send requests using the base spec from ApiClient.

ProductRequestBuilder.java:
Method	HTTP Method	Endpoint	Parameters
getAllProducts()	GET	/api/productsList	None
searchProduct(String name)	POST	/api/searchProduct	search_product form param

UserRequestBuilder.java:
Method	HTTP Method	Endpoint	Parameters
createAccount(Map data)	POST	/api/createAccount	User form params from map
deleteAccount(String email, String password)	DELETE	/api/deleteAccount	email, password
updateAccount(Map data)	PUT	/api/updateAccount	User form params from map

Layer 3 — Validators
Package: com.precision.api.validator
ResponseValidator.java:
•	validateStatusCode(Response, int expected) — asserts HTTP status code
•	validateResponseContains(Response, String key, Object value) — asserts JSON field value
•	validateResponseTime(Response, long maxMs) — asserts response time within limit
•	validateResponseNotEmpty(Response) — asserts response body is not empty

SchemaValidator.java:
•	validateSchema(Response, String schemaPath) — validates response against JSON schema file
•	Schema files stored in src/test/resources/schemas/
•	Uses RestAssured JsonSchemaValidator.matchesJsonSchemaInClasspath()

3.3 Endpoints.java
Package: com.precision.api.endpoints
Centralizes all API endpoint constants. No endpoint strings are hardcoded in test classes.

Constant	Value	Used For
PRODUCTS_LIST	/api/productsList	Get all products
SEARCH_PRODUCT	/api/searchProduct	Search product by name
CREATE_ACCOUNT	/api/createAccount	Register new user
DELETE_ACCOUNT	/api/deleteAccount	Delete user account
UPDATE_ACCOUNT	/api/updateAccount	Update user account details

3.4 JSON Schema Files
Location: src/test/resources/schemas/
JSON schema files define the expected structure of API responses. Every API test validates the response against its schema to ensure response integrity.

products_schema.json — validates GET /api/productsList response:
{
  "type": "object",
  "properties": {
    "responseCode": { "type": "integer" },
    "products": {
      "type": "array",
      "items": {
        "type": "object",
        "required": ["id", "name", "price", "brand", "category"]
      }
    }
  }
}

3.5 API Test Cases
TC ID	Scenario	Method	Endpoint	Validation
API-TC1	Get All Products List	GET	/api/productsList	Status 200, schema validation, non-empty product list
API-TC2	Search Product by name	POST	/api/searchProduct	Status 200, products array contains searched item
API-TC3	Create User Account	POST	/api/createAccount	Status 201, responseCode = 201, account created message
API-TC4	Delete User Account	DELETE	/api/deleteAccount	Status 200, responseCode = 200, account deleted message
API-TC5	Update User Account	PUT	/api/updateAccount	Status 200, responseCode = 200, account updated message
API-TC6	Negative Validation	POST	/api/searchProduct	Status 400 or empty results for invalid search term

3.6 API Feature Files
products_api.feature:
Feature: Products API

  Scenario: TC1 - Get all products list
    When I send GET request to products list
    Then response status code should be 200
    And response should match products schema
    And products list should not be empty

  Scenario: TC2 - Search product
    When I send POST request to search product with name "top"
    Then response status code should be 200
    And response should contain products matching search term

user_api.feature:
Feature: User API

  Scenario: TC3 - Create user account
    When I send POST request to create account with valid user data
    Then response status code should be 201
    And response message should be "User created!"

  Scenario: TC4 - Delete user account
    When I send DELETE request to delete account
    Then response status code should be 200
    And response message should be "Account deleted!"

3.7 Running API Tests
# Run all API tests
mvn clean test -pl api-module

# Run with specific tag
mvn test -pl api-module -Dcucumber.filter.tags="@api"

# Run via testng-api.xml
mvn test -pl api-module -DsuiteXmlFile=src/test/resources/testng-api.xml


 
4. Hybrid Module
The hybrid-module combines UI automation (Selenium) and API automation (RestAssured) within single Cucumber BDD scenarios. It demonstrates cross-layer validation — using the API to set up state and the UI to verify it, or vice versa.

Dependency: hybrid-module depends on both ui-module and api-module — inheriting all pages, step definitions, request builders, and validators.

4.1 Module Structure
hybrid-module/
  pom.xml
  src/test/java/com/precision/hybrid/
    stepdefs/
      HybridSteps.java
    runners/
      HybridTestRunner.java
  src/test/resources/
    features/
      hybrid_register_flow.feature
      hybrid_cart_flow.feature
    testng-hybrid.xml

4.2 Hybrid Design Pattern
Cross-Layer Validation: API creates the state → UI verifies it. Or UI performs the action → API validates the result.

Two Hybrid Flows:
Flow 1 — API Setup + UI Verification:
•	Step 1: Create user account via API (POST /api/createAccount)
•	Step 2: Verify API response — status 201, account created message
•	Step 3: Open browser and login with the new credentials via UI
•	Step 4: Verify user is logged in successfully on the homepage

Flow 2 — UI Action + API Verification:
•	Step 1: Login via UI and add product to cart
•	Step 2: Verify cart state via UI
•	Step 3: Call API to get product details and cross-validate
•	Step 4: Clean up — delete test account via API

4.3 HybridSteps.java
Package: com.precision.hybrid.stepdefs
Hybrid step definitions combine both UI page calls and API request builder calls within the same class. They use RestAssured for API calls and Selenium page objects for UI actions.

public class HybridSteps {

    private UserRequestBuilder userApi = new UserRequestBuilder();
    private Response apiResponse;
    private String registeredEmail;

    @Given("I create a new user account via API")
    public void createUserViaApi() {
        registeredEmail = "hybrid_" + System.currentTimeMillis() + "@test.com";
        Map<String, String> userData = buildUserData(registeredEmail);
        apiResponse = userApi.createAccount(userData);
    }

    @Then("the API response should confirm account creation")
    public void verifyApiResponse() {
        ResponseValidator.validateStatusCode(apiResponse, 201);
        ResponseValidator.validateResponseContains(apiResponse, "message", "User created!");
    }

    @When("I open the browser and login with the new credentials")
    public void loginViaUI() {
        DriverManager.initDriver();
        HomePage homePage = new HomePage();
        homePage.navigateToBaseUrl();
        LoginPage loginPage = homePage.navigateToLoginPage();
        loginPage.loginAs(registeredEmail, "Test@1234");
    }

    @Then("I should be successfully logged in on the UI")
    public void verifyUILogin() {
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isUserLoggedIn());
        DriverManager.quitDriver();
    }
}

4.4 Hybrid Feature Files
hybrid_register_flow.feature:
Feature: Hybrid Registration and Login Flow

  @hybrid @regression
  Scenario: HC1 - Register via API then verify login via UI
    Given I create a new user account via API
    Then the API response should confirm account creation
    When I open the browser and login with the new credentials
    Then I should be successfully logged in on the UI

  @hybrid @regression
  Scenario: HC2 - Verify API and UI data consistency
    Given I create a new user account via API
    And the API response should confirm account creation
    When I login via UI with the registered credentials
    And I navigate to account details page
    Then the UI should display the same user data as registered via API

4.5 hybrid-module pom.xml Dependencies
The hybrid module declares dependencies on both ui-module and api-module to access all pages, step defs, request builders, and validators.

<dependencies>
  <dependency>
    <groupId>com.precision</groupId>
    <artifactId>ui-module</artifactId>
    <version>1.0.0</version>
  </dependency>
  <dependency>
    <groupId>com.precision</groupId>
    <artifactId>api-module</artifactId>
    <version>1.0.0</version>
  </dependency>
  <dependency>
    <groupId>com.precision</groupId>
    <artifactId>common</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>

4.6 Hybrid Test Cases
TC ID	Scenario	API Layer	UI Layer	Validation
HC1	Register via API, verify login via UI	POST /api/createAccount	Login + homepage check	API status 201 + UI logged in
HC2	UI action verified by API state	GET /api/productsList	Add to cart via UI	UI cart count matches API product data
HC3	Full lifecycle — Create, Use, Delete	POST createAccount, DELETE deleteAccount	Login and navigate	End-to-end user lifecycle

4.7 Running Hybrid Tests
# Build all modules first (required)
mvn clean install -DskipTests

# Run hybrid tests
mvn test -pl hybrid-module

# Run with hybrid tag
mvn test -pl hybrid-module -Dcucumber.filter.tags="@hybrid"


 
5. Module Dependency Map

                    precision-unified-framework (Parent POM)
                              |
              ________________|___________________________
              |               |            |             |
           common         ui-module    api-module   hybrid-module
         (foundation)    (Selenium)   (RestAssured)  (UI+API)
              |               |            |             |
              |_______________|____________|             |
                              |__________________________|

Dependency Direction:
  common        <- used by all
  ui-module     <- depends on common
  api-module    <- depends on common
  hybrid-module <- depends on ui-module + api-module + common

5.1 Maven Build Order
Always build in this order to avoid stale jar issues.

mvn clean install -pl common -DskipTests   # Step 1: build common
mvn clean install -pl ui-module -DskipTests # Step 2: build ui
mvn clean install -pl api-module -DskipTests# Step 3: build api
mvn test -pl hybrid-module                  # Step 4: run hybrid

# Or build everything at once from root:
mvn clean install -DskipTests

5.2 Complete Execution Order
Phase	Module	Command	What Runs
1	common	mvn clean install -pl common -DskipTests	Builds shared utilities
2	ui-module	mvn clean test -pl ui-module	TC1 → TC2 → TC3 → TC4 → TC5
3	api-module	mvn clean test -pl api-module	API-TC1 → API-TC6
4	hybrid-module	mvn clean test -pl hybrid-module	HC1 → HC2 → HC3
All	root	mvn clean install then mvn test	All phases in order


 
6. Challenges Faced & Solutions
During the development of this framework, 15 real-world exceptions and challenges were encountered and resolved across all modules.

#	Exception	Module	Root Cause	Solution
1	DuplicateStepDefinitionException	ui-module	LoginSteps and RegisterSteps both had same @Then step	Moved shared steps to CommonSteps.java
2	CucumberException — no zero-arg constructor	ui-module	cucumber-picocontainer jar not in classpath	Added picocontainer with compile scope
3	NoSuchMethodError — ExtentTestManager	common	Stale common jar in .m2 repository	mvn clean install -pl common
4	ExceptionInInitializerError — ConfigReader	common	config.properties not in classpath	Placed in src/main/resources, marked as Resources Root
5	NullPointerException — DriverManager.getDriver()	ui-module	Driver not initialized before screenshot	Added null check in Hooks.tearDown()
6	InvalidSelectorException — ProductPage	ui-module	//a[contains(@data-product-id)] invalid xpath	Changed to CSS: a[data-product-id]
7	TimeoutException — cartHeading	ui-module	Wrong locator — no h2 with that text	Changed to //li[contains(text(),'Shopping Cart')]
8	AssertionError — User not logged in	ui-module	Ad overlay blocking login button click	dismissAdsIfPresent() + jsClick() + Chrome DNS blocking
9	AssertionError — Account Created not shown	ui-module	Email already registered on previous run	Unique email via System.currentTimeMillis()
10	AssertionError — Error message not visible	ui-module	Wrong error message xpath locator	Updated to //p[contains(text(),'incorrect')]
11	AssertionError — Cart not empty	ui-module	Wrong emptyCartMessage locator	Changed to //b[contains(text(),'Cart is empty!')]
12	LifecyclePhaseNotFoundException	Maven	PowerShell treating -D parameter incorrectly	Used quotes around -DsuiteXmlFile parameter
13	CompilationError — Hooks.java	ui-module	Missing package declaration on line 1	Added package com.precision.ui.hooks
14	NoSuchMethodError — ExtentTestManager	common	Old jar still in classpath after code change	mvn clean install from root
15	Screenshots broken in report	common	File path not accessible from browser	Embedded screenshots as base64 in HTML report


Precision Unified Automation Framework  —  Complete Technical Documentation  —  Version 1.0.0
Application: https://automationexercise.com  |  Stack: Java 21, Selenium 4.18, Cucumber 7.15, RestAssured 5.4, TestNG 7.9, Maven
