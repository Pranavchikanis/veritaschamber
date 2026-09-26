```markdown
# VERITAS CHAMBERS: FOLDER STRUCTURE
**Target Agent:** Google Antigravity
**Document Scope:** Physical and Logical Project Organization

================================================================================
## PRIMARY OBJECTIVE
================================================================================
This document is the authoritative source of truth for the physical and logical file organization of the Veritas Chambers website. It dictates exactly where files belong, preventing architectural drift, messy root directories, and structural confusion as development progresses. 

**Rule for Antigravity:** Before creating a new file, identify its responsibility and architectural layer, then consult this document to determine its correct location. Do not create random directories.

================================================================================
## 1. ROOT PROJECT STRUCTURE
================================================================================
The project strictly follows standard Maven and Spring Boot conventions.

```text
veritas-chambers/
├── docs/                 (Authoritative project documentation)
├── src/                  (Application source code and resources)
├── .gitignore            (Git exclusion rules)
├── pom.xml               (Maven dependencies and build configuration)
└── README.md             (Developer entry point)

```

================================================================================

## 2. ROOT DIRECTORY RESPONSIBILITIES

================================================================================

| Directory/File | Purpose | Allowed Content | Must Not Contain |
| --- | --- | --- | --- |
| `/docs` | AI instructions and project specs. | `.md` architecture/planning files. | Application source code, secrets. |
| `/src` | Application implementation. | Java, HTML, CSS, JS, SQL, images. | `.md` project specs, `target/`. |
| `pom.xml` | Maven build definition. | Dependency/Plugin XML. | Passwords, environment secrets. |
| `.gitignore` | Prevents committing bad files. | Exclusion patterns. | N/A |
| `README.md` | Quick-start guide. | Setup instructions. | Detailed architecture specs. |

================================================================================

## 3. MAVEN / SPRING BOOT STRUCTURE

================================================================================
Under `/src`, the project follows the non-negotiable Maven standard:

* `src/main/java/` -> Contains all production Java source code.
* `src/main/resources/` -> Contains all static assets, templates, and application configuration.
* `src/test/java/` -> Contains all JUnit/integration test classes.
* `src/test/resources/` -> Contains test-specific configuration and mock data SQL.

================================================================================

## 4. JAVA PACKAGE STRUCTURE

================================================================================
**Base Package:** `src/main/java/com/veritaschambers/`

| Package | Responsibility | Belongs Here | Does NOT Belong Here |
| --- | --- | --- | --- |
| `config` | Spring configuration. | `@Configuration`, WebMvc, CORS config. | Business logic, secrets. |
| `controller` | HTTP routing & payload validation. | `@RestController`, `@Controller`. | Business logic, DB queries. |
| `dto` | API payload contracts. | Request/Response Records or POJOs. | JPA `@Entity` classes. |
| `entity` | Database models. | `@Entity`, `@Table` classes. | DTOs, API logic. |
| `exception` | Error handling. | Custom exceptions, `@ControllerAdvice`. | Business logic. |
| `mapper` | Entity <-> DTO conversion. | MapStruct interfaces or manual mappers. | Repositories. |
| `repository` | Data access abstraction. | Spring Data JPA interfaces. | Controllers, complex logic. |
| `security` | Authentication/Authorization. | Auth filters, `UserDetailsServiceImpl`. | Plaintext passwords. |
| `service` | Business logic & transactions. | Interfaces and `impl` classes. | HTTP `HttpServletRequest`. |
| `validation` | Custom validation rules. | Custom `@Constraint` annotations. | Standard DTOs. |
| `util` | Stateless shared utilities. | Date formatters, slug generators. | Business logic, DB access. |

================================================================================

## 5. PACKAGE NAMING RULES

================================================================================

* All package names must be strictly lowercase (no camelCase).
* Use singular nouns for packages (e.g., `controller` not `controllers`).
* Do not create overly deep nesting unless the module size justifies it.

================================================================================

## 6. CONTROLLER STRUCTURE

================================================================================
Organized by security boundary:

* `com.veritaschambers.controller.publicapi` -> Public website endpoints (e.g., submitting a consultation, fetching articles).
* `com.veritaschambers.controller.adminapi` -> Protected administrative endpoints requiring authentication.

================================================================================

## 7. DTO STRUCTURE

================================================================================
Organized by direction:

* `com.veritaschambers.dto.request` -> Incoming payloads (e.g., `ConsultationRequestDTO`).
* `com.veritaschambers.dto.response` -> Outgoing payloads (e.g., `ArticleSummaryResponseDTO`).

================================================================================

## 8. SERVICE STRUCTURE

================================================================================
If Spring interfaces are utilized for loose coupling:

* `com.veritaschambers.service` -> Contains the Interface (e.g., `ArticleService.java`).
* `com.veritaschambers.service.impl` -> Contains the Implementation (e.g., `ArticleServiceImpl.java`).
*(If the architecture deems interfaces unnecessary for simple CRUD, implementations may reside directly in the `service` package).*

================================================================================

## 9. UTIL STRUCTURE

================================================================================
The `util` package is heavily restricted. It is **only** for pure, stateless, shared helper functions (e.g., String manipulation, date formatting). It must never become a dumping ground for orphaned business logic or database queries.

================================================================================

## 10. RESOURCE STRUCTURE

================================================================================
**Base Directory:** `src/main/resources/`

* `static/` -> Assets served directly to the browser (CSS, JS, Images).
* `templates/` -> Server-side HTML files (Thymeleaf/HTML5).
* `application.yml` -> Base Spring Boot configuration.
* `application-dev.yml` / `application-prod.yml` -> Environment-specific overrides.

================================================================================

## 11. STATIC ASSETS ORGANIZATION

================================================================================
**Base Directory:** `src/main/resources/static/`

```text
static/
├── css/
│   ├── base.css       (Global resets, typography)
│   ├── layout.css     (Header, footer, grid)
│   ├── components.css (Buttons, cards, forms)
│   └── admin.css      (Dashboard-specific overrides)
├── js/
│   ├── main.js        (Global behavior)
│   ├── components/    (Reusable modules e.g., form-validation.js)
│   └── admin/         (Admin dashboard specific logic)
├── images/
│   ├── brand/         (Veritas Chambers logo)
│   ├── ui/            (Icons, backgrounds)
│   └── content/       (Placeholder photos, article images)
└── vendor/            (Third-party libraries)
    └── bootstrap/     (Locally hosted Bootstrap assets, if not using CDN)

```

================================================================================

## 12. HTML / TEMPLATE STRUCTURE

================================================================================
**Base Directory:** `src/main/resources/templates/`

```text
templates/
├── public/            (Public-facing pages)
│   ├── home.html
│   ├── about.html
│   ├── practice-areas.html
│   └── contact.html
├── admin/             (Protected dashboard pages)
│   ├── login.html
│   ├── dashboard.html
│   └── consultations.html
└── fragments/         (Reusable HTML partials)
    ├── head.html
    ├── header.html
    ├── footer.html
    └── admin-sidebar.html

```

================================================================================

## 13. DATABASE RESOURCE STRUCTURE

================================================================================
If database migration or seeding is utilized:

* `src/main/resources/db/migration/` -> Flyway/Liquibase versioned SQL files (if approved).
* `src/main/resources/data.sql` -> Spring Boot default data seeding (for mock data or initial admin creation).

================================================================================

## 14. CONFIGURATION AND SECRETS

================================================================================

* `application.yml` must NOT contain production secrets (DB passwords, JWT keys).
* Secrets must be injected via Environment Variables (e.g., `${DB_PASSWORD}`).
* Local `.env` files must be strictly excluded via `.gitignore`.

================================================================================

## 15. TEST STRUCTURE

================================================================================
**Base Directory:** `src/test/java/com/veritaschambers/`
Test packages must mirror the main package structure precisely.

```text
test/java/com/veritaschambers/
├── controller/        (HTTP / WebMvc tests)
├── service/           (Business logic / Unit tests)
├── repository/        (DataJpa tests)
└── integration/       (Full application slice tests)

```

* `src/test/resources/application-test.yml` -> Configures an in-memory database (H2) or testcontainers for isolation.

================================================================================

## 16. NAMING CONVENTIONS

================================================================================

* **Java Classes/Interfaces:** `PascalCase` (e.g., `ConsultationService`).
* **Methods/Variables:** `camelCase` (e.g., `findBySlug`).
* **Java Packages:** `lowercase` (e.g., `controller`).
* **HTML/CSS/JS Files:** `kebab-case` (e.g., `practice-areas.html`, `main-nav.js`).
* **Documentation:** `UPPERCASE_WITH_UNDERSCORES.md` (e.g., `FOLDER_STRUCTURE.md`).

================================================================================

## 17. DUPLICATION PREVENTION & FILE CREATION RULES

================================================================================
Before Antigravity creates a file, it MUST:

1. Identify the file's architectural responsibility.
2. Check this document for the correct directory.
3. Search the repository to ensure an existing file/component does not already serve this purpose.
4. If modifying vendor code (e.g., Bootstrap), override it in custom CSS rather than editing the vendor file directly.

**Strict Prohibitions:**

* Do not create random scratch files in the root directory.
* Do not put backend logic (Java) in frontend directories.
* Do not put HTML templates inside `static/`.

================================================================================

## 18. GIT STRUCTURE RULES

================================================================================
**Must Commit:** Source code, `pom.xml`, documentation, static assets.
**Must NOT Commit:** `target/` directory, IDE `.idea/` or `.settings/` folders, OS `.DS_Store` files, local `.env` files containing secrets.

================================================================================

## 19. COMPLETE REFERENCE TREE

================================================================================

```text
veritas-chambers/
├── docs/
│   ├── 00-ai/
│   ├── 01-project/
│   ├── 02-architecture/
│   ├── 03-backend/
│   ├── 04-frontend/
│   └── 05-devops/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── veritaschambers/
│   │   │           ├── config/
│   │   │           ├── controller/
│   │   │           │   ├── publicapi/
│   │   │           │   └── adminapi/
│   │   │           ├── dto/
│   │   │           │   ├── request/
│   │   │           │   └── response/
│   │   │           ├── entity/
│   │   │           ├── exception/
│   │   │           ├── mapper/
│   │   │           ├── repository/
│   │   │           ├── security/
│   │   │           ├── service/
│   │   │           │   └── impl/
│   │   │           ├── validation/
│   │   │           └── util/
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   ├── images/
│   │       │   └── vendor/
│   │       ├── templates/
│   │       │   ├── public/
│   │       │   ├── admin/
│   │       │   └── fragments/
│   │       ├── application.yml
│   │       └── data.sql
│   └── test/
│       ├── java/
│       │   └── com/veritaschambers/...
│       └── resources/
│           └── application-test.yml
├── .gitignore
├── pom.xml
└── README.md

```

================================================================================

## 20. SOURCE-OF-TRUTH RULE

================================================================================
`FOLDER_STRUCTURE.md` is strictly authoritative for **physical and logical file organization**.

It does **NOT** override:

* `SYSTEM_ARCHITECTURE.md` (Architectural patterns and data flow).
* `DB_SCHEMA.md` (Database tables and relationships).
* `API_CONTRACTS.md` (REST payload definitions).
* `CONSTRAINTS.md` (Hard project boundaries).

If a conflict arises regarding where a file belongs, this document wins. If a conflict arises regarding what the file does, `SYSTEM_ARCHITECTURE.md` wins.

```

```