# VERITAS CHAMBERS: MOCK DATA STRATEGY
**Target Agent:** Google Antigravity
**Document Scope:** Governance and Handling of Unverified/Temporary Data

================================================================================
## CORE PRINCIPLE
================================================================================
**Missing information must remain missing or be explicitly mocked. It must NEVER be silently invented.**

Mock data is a development tool, not factual content. Documenting a placeholder value does not make that value verified. Because "Veritas" signifies truth and precision, the website must maintain an absolute standard of factual accuracy. We must protect the project from accidentally presenting fabricated professional or legal information as genuine.

================================================================================
## 1. DATA CLASSIFICATION SYSTEM
================================================================================
All data within this project falls into one of the following classifications:

*   **VERIFIED:** Information confirmed by the user/project owner and approved for production use.
*   **MOCK:** Artificial data created solely to support UI development, database seeding, or API testing.
*   **PROVISIONAL:** Information proposed for development but currently awaiting user confirmation.
*   **PLACEHOLDER:** A temporary UI/content marker indicating that real information is missing.
*   **DRAFT:** Content that may be real (e.g., an unfinished article) but has not yet been approved for publication.
*   **DEPRECATED:** Previously used information that is no longer accurate and must not be used.
*   **UNKNOWN:** Information for which no value is currently available.

================================================================================
## 2. TRUST LEVELS
================================================================================
Data must progress through a trust hierarchy before it can reach the public:

*   **LEVEL 0 — UNKNOWN:** No information available.
*   **LEVEL 1 — PLACEHOLDER:** Temporary UI marker.
*   **LEVEL 2 — MOCK:** Artificial development data.
*   **LEVEL 3 — PROVISIONAL:** Proposed but unverified.
*   **LEVEL 4 — VERIFIED:** Confirmed and approved.
*   **LEVEL 5 — PUBLISHED:** Verified and currently live on production.

*Rule: Higher trust levels must NEVER be assigned by the AI agent without explicit user evidence.*

================================================================================
## 3. CURRENT INFORMATION STATE
================================================================================

| Data Point | Current Value/State | Classification | Prod Allowed? | Replacement Req? |
| :--- | :--- | :--- | :--- | :--- |
| Law Firm Name | Veritas Chambers | VERIFIED | Yes | No |
| Lawyer Name | Dhiraj Sawant | VERIFIED | Yes | No |
| Location | Sangli, Maharashtra | VERIFIED | Yes | No |
| Office Address | Opp. Vijay Nagar Court... | VERIFIED | Yes | No |
| Phone | +91 89835 12124 | VERIFIED | Yes | No |
| Official Logo | [Provided in workspace] | VERIFIED | Yes | No |
| Email | `[MOCK EMAIL]` | MOCK | No | Yes |
| Office Hours | `[MOCK OFFICE HOURS]` | MOCK | No | Yes |
| Domain | `[DOMAIN_NAME]` | UNKNOWN | No | Yes |
| Lawyer Credentials | `[TO BE CONFIRMED]` | UNKNOWN | No | Yes |
| Practice Areas | `[PROVISIONAL_AREA_X]` | PROVISIONAL | No | Yes |
| Biography | `[MOCK BIO]` | MOCK | No | Yes |
| Photograph | `[PLACEHOLDER_IMG]` | PLACEHOLDER | No | Yes |
| Testimonials | Not Applicable | UNKNOWN | No | Yes (If used) |
| Legal Articles | `[MOCK_ARTICLE_X]` | MOCK | No | Yes |
| Legal Disclaimer | `[MOCK_DISCLAIMER]` | MOCK | No | Yes |

================================================================================
## 4. NEVER-FABRICATE DATA (STRICT PROHIBITION)
================================================================================
**The following information must NEVER be invented, hallucinated, or estimated.**

*   Lawyer qualifications, degrees, or universities.
*   Bar Council enrollment details or IDs.
*   Years of experience.
*   Court appearances, case victories, or case outcomes.
*   Client names or client testimonials.
*   Awards, rankings, or certifications.
*   Professional memberships or specific licenses.
*   Legal advice or fee structures.
*   Case statistics or success rates.
*   Claims of being "best", "top", "leading", or "number one".

**Action if unavailable:** Do not invent it. Use an explicit placeholder (e.g., `[TO BE VERIFIED]`), hide the UI section, or leave the field empty.

================================================================================
## 5. DATA THAT MAY BE MOCKED
================================================================================
Mock data is acceptable **only** for development and testing purposes in these categories:
*   Sample blog articles / Legal Insights.
*   Sample FAQ questions.
*   Sample practice-area descriptions (for layout testing).
*   Sample consultation records (inbox testing).
*   Sample contact messages.
*   Sample admin users (dev environment only).
*   Pagination/UI testing data.

================================================================================
## 6. MOCK CONTENT NAMING CONVENTIONS
================================================================================
All mock values must use explicit, machine-detectable identifiers.

**Allowed Prefixes/Markers:**
*   `MOCK_` (e.g., `MOCK_PRACTICE_AREA_CRIMINAL`)
*   `TEST_` (e.g., `TEST_CONSULTATION_REQUEST`)
*   `DEMO_` (e.g., `DEMO_CONTACT_MESSAGE`)
*   `PLACEHOLDER_` (e.g., `PLACEHOLDER_LAWYER_BIO`)
*   `[BRACKETED_CAPS]` (e.g., `[REPLACE_WITH_REAL_EMAIL]`)

**Prohibited Ambiguous Names:**
Do not use names that look real, such as "John Doe", "ABC Law Firm", "15 Years Experience", or realistic-sounding fake Bar Council numbers.

================================================================================
## 7. FRONTEND MOCK DATA RULES
================================================================================
During UI development, if real content is missing:
1.  Use `[MOCK]` labels or bracketed text visibly in the UI.
2.  If a missing piece of data is structurally critical (e.g., a Lawyer Bio section), render the section with an explicit placeholder text: `"Biography content pending verification from Advocate Dhiraj Sawant."`
3.  Do not use generic "Lorem Ipsum" for legal content, as it may accidentally go to production. Use explicit warnings: `[MOCK_LEGAL_DISCLAIMER_PENDING_REVIEW]`.

================================================================================
## 8. BACKEND & DATABASE MOCK DATA RULES
================================================================================
*   **Seed Data:** Database seed scripts (`data.sql` or CommandLineRunners) must only inject records prefixed with `MOCK_` or `TEST_`.
*   Seed data must be safe to truncate/delete.
*   Seed data must never contain real Personally Identifiable Information (PII).
*   **API Responses:** If an API endpoint is mocked, it must return JSON containing identifiable test values (e.g., `{ "status": "MOCK_SUCCESS", "email": "test@example.invalid" }`). The structure must strictly follow `API_CONTRACTS.md`.

================================================================================
## 9. FORM TEST DATA & SENSITIVE DATA RULES
================================================================================
*   **Never use real people's personal information** for testing consultation or contact forms.
*   Use clearly synthetic test values:
    *   Name: `TEST_USER_ALPHA`
    *   Phone: `555-0199` (or clearly invalid regional equivalents)
    *   Email: `testuser@example.invalid` (Use `.invalid` TLD)
*   **Sensitive Data Rule:** Never commit real passwords, API keys, private legal documents, or real client data to source control.

================================================================================
## 10. MOCK ADMIN ACCOUNT
================================================================================
If a development admin account is required to test Spring Security:
*   Username: `admin_dev`
*   Password: `password` (Strictly local dev only)
*   These credentials must NEVER be seeded in a production environment or hardcoded in production configuration files.

================================================================================
## 11. MOCK TESTIMONIAL & CASE RESULT RULES
================================================================================
*   **Testimonials:** If the UI requires a testimonial carousel, use `TESTIMONIAL_TEST_RECORD_01`. Never fabricate praise or fake client names. If no verified testimonials exist at launch, the UI section must be hidden.
*   **Case Results:** Never fabricate case wins or settlement amounts. If testing a "Recent Cases" layout, use `[MOCK_CASE_SUMMARY_DO_NOT_PUBLISH]`.

================================================================================
## 12. SEO MOCK DATA RULES
================================================================================
*   Do not publish fabricated SEO claims (e.g., "Top lawyer in Sangli").
*   Mock metadata (Title tags, Meta descriptions) must be neutral and descriptive based only on verified facts (e.g., "Veritas Chambers - Legal Services in Sangli").

================================================================================
## 13. ENVIRONMENT SEPARATION
================================================================================
*   **Development / Testing:** Mock data is permitted and expected.
*   **Staging:** Mock data is permitted only if the environment is securely isolated and not indexed by search engines.
*   **Production:** NO MOCK DATA IS PERMITTED. Production databases must start clean or with verified reference data only.

================================================================================
## 14. REPLACEMENT PROCESS & PRODUCTION SAFETY GATE
================================================================================
Before any production build or deployment, the following workflow must occur:

1.  Obtain verified information from the user.
2.  Replace the `MOCK_` or `[PLACEHOLDER]` value in the source code or database.
3.  **Automated Search:** The AI/Developer must search the repository for:
    *   `MOCK_`
    *   `TEST_`
    *   `DEMO_`
    *   `[TO BE VERIFIED]`
    *   `[CONTENT TO BE PROVIDED]`
    *   `example.invalid`
4.  If any markers are found in production-bound code, deployment must halt until they are replaced or removed.

================================================================================
## 15. AI AGENT INSTRUCTIONS (ANTIGRAVITY SPECIFIC)
================================================================================
Google Antigravity MUST:
*   Use mock data when required to unblock UI/API development.
*   Clearly identify that data using the naming conventions above.
*   Ask the user for missing factual information rather than fabricating it when factual accuracy matters.
*   Search for remaining mock markers before declaring a project ready for production.

Google Antigravity MUST NOT:
*   Guess missing professional facts.
*   Treat documentation examples as real information.
*   Generate fake social proof or fake legal victories.
*   Remove mock markers without receiving verified replacement data.

================================================================================
## 16. EXAMPLES: BAD VS. GOOD MOCKING
================================================================================

**BAD (Hallucination):** "Dhiraj Sawant has 15+ years of courtroom experience handling complex civil litigation." (When experience is unknown).
**GOOD (Placeholder):** `[LAWYER_BIO_PENDING_VERIFICATION]`

**BAD (Fake Social Proof):** "Veritas Chambers won my property dispute in record time! - Amit Patel"
**GOOD (Test Record):** `"TESTIMONIAL_RECORD_01 - DO NOT PUBLISH"`

**BAD (SEO Stuffing):** "Best number one criminal lawyer in Sangli Maharashtra."
**GOOD (Factual SEO):** "Veritas Chambers - Advocate Dhiraj Sawant - Legal Services in Sangli."

================================================================================
## 17. FINAL PRE-PRODUCTION CHECKLIST
================================================================================
[ ] Lawyer name and Firm name verified.
[ ] Professional credentials and biography verified.
[ ] Practice areas confirmed.
[ ] Phone, Email, Address, and Office Hours verified.
[ ] Domain verified and configured.
[ ] Legal Disclaimer, Privacy Policy, and Terms reviewed.
[ ] All `MOCK_` and `TEST_` records purged from the database.
[ ] All `[PLACEHOLDER]` UI text replaced or hidden.
[ ] Development admin accounts removed or passwords reset.

================================================================================
## 18. DOCUMENT MAINTENANCE
================================================================================
This document is authoritative for how temporary/unverified data is handled. It does not override `PRD_OVERVIEW.md` or `CONSTRAINTS.md`. Update this document only when new data categories are introduced, data verification workflows change, or mock-data leakage is discovered.