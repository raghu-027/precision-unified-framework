<div align="center">

# 🎯 Precision Unified Automation Framework

### End-to-End Test Automation · UI | API | Hybrid

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Selenium](https://img.shields.io/badge/Selenium-4.18-43B02A?style=for-the-badge&logo=selenium&logoColor=white)](https://selenium.dev)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.15-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)](https://cucumber.io)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.4-4BA3C3?style=for-the-badge)](https://rest-assured.io)
[![TestNG](https://img.shields.io/badge/TestNG-7.9-FF6B35?style=for-the-badge)](https://testng.org)
[![Maven](https://img.shields.io/badge/Maven-Multi--Module-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org)

**Application Under Test:** [automationexercise.com](https://automationexercise.com)

</div>

---

## 📑 Table of Contents

- [Project Overview](#-project-overview)
- [Framework Architecture](#-framework-architecture)
- [Module Dependency Map](#-module-dependency-map)
- [Project Structure](#-project-structure)
- [Tech Stack](#-tech-stack)
- [Common Module](#1️⃣-common-module)
- [UI Module](#2️⃣-ui-module)
- [API Module](#3️⃣-api-module)
- [Hybrid Module](#4️⃣-hybrid-module)
- [Test Cases](#-test-cases)
- [Execution Guide](#-execution-guide)
- [Challenges & Solutions](#-challenges--solutions)
- [Team](#-team)

---

## 🔍 Project Overview

The **Precision Unified Automation Framework** is a production-grade Maven multi-module test automation suite covering three distinct testing layers:

| Layer | Module | Technology | Scope |
|-------|--------|-----------|-------|
| 🌐 **UI** | `ui-module` | Selenium WebDriver 4 + Cucumber | Browser-based end-to-end testing |
| 🔌 **API** | `api-module` | RestAssured + Cucumber | REST API contract & response validation |
| 🔗 **Hybrid** | `hybrid-module` | Selenium + RestAssured combined | Cross-layer validation flows |
| 🏗️ **Foundation** | `common` | Java utilities + ExtentReports | Shared config, driver, reporting |

---

## 🏛️ Framework Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│               precision-unified-framework  (Parent POM)             │
│                     Java 21 · Maven Multi-Module                    │
└──────────────────────────────┬──────────────────────────────────────┘
                               │
          ┌────────────────────┼────────────────────┐
          │                    │                    │
          ▼                    ▼                    ▼
 ┌─────────────────┐  ┌──────────────────┐  ┌──────────────────┐
 │   ui-module     │  │   api-module     │  │  hybrid-module   │
 │                 │  │                  │  │                  │
 │  • BasePage     │  │  • ApiClient     │  │  • HybridSteps   │
 │  • Page Objects │  │  • RequestBldrs  │  │  • HybridRunner  │
 │  • StepDefs     │  │  • Validators    │  │  • Feature Files │
 │  • Hooks        │  │  • StepDefs      │  │                  │
 │  • Runners      │  │  • Runners       │  │  Uses BOTH       │
 │  • Features     │  │  • Features      │  │  ui + api layers │
 └────────┬────────┘  └────────┬─────────┘  └───────┬──────────┘
          │                    │                     │
          └────────────────────┼─────────────────────┘
                               │  all depend on
                               ▼
          ┌────────────────────────────────────────┐
          │              common/                   │
          │                                        │
          │  ConfigReader   ──► config.properties  │
          │  DriverManager  ──► ThreadLocal<Driver>│
          │  ExcelUtils     ──► testdata.xlsx       │
          │  WaitUtils      ──► Explicit Waits      │
          │  ExtentReports  ──► HTML Reports        │
          │  ScreenshotUtils──► Base64 Screenshots  │
          │  LogManager     ──► Log4j2              │
          └────────────────────────────────────────┘
```

---

## 🔗 Module Dependency Map

```
common          ◄── foundation (no dependencies)
   │
   ├──► ui-module      (depends on: common)
   │
   ├──► api-module     (depends on: common)
   │
   └──► hybrid-module  (depends on: common + ui-module + api-module)
```

### Dependency Flow Diagram

```
┌──────────┐     ┌──────────────┐     ┌──────────────┐
│  common  │────▶│  ui-module   │────▶│              │
│          │     └──────────────┘     │   hybrid-    │
│          │                          │   module     │
│          │     ┌──────────────┐     │              │
│          │────▶│  api-module  │────▶│              │
└──────────┘     └──────────────┘     └──────────────┘
```

---

## 📁 Project Structure

```
precision-unified-framework/
│
├── pom.xml                          ← Parent POM (modules declared here)
│
├── common/
│   ├── pom.xml
│   └── src/main/java/com/precision/common/
│       ├── config/
│       │   └── ConfigReader.java
│       ├── driver/
│       │   └── DriverManager.java
│       ├── logger/
│       │   └── LogManager.java
│       ├── reports/
│       │   ├── ExtentReportManager.java
│       │   └── ExtentTestManager.java
│       └── utils/
│           ├── ExcelUtils.java
│           ├── WaitUtils.java
│           └── ScreenshotUtils.java
│
├── ui-module/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/precision/ui/
│       │   ├── pages/
│       │   │   ├── BasePage.java
│       │   │   ├── HomePage.java
│       │   │   ├── LoginPage.java
│       │   │   ├── RegisterPage.java
│       │   │   ├── ProductPage.java
│       │   │   └── CartPage.java
│       │   └── hooks/
│       │       └── Hooks.java
│       └── test/java/com/precision/ui/
│           ├── stepdefs/
│           │   ├── CommonSteps.java
│           │   ├── LoginSteps.java
│           │   ├── RegisterSteps.java
│           │   └── CartSteps.java
│           ├── runners/
│           │   ├── MasterTestRunner.java
│           │   ├── RegisterTestRunner.java
│           │   ├── LoginTestRunner.java
│           │   └── CartTestRunner.java
│           └── resources/features/
│               ├── register.feature
│               ├── login.feature
│               └── cart.feature
│
├── api-module/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/precision/api/
│       │   ├── client/
│       │   │   └── ApiClient.java
│       │   ├── endpoints/
│       │   │   └── Endpoints.java
│       │   ├── builders/
│       │   │   ├── ProductRequestBuilder.java
│       │   │   └── UserRequestBuilder.java
│       │   └── validators/
│       │       ├── ProductValidator.java
│       │       └── UserValidator.java
│       └── test/java/com/precision/api/
│           ├── steps/
│           │   ├── ProductSteps.java
│           │   └── UserSteps.java
│           ├── hooks/
│           │   └── ApiHooks.java
│           ├── runner/
│           │   └── ApiTestRunner.java
│           └── resources/features/
│               ├── get_products.feature
│               ├── search_product.feature
│               ├── create_user.feature
│               ├── update_user.feature
│               ├── delete_user.feature
│               └── negative_validation.feature
│
└── hybrid-module/
    ├── pom.xml
    └── src/test/
        ├── java/com/precision/hybrid/
        │   ├── context/
        │   │   └── HybridContext.java
        │   ├── steps/
        │   │   └── HybridSteps.java
        │   ├── hooks/
        │   │   └── HybridHooks.java
        │   └── runner/
        │       └── HybridTestRunner.java
        └── resources/features/
            └── hybrid_login.feature
```

---

## 🛠️ Tech Stack

| Category | Technology | Version | Purpose |
|----------|-----------|---------|---------|
| Language | Java | 21 | Core programming language |
| Build Tool | Apache Maven | 3.8+ | Multi-module build management |
| UI Automation | Selenium WebDriver | 4.18 | Browser automation |
| API Automation | RestAssured | 5.4 | REST API testing |
| BDD Framework | Cucumber | 7.15 | Gherkin feature files & step defs |
| Test Runner | TestNG | 7.9 | Test execution & assertions |
| Reporting | ExtentReports (Spark) | 5.x | Dark-theme HTML reports |
| Test Data | Apache POI | 5.x | Excel `.xlsx` data reading |
| Logging | Log4j2 | 2.x | Console + file logging |
| DI Framework | PicoContainer | 2.x | Cucumber dependency injection |
| Design Pattern | Page Object Model | — | UI layer abstraction |
| Driver Strategy | ThreadLocal | — | Parallel-safe WebDriver |

---

## 1️⃣ Common Module

> **Foundation of the entire framework. All modules depend on this.**

### ConfigReader.java

Reads all configuration from `config.properties` using classpath-based loading. Zero hardcoded values across the framework.

```java
// Usage anywhere in the framework:
String baseUrl = ConfigReader.get("base.url");
String browser  = ConfigReader.get("browser");
int timeout     = Integer.parseInt(ConfigReader.get("explicit.wait"));
```

#### `config.properties` Keys

| Key | Example Value | Used By |
|-----|-------------|---------|
| `base.url` | `https://automationexercise.com` | All modules |
| `base.api.url` | `https://automationexercise.com` | API module |
| `browser` | `chrome` | DriverManager |
| `explicit.wait` | `10` | WaitUtils |
| `valid.password` | `Test@1234` | API / Hybrid |
| `report.path` | `target/extent-reports/...` | ExtentReportManager |
| `screenshot.path` | `target/screenshots/` | ScreenshotUtils |

---

### DriverManager.java

Thread-safe WebDriver management using `ThreadLocal<WebDriver>`. Supports **Chrome**, **Firefox**, and **Edge**.

```
DriverManager
│
├── initDriver()    ──► creates browser instance per thread
├── getDriver()     ──► returns current thread's WebDriver
└── quitDriver()    ──► quits and removes from ThreadLocal
```

**Chrome Hardening Applied:**

```
✔  --disable-notifications          ✔  --no-sandbox
✔  --disable-popup-blocking         ✔  --disable-dev-shm-usage
✔  --disable-blink-features=AutomationControlled
✔  DNS-level ad blocking (doubleclick.net → 0.0.0.0)
✔  Password manager disabled via Chrome prefs
✔  Ads suppressed via profile.default_content_setting_values
```

---

### ExcelUtils.java

Apache POI-based Excel reader. Returns `Map<String, String>` keyed by column header names.

```java
Map<String, String> data = ExcelUtils.getTestData("LoginData", "TC2_ValidLogin");
// Returns: { "email" -> "user@test.com", "password" -> "Test@1234", ... }
```

#### Excel Sheets Structure

| Sheet | Test Case IDs | Key Columns |
|-------|-------------|-------------|
| `LoginData` | TC2_ValidLogin, TC3_InvalidLogin | email, password |
| `RegisterData` | TC1_RegisterUser | name, title, password, day, month, year, firstName, lastName, address, country, state, city, zipcode, mobile |
| `CartData` | TC4_AddToCart, TC5_RemoveFromCart | productIndex, expectedProductName, expectedQuantity |

---

### WaitUtils.java

Explicit waits delegated from BasePage — timeout values from `config.properties`.

| Method | Timeout Config Key | Use Case |
|--------|-------------------|---------|
| `waitForVisibility(el)` | `explicit.wait` (10s) | Standard element checks |
| `waitForClickability(el)` | `explicit.wait` (10s) | Button / link clicks |
| `waitForInvisibility(el)` | `explicit.wait` (10s) | Loader / modal dismissal |

---

### ExtentReports Setup

```
ExtentReportManager (Singleton)
│
├── getInstance()         ──► lazy-init ExtentReports
├── createInstance()      ──► Spark reporter, dark theme, system info
└── flushReports()        ──► writes HTML file to disk

ExtentTestManager (ThreadLocal)
│
├── createTest(name)      ──► new test node per scenario
├── getTest()             ──► current thread's ExtentTest
├── attachScreenshotBase64(b64) ──► embeds screenshot in HTML
└── removeTest()          ──► cleans up ThreadLocal
```

> 📄 **Report Location:** `target/extent-reports/AutomationReport.html`

---

## 2️⃣ UI Module

> **Selenium WebDriver automation using Page Object Model + Cucumber BDD**

### Page Object Model Architecture

```
                     BasePage (abstract)
                          │
          ┌───────────────┼──────────────────┐
          │               │                  │
     HomePage        LoginPage          RegisterPage
          │
    ┌─────┴───────┐
    │             │
ProductPage    CartPage
```

**Rule:** _No Selenium commands inside Step Definitions. All browser interactions live only inside Page classes._

---

### BasePage.java — Utility Methods

| Category | Method | Description |
|----------|--------|-------------|
| **Navigation** | `navigateToBaseUrl()` | Opens base URL from config |
| | `navigateTo(path)` | Appends path to base URL |
| | `waitForPageLoad()` | Waits for `document.readyState == complete` |
| | `waitForUrlToChange(part)` | Waits for URL redirect |
| **Elements** | `click(el)` | Wait for clickability → click |
| | `clearAndType(el, text)` | Clear field → type text |
| | `getText(el)` | Wait for visibility → get text |
| **JavaScript** | `scrollToElement(el)` | JS scroll into view |
| | `jsClick(el)` | JS click — bypasses overlays |
| | `dismissAdsIfPresent()` | JS removes ad elements |
| **Screenshot** | `takeScreenshot(name)` | Saves PNG to disk |

---

### Page Classes

#### HomePage.java
```
Locators:  homeCarousel · signupLoginBtn · cartBtn · logoutBtn · loggedInUsername
Methods:   navigateToLoginPage() → LoginPage
           navigateToCartPage()  → CartPage
           navigateToProductsPage() → ProductPage
           isUserLoggedIn() → boolean
           getLoggedInUsername() → String
```

#### LoginPage.java
```
Locators:  emailField · passwordField · loginButton · errorMessage
Methods:   loginAs(email, password) → HomePage    [3-level fallback click]
           enterEmail(email) → LoginPage           [fluent]
           enterPassword(pwd) → LoginPage          [fluent]
           clickLoginButtonExpectingFailure()       [for negative tests]
           isErrorMessageDisplayed() → boolean
```

**Login Click Fallback Chain:**
```
1. waitForClickability(loginButton).click()   ← standard
        ↓ (if fails)
2. jsClick(loginButton)                       ← JavaScript click
        ↓ (if fails)
3. Actions.moveToElement(btn).click().perform() ← mouse simulation
```

#### RegisterPage.java
```
Locators:  signupNameField · signupEmailField · signupButton
           titleMr/Mrs · passwordField · dobDay/Month/Year
           firstName · lastName · address1 · country · state · city · zipcode · mobile
           createAccountBtn · accountCreatedHeading · continueBtn

Key Methods:
  fillRegistrationForm(Map<String,String>) ← orchestrates all field setters
  clickCreateAccount()  ← ad dismiss + scroll + JS click
  isAccountCreated()    ← checks 'Account Created!' heading
  clickContinue()       → HomePage
```

#### ProductPage.java
```
Locators:  productNames (List) · productCards (List) · addToCartButtons (List)
           modalTitle · continueShoppingBtn · viewCartBtn

Methods:   addProductToCart(index)   ← dismissAds → scroll → jsClick → wait modal
           getProductName(index)     → String
           clickContinueShopping()
           clickViewCart()           → CartPage
```

#### CartPage.java
```
Locators:  cartHeading · cartItems (List) · deleteButtons (List)
           emptyCartMessage · itemPrices (List) · itemQuantities (List)

Methods:   isCartPageLoaded()      → boolean
           getCartItemCount()      → int
           isCartEmpty()          → boolean
           removeItemFromCart(i)   ← click delete → waitForInvisibility
           getItemPrice(i)        → String
           getItemQuantity(i)     → String
```

---

### Hooks.java — Lifecycle

```
@Before (each scenario)
  │
  ├── ExtentTestManager.createTest(scenario.getName())
  └── DriverManager.initDriver()

@After (each scenario)
  │
  ├── [if FAILED] → capture Base64 screenshot → attach to ExtentReport
  ├── [if PASSED] → mark pass in ExtentReport
  └── [always]    → quitDriver() → removeTest() → flushReports()
```

---

### Step Definitions

| Class | Owns Steps For | Injection |
|-------|---------------|-----------|
| `CommonSteps` | Launch app, click signup/login, verify logged in | Holds `homePage` & `loginPage` as shared public fields |
| `LoginSteps` | TC2 valid login, TC3 invalid login | Receives `CommonSteps` via PicoContainer |
| `RegisterSteps` | TC1 full registration flow | Receives `CommonSteps` via PicoContainer |
| `CartSteps` | TC4 add to cart, TC5 remove from cart | Receives `CommonSteps` via PicoContainer |

---

### Test Runners

| Runner | Feature File | Tag | Use |
|--------|-------------|-----|-----|
| `MasterTestRunner` | all 3 features | `@regression` | Full UI regression |
| `RegisterTestRunner` | `register.feature` | `@register` | Registration only |
| `LoginTestRunner` | `login.feature` | `@login` | Login only |
| `CartTestRunner` | `cart.feature` | `@cart` | Cart only |

---

## 3️⃣ API Module

> **REST API automation using RestAssured — layered Client → Builder → Validator pattern**

### API Layer Architecture

```
┌─────────────────────────────────────────────────────┐
│                   Feature Files (.feature)           │
│         (Gherkin BDD scenarios — 6 files)           │
└────────────────────────┬────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────┐
│              Step Definitions (Glue Layer)           │
│         ProductSteps.java · UserSteps.java           │
└────────────────────────┬────────────────────────────┘
                         │
          ┌──────────────┼──────────────┐
          ▼              ▼              ▼
  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
  │  Layer 1     │ │  Layer 2     │ │  Layer 3     │
  │  ApiClient   │ │  Request     │ │  Validators  │
  │              │ │  Builders    │ │              │
  │ • Base URI   │ │ • Product    │ │ • Product    │
  │ • ContentType│ │   Builder    │ │   Validator  │
  │ • Accept hdr │ │ • User       │ │ • User       │
  │ • Singleton  │ │   Builder    │ │   Validator  │
  └──────────────┘ └──────────────┘ └──────────────┘
```

---

### ApiClient.java

```java
// Singleton RequestSpecification — shared by all builders
RequestSpecification spec = new RequestSpecBuilder()
    .setBaseUri(ConfigReader.get("base.api.url"))
    .setContentType(ContentType.URLENC)
    .setAccept(ContentType.JSON)
    .build();
```

---

### Request Builders

#### ProductRequestBuilder.java

| Method | HTTP | Endpoint | Parameters |
|--------|------|----------|-----------|
| `getAllProducts()` | GET | `/api/productsList` | None |
| `searchProduct(name)` | POST | `/api/searchProduct` | `search_product` form param |
| `searchProductWithNoParam()` | POST | `/api/searchProduct` | None (negative test) |

#### UserRequestBuilder.java

| Method | HTTP | Endpoint | Parameters |
|--------|------|----------|-----------|
| `createUser(email, password)` | POST | `/api/createAccount` | All user fields from config |
| `updateUser(email, password)` | PUT | `/api/updateAccount` | Updated name/firstname/lastname |
| `deleteUser(email, password)` | DELETE | `/api/deleteAccount` | email, password |

---

### Validators

#### ProductValidator.java

| Method | Validates |
|--------|-----------|
| `validateStatusCode(response)` | `responseCode == 200` |
| `validateProductsList(response)` | products array is non-null and non-empty |
| `validateSearchResults(response)` | search results array non-empty |
| `validateMissingParameter(response)` | `responseCode == 400` + exact error message |

#### UserValidator.java

| Method | Expected Code | Expected Message |
|--------|-------------|-----------------|
| `validateUserCreated(response)` | `201` | `"User created!"` |
| `validateUserUpdated(response)` | `200` | `"User updated!"` |
| `validateUserDeleted(response)` | `200` | `"Account deleted!"` |

---

### API Endpoints Reference

| Constant | Endpoint | Method |
|---------|----------|--------|
| `PRODUCTS_LIST` | `/api/productsList` | GET |
| `SEARCH_PRODUCT` | `/api/searchProduct` | POST |
| `CREATE_ACCOUNT` | `/api/createAccount` | POST |
| `UPDATE_ACCOUNT` | `/api/updateAccount` | PUT |
| `DELETE_ACCOUNT` | `/api/deleteAccount` | DELETE |

---

### UserSteps — Shared State Management

```
UserSteps (static class-level fields)
│
├── sharedEmail    ──► UUID-generated per test run
├── sharedPassword ──► loaded from config.properties
└── userCreated    ──► boolean guard flag

Flow:
  createUser() ──sets──► sharedEmail, sharedPassword, userCreated=true
  updateUser() ──reads──► sharedEmail, sharedPassword (guards: userCreated must be true)
  deleteUser() ──reads──► sharedEmail, sharedPassword (sets userCreated=false)
```

---

## 4️⃣ Hybrid Module

> **Cross-layer validation — API sets up state, UI verifies it (or vice versa)**

### Hybrid Flow Patterns

```
Flow 1: API Setup → UI Verification
┌─────────────────────────────────────────────────────┐
│  Step 1: POST /api/createAccount  (API Layer)        │
│  Step 2: Assert status 200 + "User created!"         │
│  Step 3: Open browser, navigate, login (UI Layer)    │
│  Step 4: Assert "Logged in as" username visible      │
└─────────────────────────────────────────────────────┘

Flow 2: Invalid credential cross-layer test
┌─────────────────────────────────────────────────────┐
│  Step 1: POST /api/createAccount  (API Layer)        │
│  Step 2: Login with correct email + WRONG password   │
│  Step 3: Assert error message displayed on UI        │
└─────────────────────────────────────────────────────┘

Flow 3: API product list → UI products page
┌─────────────────────────────────────────────────────┐
│  Step 1: GET /api/productsList  (API Layer)          │
│  Step 2: Assert non-empty product list               │
│  Step 3: Navigate to products page (UI Layer)        │
│  Step 4: Assert product cards visible on UI          │
└─────────────────────────────────────────────────────┘

Flow 4: Full lifecycle — Create → Use → Delete
┌─────────────────────────────────────────────────────┐
│  Step 1: POST /api/createAccount  (API Layer)        │
│  Step 2: Login via UI + verify success               │
│  Step 3: DELETE /api/deleteAccount (API Layer)       │
│  Step 4: Assert account confirmed deleted            │
└─────────────────────────────────────────────────────┘
```

---

### HybridContext.java — Shared State Container

```java
public class HybridContext {
    private String email;           // set in @Given, read in all steps
    private String password;        // set in @Given, read in all steps
    private Response createUserResponse;
    private Response deleteUserResponse;
    private Response productsResponse;
}
```

Injected via PicoContainer into `HybridSteps` — avoids static field sharing.

---

### Hybrid Module pom.xml Dependencies

```xml
<dependencies>
  <dependency>
    <groupId>com.precision</groupId>
    <artifactId>common</artifactId>
    <version>1.0.0</version>
  </dependency>
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
</dependencies>
```

---

## 📋 Test Cases

### UI Test Cases

| TC ID | Scenario | Feature File | Covers |
|-------|---------|-------------|--------|
| **TC1** | Register New User | `register.feature` | Signup form → full registration form → account created |
| **TC2** | Valid Login | `login.feature` | Login with correct credentials → verify username visible |
| **TC3** | Invalid Login | `login.feature` | Login with wrong credentials → verify error message |
| **TC4** | Add Product to Cart | `cart.feature` | Navigate products → add item → verify in cart with quantity |
| **TC5** | Remove Item from Cart | `cart.feature` | Remove cart item → verify cart is empty |

### API Test Cases

| TC ID | Scenario | Method | Endpoint | Validation |
|-------|---------|--------|----------|-----------|
| **API-TC1** | Get All Products | GET | `/api/productsList` | Status 200 + non-empty list |
| **API-TC2** | Search Product | POST | `/api/searchProduct` | Status 200 + results returned |
| **API-TC3** | Create User | POST | `/api/createAccount` | responseCode 201 + `"User created!"` |
| **API-TC4** | Delete User | DELETE | `/api/deleteAccount` | responseCode 200 + `"Account deleted!"` |
| **API-TC5** | Update User | PUT | `/api/updateAccount` | responseCode 200 + `"User updated!"` |
| **API-TC6** | Negative — Missing Param | POST | `/api/searchProduct` | responseCode 400 + error message |

### Hybrid Test Cases

| TC ID | Scenario | API Layer | UI Layer | Validation |
|-------|---------|-----------|---------|-----------|
| **HC1** | API create → UI login (valid) | POST createAccount | Login + homepage | Status 200 + logged in visible |
| **HC2** | API create → UI login (invalid pwd) | POST createAccount | Login with wrong pwd | Error message displayed |
| **HC3** | API products → UI products page | GET productsList | Navigate products | API non-empty + UI cards visible |
| **HC4** | Full lifecycle — Create, Login, Delete | POST + DELETE | Login + verify | End-to-end user lifecycle |

**Total: 15 Test Scenarios across 3 modules**

---

## 🚀 Execution Guide

### Prerequisites

```
✔  Java 21+
✔  Maven 3.8+
✔  Chrome / Firefox / Edge installed
✔  config.properties configured
✔  testdata.xlsx in src/main/resources/testdata/
```

### Build Order

```bash
# Step 1 — Build the common module first (always required)
mvn clean install -pl common -DskipTests

# Step 2 — Build UI module
mvn clean install -pl ui-module -DskipTests

# Step 3 — Build API module
mvn clean install -pl api-module -DskipTests

# Or build everything at once from root:
mvn clean install -DskipTests
```

### Run Commands

```bash
# ── UI Module ─────────────────────────────────────────
# Run all UI tests
mvn clean test -pl ui-module

# Run specific feature by tag
mvn test -pl ui-module -Dcucumber.filter.tags="@register"
mvn test -pl ui-module -Dcucumber.filter.tags="@login"
mvn test -pl ui-module -Dcucumber.filter.tags="@cart"


# ── API Module ────────────────────────────────────────
# Run all API tests
mvn clean test -pl api-module

# Run specific API tag
mvn test -pl api-module -Dcucumber.filter.tags="@api"


# ── Hybrid Module ─────────────────────────────────────
# Build all first, then run hybrid
mvn clean install -DskipTests
mvn test -pl hybrid-module

# Run with hybrid tag
mvn test -pl hybrid-module -Dcucumber.filter.tags="@hybrid"


# ── Full Suite from Root ──────────────────────────────
mvn clean install -DskipTests && mvn test
```

### Complete Execution Order

| Phase | Module | Command | What Runs |
|-------|--------|---------|-----------|
| 1 | `common` | `mvn clean install -pl common -DskipTests` | Builds shared utilities |
| 2 | `ui-module` | `mvn clean test -pl ui-module` | TC1 → TC2 → TC3 → TC4 → TC5 |
| 3 | `api-module` | `mvn clean test -pl api-module` | API-TC1 → API-TC6 |
| 4 | `hybrid-module` | `mvn clean test -pl hybrid-module` | HC1 → HC2 → HC3 → HC4 |

---

### Report Locations

```
target/
├── extent-reports/
│   └── AutomationReport.html     ← 🌟 Main ExtentReports (Dark Theme)
├── cucumber-reports/
│   ├── login-report.html
│   ├── register-report.html
│   ├── cart-report.html
│   ├── api-report.html
│   └── hybrid-report.html
└── screenshots/
    └── *.png                     ← Failure screenshots (auto-captured)
```

---

## ⚡ Challenges & Solutions

| # | Exception / Issue | Module | Root Cause | Solution Applied |
|---|------------------|--------|-----------|-----------------|
| 1 | `DuplicateStepDefinitionException` | ui-module | LoginSteps & RegisterSteps both had same `@Then` step | Moved shared steps to `CommonSteps.java` |
| 2 | `CucumberException` — no zero-arg constructor | ui-module | `cucumber-picocontainer` jar missing from classpath | Added picocontainer with compile scope |
| 3 | `NoSuchMethodError` — ExtentTestManager | common | Stale common jar in `.m2` repository | `mvn clean install -pl common` |
| 4 | `ExceptionInInitializerError` — ConfigReader | common | `config.properties` not in classpath | Placed in `src/main/resources`, marked as Resources Root |
| 5 | `NullPointerException` — DriverManager.getDriver() | ui-module | Driver not initialized before screenshot | Added null check in `Hooks.tearDown()` |
| 6 | `InvalidSelectorException` — ProductPage | ui-module | `//a[contains(@data-product-id)]` invalid XPath | Changed to CSS: `a[data-product-id]` |
| 7 | `TimeoutException` — cartHeading | ui-module | Wrong locator — no `h2` with that text | Changed to `//li[contains(text(),'Shopping Cart')]` |
| 8 | `AssertionError` — User not logged in | ui-module | Ad overlay blocking login button click | `dismissAdsIfPresent()` + `jsClick()` + Chrome DNS blocking |
| 9 | `AssertionError` — Account Created not shown | ui-module | Email already registered on previous run | Unique email via `System.currentTimeMillis()` |
| 10 | `AssertionError` — Error message not visible | ui-module | Wrong error message XPath locator | Updated to `//p[contains(text(),'incorrect')]` |
| 11 | `AssertionError` — Cart not empty | ui-module | Wrong `emptyCartMessage` locator | Changed to `//b[contains(text(),'Cart is empty!')]` |
| 12 | `LifecyclePhaseNotFoundException` | Maven | PowerShell treating `-D` parameter incorrectly | Used quotes around `-DsuiteXmlFile` parameter |
| 13 | `CompilationError` — Hooks.java | ui-module | Missing package declaration on line 1 | Added `package com.precision.ui.hooks` |
| 14 | `NoSuchMethodError` — ExtentTestManager | common | Old jar still in classpath after code change | `mvn clean install` from root |
| 15 | Screenshots broken in report | common | File path not accessible from browser | Embedded screenshots as **base64** in HTML report |

---

## 👥 Team

| Member | Module | Responsibilities |
|--------|--------|-----------------|
| **P. Mohan Sri Sai Surya** | 🔌 API Module Lead | ApiClient, RequestBuilders, Validators, UserSteps, ProductSteps, ApiHooks, ApiTestRunner, all API feature files |
| **P. Raghu Vamsi** | 🌐 UI Module Lead | BasePage, all Page Classes (HomePage, LoginPage, RegisterPage, ProductPage, CartPage), StepDefs, Hooks, 3 Runners, all UI feature files |
| **P. Yuva Chandra Naga Prasad** | 🔗 Hybrid Module Lead | HybridContext, HybridSteps, HybridHooks, HybridTestRunner, 4 hybrid scenarios |
| **P. Pavan Kumar** | 🏗️ Common Module Lead | DriverManager (ThreadLocal), ConfigReader, ExcelUtils, ExtentReportManager, ExtentTestManager, WaitUtils, ScreenshotUtils, LogManager |

---

<div align="center">

**Precision Unified Automation Framework · Version 1.0.0**

*Java 21 · Selenium 4.18 · Cucumber 7.15 · RestAssured 5.4 · TestNG 7.9 · Maven*

[automationexercise.com](https://automationexercise.com)

</div>
