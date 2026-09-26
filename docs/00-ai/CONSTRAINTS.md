# VERITAS CHAMBERS: PROJECT CONSTRAINTS
**Target Agent:** Google Antigravity
**Authority Level:** HIGHEST

================================================================================
## DOCUMENT PURPOSE
================================================================================
This document defines the absolute boundaries of the Veritas Chambers project. While `ENTRY_INSTRUCTIONS.md` dictates *how* you operate, this document dictates *what you are and are not allowed to do*. 

Its purpose is to prevent architectural drift, unauthorized framework introduction, factual hallucination, security vulnerabilities, and scope creep. This is a **HARD-CONSTRAINT** document.

================================================================================
## 1. CONSTRAINT CLASSIFICATION
================================================================================
All rules within the project fall into one of these categories:

*   **HARD CONSTRAINT:** Must never be violated without explicit, verified user approval. 
*   **SOFT CONSTRAINT:** Should normally be followed, but may be changed if a documented, technical reason exists.
*   **PREFERRED:** Recommended implementation direction, but not an absolute restriction.
*   **PROVISIONAL:** Temporary decision/data that will be replaced when verified information becomes available.
*   **DEFERRED:** Intentionally postponed features/tech. Must not be implemented unless explicitly activated.

Unless otherwise labeled, treat all rules in this document as **HARD CONSTRAINTS**.

================================================================================
## 2. TECHNOLOGY CONSTRAINTS
================================================================================
**APPROVED CORE STACK:**
*   **Frontend:** HTML5, CSS3, Bootstrap 5, Vanilla JavaScript
*   **Backend:** Java 17+, Spring Boot 3.x, Spring MVC, Spring REST
*   **Database:** MySQL 8.x, Spring Data JPA, Hibernate
*   **Security:** Spring Security
*   **Build/VCS:** Maven, Git

**BANNED TECHNOLOGIES (Unless explicitly approved):**
*   React, Angular, Vue, Next.js, Svelte
*   Node.js, Express.js, PHP, Laravel, Python/Django, .NET
*   MongoDB, NoSQL alternatives
*   Tailwind CSS, Material UI

**BANNED INFRASTRUCTURE (Unless explicitly approved):**
*   Microservices architecture
*   Redis, Elasticsearch, Kafka, Message Queues
*   Kubernetes, Service Meshes
*   Additional separated backend services

================================================================================
## 3. ARCHITECTURAL CONSTRAINTS
================================================================================
**Architecture:** MONOLITHIC SPRING BOOT (N-Tier / Layered)

**Expected Data Flow:**
`Frontend -> Controller -> DTO / Validation -> Service -> Repository -> JPA / Hibernate -> MySQL`

*   **Do not** convert to microservices.
*   **Do not** place business logic inside Controllers.
*   **Do not** perform direct database/repository calls from Controllers.
*   **Do not** bypass the Service layer.
*   **Do not** return JPA Entities directly to the frontend (use DTOs at API boundaries).

================================================================================
## 4. FRONTEND CONSTRAINTS
================================================================================
*   Bootstrap 5 is a structural foundation, *not* the final brand identity.
*   The site must not look like a default Bootstrap template.
*   Prioritize semantic HTML, accessibility, and SEO.
*   No heavy frontend frameworks. Interactivity must rely on Vanilla JavaScript.

================================================================================
## 5. BACKEND CONSTRAINTS
================================================================================
*   **Controllers:** Strict HTTP concerns, route mapping, and payload validation.
*   **Services:** Strict business logic and domain coordination.
*   **Repositories:** Strict data access.
*   Avoid circular dependencies between Service beans.
*   Avoid unnecessary abstraction layers (e.g., interfaces for services that only have and will only ever have one implementation, unless required for proxying/testing).

================================================================================
## 6. DATABASE CONSTRAINTS
================================================================================
*   **SSOT:** `docs/03-backend/DB_SCHEMA.md`
*   Use strictly normalized relational design (Primary Keys, Foreign Keys).
*   Do not store structured relational data as arbitrary JSON strings/text blobs unless strictly necessary.
*   Do not invent tables that are not defined in the SSOT. Any proposed schema change must update Entities, Repositories, Services, DTOs, APIs, and the SSOT document in sync.

================================================================================
## 7. API CONSTRAINTS
================================================================================
*   **SSOT:** `docs/03-backend/API_CONTRACTS.md`
*   Public and Administrative APIs must be strictly separated by URL paths (e.g., `/api/public/...` vs `/api/admin/...`).
*   **Never silently change** endpoint paths, HTTP verbs, or JSON payloads. The API Contract is binding.
*   If an API change is forced by logic, propose the change to the user and update the contract first.

================================================================================
## 8. SECURITY CONSTRAINTS
================================================================================
*   **SSOT:** `docs/03-backend/SECURITY_RULES.md`
*   **Never** hardcode passwords, API keys, or database credentials.
*   **Never** disable Spring Security authentication to "simplify development".
*   **Never** bypass authorization checks.
*   **Never** expose private client consultation/contact information via public endpoints.
*   **Never** store plaintext passwords.

================================================================================
## 9. ADMIN PANEL CONSTRAINTS
================================================================================
*   Must be rigorously protected via server-side authorization (Spring Security).
*   Cannot rely on frontend-only route protection (e.g., hiding HTML elements).
*   Admin APIs must validate the user's roles/permissions on every request.
*   Must not leak stack traces or credentials to the browser console.

================================================================================
## 10. LEGAL CONTENT CONSTRAINTS
================================================================================
**HARD CONSTRAINT:** NEVER invent factual professional information.

Do not fabricate or hallucinate:
*   Bar Council Enrollment details.
*   Degrees, universities, or qualifications.
*   Years of experience.
*   Case victories, court appearances, or client names.
*   Awards, rankings, or success rates.

================================================================================
## 11. MOCK DATA CONSTRAINTS
================================================================================
*   **SSOT:** `docs/01-project/MOCK_DATA_STRATEGY.md`
*   Missing information must use explicit visual markers: `[MOCK DATA]`, `[TO BE VERIFIED]`, or `[CONTENT TO BE PROVIDED]`.
*   Do not silently replace these placeholders with realistic-sounding fake information.

================================================================================
## 12. BRAND CONSTRAINTS
================================================================================
*   **SSOT:** `docs/04-frontend/BRAND_AND_UI.md`
*   **Core Identity:** Truth, Precision, Sophistication, Classical Legal Heritage + Modern Quality.
*   **Prohibited Designs:** Generic SaaS, Startup Landing Pages, Gaming aesthetics, Social Media clones.
*   **Prohibited Elements:** Excessive neon colors, heavy glassmorphism, decorative visual noise, bouncy animations.
*   The Veritas Chambers logo must be treated as the absolute anchor of the visual identity and must never be distorted.

================================================================================
## 13. WEBSITE SCOPE CONSTRAINTS
================================================================================
*   This is a professional legal practice website.
*   **Banned Scope Expansions:** Ecommerce, Social Networking, Forums, Complex CRM, Full Case-Management Software, Payment Processing.

================================================================================
## 14. AI / AUTOMATION CONSTRAINTS
================================================================================
*   **Do not** introduce automated AI legal advice.
*   **Do not** generate automated legal conclusions for users.
*   The website must not present AI-generated placeholder text as professional legal counsel from Advocate Dhiraj Sawant.

================================================================================
## 15. PRIVACY AND CONFIDENTIALITY
================================================================================
*   Client messages from the Contact/Consultation forms are highly sensitive.
*   Do not log form payloads containing PII (Personally Identifiable Information).
*   Do not use real client names or case details as mock data during development.

================================================================================
## 16. SEO CONSTRAINTS
================================================================================
*   Do not inject fake reviews, fake awards, or misleading structured data just to improve SEO scores.
*   Focus purely on semantic HTML, performance, and legitimate Local SEO markers for Sangli, Maharashtra.

================================================================================
## 17. ACCESSIBILITY CONSTRAINTS
================================================================================
*   Do not sacrifice accessibility for visual flair.
*   Mandatory: Semantic HTML, keyboard navigability, visible focus states, form labels, and sufficient text contrast.

================================================================================
## 18. PERFORMANCE CONSTRAINTS
================================================================================
*   Prioritize fast loading and minimal JavaScript.
*   Do not introduce heavy third-party UI libraries (like Three.js or heavy animation suites) solely for decorative purposes.

================================================================================
## 19. RESPONSIVE DESIGN CONSTRAINTS
================================================================================
*   Must scale gracefully across Mobile, Tablet, and Desktop.
*   Mobile layout is not an afterthought; critical user journeys (booking a consultation) must be flawless on small screens.

================================================================================
## 20. TESTING CONSTRAINTS
================================================================================
*   Code compilation is not completion.
*   **Never** claim a test passed unless you actually generated and executed the test via Maven/IDE.
*   If an environment limitation prevents you from running a test, state the limitation explicitly.

================================================================================
## 21. GIT CONSTRAINTS
================================================================================
*   **SSOT:** `docs/05-devops/MAVEN_GIT_DEPLOY.md`
*   **Never** commit secrets, `.env` files, or `application-prod.yml` with real passwords.
*   **Never** rewrite Git history (force push/rebase) without explicit approval.

================================================================================
## 22. DEPENDENCY CONSTRAINTS
================================================================================
*   Every `pom.xml` dependency requires a legitimate purpose.
*   Do not add libraries simply because they are popular. If a native Java/Spring feature exists, use it first.

================================================================================
## 23. CONFIGURATION CONSTRAINTS
================================================================================
*   Environment-specific values (DB urls, passwords, secret keys) must use Spring Boot environment variable injection (`${ENV_VAR_NAME}`).
*   Hardcoding environment secrets into source code is a critical violation.

================================================================================
## 24. FILE AND PROJECT STRUCTURE CONSTRAINTS
================================================================================
*   **SSOT:** `docs/02-architecture/FOLDER_STRUCTURE.md`
*   Do not create random utility folders or duplicate abstraction directories outside the established Maven/Spring convention.

================================================================================
## 25. CODE STYLE CONSTRAINTS
================================================================================
*   Write clear, consistent, testable code.
*   **Avoid:** God classes, giant methods, magic strings/numbers, deep nesting, and excessive comments on obvious logic.
*   Prefer single-responsibility and explicit dependency injection.

================================================================================
## 26. DESTRUCTIVE CHANGE CONSTRAINTS
================================================================================
*   **Explicit approval required for:** Dropping DB tables, deleting production data, removing major application modules, replacing architecture, or performing bulk code deletion.
*   Before attempting, explain the impact and request user confirmation.

================================================================================
## 27. SCOPE CONTROL
================================================================================
*   Implement exactly what was requested.
*   **No "While I was here..." coding.** Do not add unrequested features or abstractions. Mention improvements as suggestions, do not silently write them.

================================================================================
## 28. DOCUMENTATION CONSTRAINTS
================================================================================
*   Do not duplicate complete specifications across multiple files. Use references.
*   Never document an implementation as "complete" in `CURRENT_STATE.md` if it has only been planned and not actually coded and verified.

================================================================================
## 29. PROVISIONAL DECISIONS
================================================================================
Treat the following as **PROVISIONAL**:
*   Lawyer credentials, Practice areas, Email, Office hours, Domain, Final production content.
*   Do not hardcode these into deep architectural assumptions.

================================================================================
## 30. FUTURE TECHNOLOGY POLICY
================================================================================
Technologies like Redis, Docker, and Elasticsearch are **DEFERRED**. 
*   "Future consideration" does NOT mean "build it now". 
*   Stick to the MVP stack until instructed otherwise.

================================================================================
## 31. CONSTRAINT CHANGE POLICY
================================================================================
*   A Hard Constraint may only be changed if the user explicitly requests it OR approves a suggestion.
*   Never silently weaken a constraint.
*   If a constraint is formally changed, update this document and `CURRENT_STATE.md`.

================================================================================
## 32. BLOCKING SCENARIOS (WHAT TO DO)
================================================================================
If a requested feature CANNOT be implemented without violating a constraint in this document:
1. **STOP.** Do not silently work around the constraint.
2. Identify the constraint to the user.
3. Explain why it blocks the request.
4. Propose the minimum required change to the constraint.
5. Ask for user approval to proceed.

================================================================================
## 33. PRIORITY OF SIMPLICITY
================================================================================
When multiple technically valid paths exist, choose the solution that is:
1. Simpler
2. More maintainable
3. More secure
4. Consistent with the existing Monolith MVC architecture
*Do not optimize for novelty.*

================================================================================
## 34. FINAL NON-NEGOTIABLE RULES
================================================================================
*   **NO** stack changes without approval.
*   **NO** architecture changes without approval.
*   **NO** hallucinated legal facts or client data.
*   **NO** exposure of confidential data.
*   **NO** weakened security.
*   **NO** silent API or Database contract changes.
*   **NO** scope expansion or unnecessary dependencies.
*   **NO** destructive operations without approval.
*   **NO** claiming fake test results or fake completion.

================================================================================
## DOCUMENT RELATIONSHIPS (THE SSOT MATRIX)
================================================================================
Understand your `/docs` ecosystem. Do not duplicate rules, rely on the designated source:

*   **`ENTRY_INSTRUCTIONS.md`** -> Defines HOW you operate.
*   **`CONSTRAINTS.md`** -> Defines WHAT you must and must not do (This file).
*   **`CURRENT_STATE.md`** -> Defines WHERE the project currently stands.
*   **`PRD_OVERVIEW.md`** -> Defines WHAT product is being built.
*   **`MOCK_DATA_STRATEGY.md`** -> Defines HOW temporary content is handled.
*   **`SYSTEM_ARCHITECTURE.md`** -> Defines HOW the system is architected.
*   **`FOLDER_STRUCTURE.md`** -> Defines WHERE code belongs.
*   **`DB_SCHEMA.md`** -> Defines the database structure.
*   **`API_CONTRACTS.md`** -> Defines API behavior.
*   **`SECURITY_RULES.md`** -> Defines security implementation.
*   **`BRAND_AND_UI.md`** -> Defines visual rules.
*   **`SITEMAP_UX.md`** -> Defines website structure.
*   **`MAVEN_GIT_DEPLOY.md`** -> Defines CI/CD and Version Control rules.