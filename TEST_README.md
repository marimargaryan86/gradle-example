# Kineto.app QA Test Suite

Automated test suite for testing **Authorization** and **Publishing** functionality on kineto.app.

## Test Structure

```
src/test/groovy/com/kineto/tests/
├── BaseSpec.groovy           # Base class with shared configuration
├── AuthorizationSpec.groovy  # 15 Authorization test cases (AUTH-01 to AUTH-15)
├── PublishingSpec.groovy     # 26 Publishing test cases (PUB-01 to PUB-26)
└── SecuritySpec.groovy       # 8 Security test cases (SEC-01 to SEC-08)
```

## Running Tests

### Run All Tests
```bash
./gradlew test
```

### Run Specific Test Suites
```bash
# Authorization tests only
./gradlew testAuth

# Publishing tests only
./gradlew testPublishing

# Security tests only
./gradlew testSecurity
```

### Run Tests with Tags
```bash
# Run smoke tests
./gradlew test --tests '*' -Dspock.include.tags='smoke'

# Run priority-high tests
./gradlew test --tests '*' -Dspock.include.tags='priority-high'

# Run security tests
./gradlew test --tests '*' -Dspock.include.tags='security'
```

### Run with Custom Configuration
```bash
# Set base URL
./gradlew test -Dkineto.base.url=https://staging.kineto.app

# Set test credentials
./gradlew test -Dkineto.test.email=test@example.com -Dkineto.test.password=testpass
```

## Test Reports

After running tests, reports are generated in:
- **HTML Report**: `build/reports/tests/test/index.html`
- **JUnit XML**: `build/test-results/test/`

## Test Cases Summary

### Authorization (15 tests)
| ID | Test Case | Tags |
|---|---|---|
| AUTH-01 | Sign in (happy path) | smoke, priority-high |
| AUTH-02 | Sign out | smoke |
| AUTH-03 | Login with invalid credentials | negative |
| AUTH-04 | Session persistence after refresh | session |
| AUTH-05 | Access protected page while logged out | security |
| AUTH-06 | Session stability (cache clear) | session |
| AUTH-07 | Login with email case variations | validation |
| AUTH-08 | Multiple failed login attempts | security |
| AUTH-09 | Concurrent sessions | priority-high, session |
| AUTH-10 | Session timeout after inactivity | session |
| AUTH-11 | Deep link redirect after login | priority-high, navigation |
| AUTH-12 | Login in multiple tabs | session |
| AUTH-13 | Token/cookie expiration | session |
| AUTH-14 | Password reset flow | recovery |
| AUTH-15 | Social login (OAuth) | oauth |

### Publishing (26 tests)
| ID | Test Case | Tags |
|---|---|---|
| PUB-01 | Create new idea | smoke |
| PUB-02 | Publish without subscription | subscription |
| PUB-03 | Publish idea (happy path) | smoke, priority-high |
| PUB-04 | Preview idea | preview |
| PUB-05 | Unpublished idea indicator | ui |
| PUB-06 | Published idea UI tooltips | ui |
| PUB-07 | Access published idea without login | access, priority-high |
| PUB-08 | Access idea before publishing | access, security |
| PUB-09 | Edit idea after publishing | consistency |
| PUB-10 | Edit and re-publish idea | update |
| PUB-11 | Unpublish idea | unpublish |
| PUB-12 | Refresh after publishing | state |
| PUB-13 | Network interruption | error-handling |
| PUB-14 | Published vs preview consistency | consistency |
| PUB-15 | Delete published idea | deletion |
| PUB-16 | Logout during publishing | session, priority-high |
| PUB-17 | Publish empty content | validation, priority-high |
| PUB-18 | Publish max length content | validation |
| PUB-19 | Special characters & emojis | encoding |
| PUB-20 | Browser back during publish | navigation |
| PUB-21 | Double-click publish | priority-high, ui |
| PUB-22 | Auto-save draft | auto-save |
| PUB-23 | Published idea URL/slug | url |
| PUB-24 | Copy/share link | sharing |
| PUB-25 | Mobile publish flow | mobile |
| PUB-26 | Slow network publish | performance |

### Security (8 tests)
| ID | Test Case | Tags |
|---|---|---|
| SEC-01 | XSS attempt | xss, priority-high |
| SEC-02 | API access without auth | api |
| SEC-03 | Access other users' drafts | access-control, priority-high |
| SEC-04 | CSRF protection | csrf |
| SEC-05 | SQL injection | sql-injection |
| SEC-06 | Session ID regeneration | session-fixation |
| SEC-07 | Password protection | sensitive-data |
| SEC-08 | Rate limiting | rate-limiting |

## CI/CD Integration (TeamCity)

### TeamCity Build Configuration

Add these build steps to your TeamCity configuration:

```xml
<build-type>
    <name>Kineto QA Tests</name>
    <steps>
        <step type="gradle">
            <gradle-tasks>clean test</gradle-tasks>
            <gradle-params>
                -Dkineto.base.url=%kineto.test.url%
                -Dkineto.test.email=%kineto.test.email%
                -Dkineto.test.password=%kineto.test.password%
            </gradle-params>
        </step>
    </steps>
    <build-triggers>
        <trigger type="schedulingTrigger">
            <param name="cronExpression" value="0 0 9 * * ?"/>
        </trigger>
    </build-triggers>
</build-type>
```

### Environment Variables for CI
```
KINETO_BASE_URL=https://kineto.app
KINETO_TEST_EMAIL=<test-account-email>
KINETO_TEST_PASSWORD=<test-account-password>
```

## Implementing Actual Tests

The test files contain placeholder implementations. To implement actual tests:

1. **For UI Tests**: Uncomment and update Selenium WebDriver code
2. **For API Tests**: Use REST-assured for API validation
3. **Locate Elements**: Update element locators based on actual kineto.app DOM

Example implementation:
```groovy
when: "User enters valid credentials and submits"
driver.findElement(By.id("email")).sendKeys(TEST_EMAIL)
driver.findElement(By.id("password")).sendKeys(TEST_PASSWORD)
driver.findElement(By.id("login-button")).click()
```

## Dependencies

- **Spock Framework 2.3**: BDD-style testing
- **Selenium 4.16**: Browser automation
- **WebDriverManager 5.6**: Automatic WebDriver management
- **REST-assured 5.4**: API testing
- **JUnit 5.10**: Test platform
