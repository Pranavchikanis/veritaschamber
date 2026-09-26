```markdown
# AI ENTRY INSTRUCTIONS & OPERATING MANUAL
**Target Agent:** Google Antigravity
**Project:** Veritas Chambers Website

================================================================================
## 1. DOCUMENT PURPOSE
================================================================================
This document is the **AI Development Operating System** for the Veritas Chambers project. It dictates exactly how you, Google Antigravity, must behave, read context, resolve conflicts, and execute tasks. 

You must read this document at the beginning of every session or task. It is not a technical specification; it is your behavioral and operational manual to prevent architectural drift, UI inconsistency, data hallucination, and destructive changes.

================================================================================
## 2. PROJECT IDENTITY
================================================================================
*   **Project Name:** Veritas Chambers Website
*   **Legal Practice:** Veritas Chambers
*   **Lawyer:** Advocate Dhiraj Sawant
*   **Location:** Sangli, Maharashtra, India
*   **Office:** Opposite building of Vijay Nagar Court, below Aurum Films, Ground Floor, Sangli.
*   **Primary Contact:** +91 89835 12124
*   **Domain:** [DOMAIN_NAME] (Not decided yet)
*   **Email:** [MOCK EMAIL] (Pending verification)
*   **Office Hours:** [MOCK OFFICE HOURS] (Pending verification)
*   **Brand Philosophy:** "Veritas" (Truth, reality, accuracy). The brand communicates precision, integrity, confidentiality, and sophisticated legal authority within the Commonwealth tradition.
*   **Core Tech Stack:** Java 17+, Spring Boot 3.x, MySQL 8.x, Bootstrap 5, Vanilla JS.
*   **Architecture:** Monolithic Layered N-Tier MVC.

*CRITICAL NOTE: All missing or unverified information must be treated as [MOCK DATA]. You must never invent legal facts.*

================================================================================
## 3. CORE AI OPERATING PRINCIPLES
================================================================================
1.  **Follow documentation over assumptions:** Project documentation overrides your generalized training data.
2.  **Preserve existing architecture:** Do not restructure the application unless an approved change is explicitly required.
3.  **Strict framework boundaries:** Never introduce a new framework or technology without explicit user authorization.
4.  **No factual hallucination:** Never invent missing business, biographical, or legal information.
5.  **Contract stability:** Never silently change database schemas or API contracts.
6.  **Do no harm:** Never break existing functionality to implement unrelated features.
7.  **Simplicity first:** Prefer simple, maintainable solutions over unnecessary complexity or premature abstractions.
8.  **Reuse over reinvent:** Reuse existing project components, CSS classes, and utilities before creating duplicates.
9.  **Inspect before modifying:** Always read the existing implementation of a file before changing it.
10. **Verify rigorously:** Test and verify your changes before declaring a task complete.
11. **Maintain synchronization:** Keep documentation synchronized with meaningful architectural or structural changes.
12. **Ask when ambiguous:** Ask for clarification when a requirement is genuinely ambiguous, conflicting, or requires a destructive action.

================================================================================
## 4. DOCUMENT READING PROTOCOL
================================================================================
You do not need to read every document for every task. You must dynamically load context to preserve your token window and focus.

**MANDATORY (Read for EVERY task):**
1.  `00-ai/ENTRY_INSTRUCTIONS.md` (This file)
2.  `00-ai/CONSTRAINTS.md`
3.  `00-ai/CURRENT_STATE.md`

**DYNAMIC LOADING MATRIX (Load based on task type):**
*   **New Backend Feature:** `SYSTEM_ARCHITECTURE.md`, `DB_SCHEMA.md`, `API_CONTRACTS.md`, and `SECURITY_RULES.md` (if auth is involved).
*   **New Frontend Feature:** `BRAND_AND_UI.md`, `SITEMAP_UX.md`, `API_CONTRACTS.md`, and relevant architecture docs.
*   **Database Modification:** `DB_SCHEMA.md`, `SYSTEM_ARCHITECTURE.md`, `MOCK_DATA_STRATEGY.md`.
*   **API Modification:** `API_CONTRACTS.md`, `DB_SCHEMA.md`, `SECURITY_RULES.md`, `SYSTEM_ARCHITECTURE.md`.
*   **Security/Auth Task:** `SECURITY_RULES.md`, `API_CONTRACTS.md`, `SYSTEM_ARCHITECTURE.md`.
*   **UI Redesign/Styling:** `BRAND_AND_UI.md`, `SITEMAP_UX.md`.
*   **Deployment/Build:** `MAVEN_GIT_DEPLOY.md`, `SECURITY_RULES.md`.
*   **Bug Fix:** `CURRENT_STATE.md`, existing implementation code, and the specific domain SSOT (e.g., `API_CONTRACTS.md` for a REST error).

================================================================================
## 5. DOCUMENT AUTHORITY HIERARCHY
================================================================================
When resolving what to do, respect this strict chain of command:

1.  **Explicit User Instruction** (Highest Authority)
2.  `00-ai/CONSTRAINTS.md`
3.  **Domain-Specific Single Source of Truth (SSOT)** (e.g., `DB_SCHEMA.md`)
4.  `00-ai/ENTRY_INSTRUCTIONS.md`
5.  Other Project Documentation
6.  Existing Implementation Code
7.  AI General Knowledge / Assumptions (Lowest Authority)

**WARNING ON USER CONFLICTS:** If an Explicit User Instruction conflicts with `CONSTRAINTS.md` (e.g., the user says "Rewrite this view in React"), you must:
1. Identify and explain the conflict to the user.
2. DO NOT silently violate the constraint.
3. Ask for explicit confirmation to override the established constraint.

================================================================================
## 6. SINGLE SOURCE OF TRUTH (SSOT) RULE
================================================================================
Every major project domain has ONE authoritative document. Do not duplicate this information elsewhere.

*   **Product/Scope:** `01-project/PRD_OVERVIEW.md`
*   **Mock Data:** `01-project/MOCK_DATA_STRATEGY.md`
*   **AI Constraints:** `00-ai/CONSTRAINTS.md`
*   **Current State:** `00-ai/CURRENT_STATE.md`
*   **System Architecture:** `02-architecture/SYSTEM_ARCHITECTURE.md`
*   **Project Structure:** `02-architecture/FOLDER_STRUCTURE.md`
*   **Database:** `03-backend/DB_SCHEMA.md`
*   **API:** `03-backend/API_CONTRACTS.md`
*   **Security:** `03-backend/SECURITY_RULES.md`
*   **Brand/UI:** `04-frontend/BRAND_AND_UI.md`
*   **Website UX:** `04-frontend/SITEMAP_UX.md`
*   **DevOps/Deployment:** `05-devops/MAVEN_GIT_DEPLOY.md`

================================================================================
## 7. CONFLICT RESOLUTION
================================================================================
If you detect a conflict between documents or implementations:
*   **Never silently choose one.**
*   Determine the nature of the conflict (Technical, UX, Data).
*   Consult the relevant SSOT document based on the hierarchy.
*   If the UI requests a field not in `DB_SCHEMA.md`, the Database SSOT wins. Propose a DB schema update rather than faking the data.
*   If the API contract differs from the implementation, update the implementation to match the contract, or propose a contract update if logically required.
*   If the conflict cannot be resolved safely, ask the user. Do not implement a speculative fix.

================================================================================
## 8. TECHNOLOGY DISCIPLINE
================================================================================
You are strictly confined to the approved stack. 

**APPROVED CORE:**
Java 17+, Spring Boot 3.x, Spring MVC, Spring REST, Spring Security, Spring Data JPA, Hibernate, MySQL 8.x, Maven, Git, HTML5, CSS3, Bootstrap 5, Vanilla JavaScript.

**STRICTLY PROHIBITED (Unless explicitly unlocked by user):**
React, Angular, Vue, Next.js, Node.js, Express, PHP, Laravel, Python/Django, .NET, MongoDB, Redis, Elasticsearch, Kafka, Microservices. 

Do not introduce a technology merely because it is common, modern, or convenient.

================================================================================
## 9. ARCHITECTURAL DISCIPLINE
================================================================================
Maintain the Monolithic Spring Boot MVC architecture.

**Expected Flow:**
`Frontend UI -> REST Controller -> DTO/Validation -> Service Layer -> Repository -> JPA Entity -> MySQL`

*   **Controllers:** Handle HTTP routing, status codes, and DTO validation only.
*   **Services:** Contain 100% of the business logic.
*   **Repositories:** Interface with the database.
*   **Entities:** Map to DB tables. Never return an Entity directly to the frontend; map it to a DTO.
*   Do not bypass APIs from the frontend. Avoid circular dependencies.

================================================================================
## 10. CHANGE MANAGEMENT
================================================================================
Before making changes to existing files:
1. Inspect the file thoroughly.
2. Understand the current logic and dependencies.
3. Check the relevant SSOT documentation.
4. Determine the blast radius (does this affect the DB, API, Security, or UI?).
5. Make the *smallest appropriate change*. Preserve unrelated code.
6. If the change alters the architecture, update the relevant SSOT document *before* or *alongside* the implementation.

================================================================================
## 11. FEATURE IMPLEMENTATION PROTOCOL
================================================================================
Follow this exact sequence for every new feature:
1. Understand request.
2. Determine affected domains.
3. Read relevant SSOTs.
4. Inspect existing code.
5. Identify dependencies.
6. Plan the change.
7. Implement incrementally.
8. Validate logic.
9. Test execution.
10. Review for regressions.
11. Update `CURRENT_STATE.md` and docs.
12. Report completion.

================================================================================
## 12. MOCK DATA AND LEGAL CONTENT SAFETY
================================================================================
**CRITICAL RULE:** You must NEVER invent factual legal information.

If Dhiraj Sawant's Bar Council number, education, court victories, or specific practice descriptions are not provided, you must NOT generate fictional realistic-sounding text.
*   Consult `01-project/MOCK_DATA_STRATEGY.md`.
*   Use explicitly marked placeholders (e.g., `[MOCK_BAR_ID]`, `[CONTENT TO BE PROVIDED]`).
*   If a feature structurally requires a fact that doesn't exist, use the placeholder. Ask the user if it blocks development.

================================================================================
## 13. BRAND CONSISTENCY
================================================================================
Consult `04-frontend/BRAND_AND_UI.md` for visual guidelines.
*   The aesthetic must be Premium, Classical, Modern, and Authoritative.
*   **Prohibited Aesthetics:** SaaS dashboards, startup landing pages, neon colors, excessive glassmorphism, heavy gradients, gamified UI, bouncy animations.
*   Bootstrap is a structural foundation, not the final visual identity. Override it to match the Veritas Chambers philosophy.

================================================================================
## 14. SECURITY-FIRST BEHAVIOR
================================================================================
Consult `03-backend/SECURITY_RULES.md` before touching auth/data access.
*   Never hardcode credentials or API keys in source files.
*   Never expose private client information (e.g., Consultation data) in public APIs.
*   Never bypass or weaken Spring Security to make your development easier.
*   Never disable CSRF or CORS protections without explicit architectural approval.

================================================================================
## 15. DATABASE AND API DISCIPLINE
================================================================================
*   **Database:** Read `DB_SCHEMA.md`. Never silently add columns or change data types without evaluating migration impact and entity relationships.
*   **API:** Read `API_CONTRACTS.md`. Never silently change a JSON payload structure or HTTP verb because it is easier for your frontend implementation. Both ends must match the contract.

================================================================================
## 16. CODE QUALITY RULES
================================================================================
*   Write readable, modular, testable, and secure code.
*   Avoid dead code, duplicate logic, and "God classes".
*   No magic numbers or hardcoded configurations (use `application.yml`).
*   Avoid premature optimization and over-engineering. Deliver the simplest secure solution that meets the requirement.

================================================================================
## 17. TESTING AND VERIFICATION
================================================================================
Code generation does not equal completion.
Before declaring a task done:
1. Ensure the code compiles/builds successfully.
2. Run relevant tests.
3. Verify API contracts match Postman/Fetch requests.
4. Verify UI responsiveness (Mobile vs Desktop).
5. Check logs/consoles for silent runtime errors.
6. State explicitly if something cannot be tested in your current environment.

================================================================================
## 18. CURRENT STATE MANAGEMENT
================================================================================
`00-ai/CURRENT_STATE.md` is the authoritative project-progress tracker.
After completing a meaningful task:
*   Update the "Completed" section.
*   Update the "Pending/Next" section.
*   Log newly discovered "Known Issues" or "Blockers".
*   Keep it concise. Do not turn it into an endless Git-style commit log.

================================================================================
## 19. DOCUMENTATION MAINTENANCE
================================================================================
If your implementation introduces a material change to an API, Database Table, Route, or Architecture pattern, you MUST update the corresponding SSOT document in the `/docs` folder so future AI sessions remain accurate.

================================================================================
## 20. GIT DISCIPLINE
================================================================================
Consult `05-devops/MAVEN_GIT_DEPLOY.md`.
*   Commit messages must be meaningful and semantic.
*   NEVER commit secrets, `.env` files, or production credentials.
*   NEVER commit build artifacts (`target/`, `.class`, `.jar`).
*   Do not perform destructive Git history rewrites without user permission.

================================================================================
## 21. DESTRUCTIVE OPERATION SAFETY
================================================================================
Before deleting files, dropping DB tables, replacing architecture, or removing major dependencies:
1. Explain to the user what will be affected.
2. Check dependencies.
3. Evaluate safer alternatives.
4. **Ask for explicit confirmation** before execution.

================================================================================
## 22. SCOPE DISCIPLINE
================================================================================
Implement EXACTLY what was requested.
Do not add unrequested features, pages, database tables, abstractions, or animations. If you think an addition is highly valuable, suggest it in your response output rather than silently writing the code.

================================================================================
## 23. DEFINITION OF DONE
================================================================================
A task is DONE when:
[ ] Requirements are fully met.
[ ] SSOT docs were consulted.
[ ] Existing implementation is intact (no regressions).
[ ] Code compiles and tests pass.
[ ] Security rules were obeyed.
[ ] `CURRENT_STATE.md` and related docs are updated.

================================================================================
## 24. RESPONSE FORMAT FOR ANTIGRAVITY
================================================================================
When reporting completed work to the user, strictly use this markdown structure:

```markdown
## Summary
[Brief description of what was built/fixed]

## Files Changed
[List of modified/created files]

## Documentation Consulted
[List of /docs files read to complete this]

## Verification
[Checks/tests actually performed]

## Known Issues
[Any blockers or unresolved edge cases]

## Documentation Updates
[Which docs were updated, if any]

## Next Step
[The logical next development action]

```

================================================================================

## 25. WHEN TO ASK THE USER

================================================================================
Stop guessing and ask the user when:

* Requirements or docs materially conflict.
* A critical legal fact is missing.
* A database change has ambiguous ripple effects.
* A requested change violates `CONSTRAINTS.md`.
* A destructive operation is required.

================================================================================

## 26. FINAL OPERATING RULE: THE LOOP

================================================================================
**READ** context -> **PLAN** the change -> **IMPLEMENT** safely -> **VERIFY** execution -> **UPDATE** documentation -> **REPORT** accurately.

Your goal is not just to generate code. Your goal is to steward a coherent, secure, maintainable, premium Java/Spring Boot application for Veritas Chambers over the entire project lifecycle.

```

```