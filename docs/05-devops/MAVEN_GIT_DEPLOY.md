```markdown
# VERITAS CHAMBERS: MAVEN, GIT & DEPLOYMENT WORKFLOW
**Target Agent:** Google Antigravity
**Document Scope:** Build Lifecycle, Version Control, Environment Config, and Deployment

================================================================================
## 1. DOCUMENT OBJECTIVE
================================================================================
This document defines the operational development lifecycle for the Veritas Chambers website. It provides precise instructions for building, testing, packaging, and versioning the Spring Boot monolithic application. 

**Workflow:** `Local Dev` -> `Maven Dependency Resolution` -> `Compile/Test` -> `Git Commit` -> `Package (.jar)` -> `Deployment Prep` -> `Production`.

*(Note: The exact production deployment hosting provider is currently **TBD**).*

================================================================================
## 2. JAVA & MAVEN VERSION REQUIREMENTS
================================================================================
*   **Java Version:** `Java 17` (Minimum).
    *   *Verification:* Run `java -version` and `javac -version`. Both must return 17+.
*   **Maven Version:** Maven 3.8+. 
    *   *Policy:* The project relies on the **Maven Wrapper** to guarantee reproducible builds across environments. Do not assume a globally installed `mvn` command.

================================================================================
## 3. MAVEN WRAPPER USAGE
================================================================================
All build commands must use the Maven Wrapper located in the project root.

*   **Linux/macOS:** `./mvnw`
*   **Windows:** `mvnw.cmd`

*(If the wrapper is missing from the repository root, the first operational task is to generate it via `mvn wrapper:wrapper`).*

================================================================================
## 4. MAVEN PROJECT STRUCTURE & POM RULES
================================================================================
**Structure:**
```text
veritas-chambers/
├── .mvn/wrapper/
├── src/
│   ├── main/java/com/veritaschambers/
│   ├── main/resources/
│   └── test/java/com/veritaschambers/
├── mvnw
├── mvnw.cmd
└── pom.xml

```

**POM.xml Rules:**

* Manage dependency versions via `spring-boot-starter-parent`. Do not hardcode specific versions unless overriding a vulnerability.
* **Approved Starters:** `web`, `security`, `data-jpa`, `validation`, `test`.
* **Approved Drivers:** `mysql-connector-j`.
* **PROHIBITED Dependencies (Unless explicitly approved):** Lombok, MapStruct, Flyway, Liquibase, Actuator, Swagger/OpenAPI, Redis, Cloud SDKs.

================================================================================

## 5. LOCAL ENVIRONMENT CONFIGURATION

================================================================================
Configuration is separated by Spring Profiles:

* `src/main/resources/application.yml` (Base configuration, active by default).
* `src/main/resources/application-dev.yml` (Local development overrides).
* `src/main/resources/application-prod.yml` (Production overrides).
* `src/test/resources/application-test.yml` (Test-specific overrides, e.g., H2 database).

**Secret Management:**

* Database passwords, JWT secrets, and API keys MUST be injected via environment variables (e.g., `${DB_PASSWORD}`).
* Local `.env` files used to source these variables must be strictly listed in `.gitignore`.

================================================================================

## 6. LOCAL DATABASE WORKFLOW

================================================================================

* **Engine:** MySQL 8.x.
* **Dev Setup:** The developer must create a local database (e.g., `veritas_dev`) and provide local credentials via `.env` or IDE run configurations.
* **Schema:** Managed by `spring.jpa.hibernate.ddl-auto=update` in the `dev` profile. (Migrations like Flyway are currently deferred).

================================================================================

## 7. TESTING WORKFLOW & BUILD FAILURE RULE

================================================================================

* **Testing Requirement:** All business logic (`@Service` layer) and custom queries (`@Repository`) must be covered by JUnit 5 tests.
* **Test Database:** Tests must use an isolated in-memory database (H2) or a dedicated test container to prevent corrupting the local `veritas_dev` database.
* **Build Failure Rule:** The project MUST NOT be committed or deployed if tests fail or compilation breaks.
* **Prohibition:** Google Antigravity MUST NEVER use `-DskipTests` to bypass failing tests during normal development or deployment. Fix the root cause.

================================================================================

## 8. STANDARD DEVELOPMENT COMMANDS

================================================================================
*(Note: Replace `./mvnw` with `mvnw.cmd` on Windows).*

* **Clean target directory:** `./mvnw clean`
* **Compile code:** `./mvnw compile`
* **Run unit/integration tests:** `./mvnw test`
* **Run full verification (Compile + Test + Checks):** `./mvnw clean verify`
* **Package for deployment:** `./mvnw clean package`
* **Run local Spring Boot server:** `./mvnw spring-boot:run`

================================================================================

## 9. GIT REPOSITORY & BRANCHING STRATEGY

================================================================================

* **Main Branch:** `main` (Production-ready code).
* **Development Strategy:** Lightweight feature branching.
* `feature/<kebab-case-description>` (e.g., `feature/consultation-api`)
* `fix/<kebab-case-description>` (e.g., `fix/mobile-nav`)
* `chore/<kebab-case-description>` (e.g., `chore/update-deps`)



**Gitignore Requirements:**
Ensure the following are excluded: `target/`, `.idea/`, `.vscode/`, `*.iml`, `.env`, `*.log`, `*.DS_Store`.

================================================================================

## 10. COMMIT RULES FOR ANTIGRAVITY

================================================================================
Before committing, Antigravity MUST:

1. Run `git status` and `git diff`.
2. Verify NO secrets or `.env` files are staged.
3. Verify NO debug statements (e.g., `System.out.println("here")`) remain.
4. Verify the build passes (`./mvnw verify`).

**Commit Message Format:**
Use semantic commit messages: `<type>: <short description>`

* `feat: add contact form validation`
* `fix: resolve null pointer in article service`
* `docs: update API_CONTRACTS.md`

================================================================================

## 11. DANGEROUS COMMANDS

================================================================================
Antigravity must avoid the following unless explicitly authorized:

* `git push --force` (Destroys remote history).
* `git reset --hard` (Destroys uncommitted local work).
* `git clean -fd` (Destroys untracked files).

================================================================================

## 12. RELEASE READINESS & PRODUCTION BUILD

================================================================================
The application is packaged as an executable Spring Boot JAR.

**Packaging Command:**
`./mvnw clean package`

**Artifact Location:**
The deployable file will be generated at `target/veritas-chambers-0.0.1-SNAPSHOT.jar` (exact name depends on `pom.xml` versioning).

**Pre-Release Checklist:**

* [ ] `./mvnw clean verify` passes 100%.
* [ ] `[MOCK]` credentials and dummy practice areas have been replaced with verified data (per `MOCK_DATA_STRATEGY.md`).
* [ ] No secrets exist in the codebase.
* [ ] Production properties are externalized.

================================================================================

## 13. DEPLOYMENT PLATFORM (TBD)

================================================================================
*(The specific hosting provider is currently undecided. The application is architected to run anywhere a JRE is available).*

**Platform-Independent Deployment Requirements:**

1. **Java Runtime:** The host must provide JRE 17+.
2. **Execution Command:** `java -jar target/<artifact-name>.jar`
3. **Port Binding:** The platform must be able to inject the port via the `SERVER_PORT` environment variable.
4. **Database Configuration:** The platform must inject `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD`.
5. **Security Configuration:** The platform must inject `JWT_SECRET` (if used) or session security keys.
6. **HTTPS:** TLS termination must be handled by the deployment provider's reverse proxy/load balancer. The Spring Boot app exposes HTTP locally to the proxy.

================================================================================

## 14. DEPLOYMENT VERIFICATION & ROLLBACK

================================================================================
**Post-Deployment Verification:**

1. Verify the homepage loads and the public API responds (200 OK).
2. Verify database connectivity (e.g., fetching published articles).
3. Verify the Contact form submits successfully.
4. Verify HTTPS is active and secure cookies are functioning.
5. Verify `/admin/login` is protected and does not leak stack traces on failure.

**Rollback Strategy:**
If verification fails:

1. Halt routing to the new deployment.
2. Revert the active deployment artifact to the previous known-good `.jar`.
3. If database schema was altered (TBD pending migration tool selection), restore from pre-deployment backup.

================================================================================

## 15. ANTIGRAVITY DEVELOPMENT LOOP

================================================================================

1. Read `ENTRY_INSTRUCTIONS.md`, `CONSTRAINTS.md`, `CURRENT_STATE_TODO.md`.
2. Inspect the existing implementation and relevant SSOT docs.
3. Write code (smallest required change).
4. Run targeted tests (`./mvnw test -Dtest=ClassName`).
5. Run full verification (`./mvnw verify`).
6. Inspect `git diff` for secrets/debug artifacts.
7. Update SSOT documentation (e.g., `API_CONTRACTS.md`) if architecture changed.
8. Commit and report to user.

================================================================================

## 16. SOURCE-OF-TRUTH OWNERSHIP

================================================================================
`MAVEN_GIT_DEPLOY.md` is the authoritative source for the Maven workflow, build commands, Git branching, and platform-neutral deployment prep.
It does NOT own Product Requirements (`PRD_OVERVIEW.md`), System Architecture (`SYSTEM_ARCHITECTURE.md`), or Database Design (`DB_SCHEMA.md`). Conflicts must be resolved explicitly based on document ownership.

```

```