# VERITAS CHAMBERS: SECURITY RULES
**Target Agent:** Google Antigravity
**Document Scope:** Application Security, Spring Security Config, and Best Practices

================================================================================
## 1. SECURITY OBJECTIVE
================================================================================
The primary objective is to secure the Veritas Chambers public website and its administrative backend. The security model must protect website visitors, sensitive consultation inquiries, administrator credentials, and the integrity of published legal content. 

Security must prioritize Confidentiality (protecting inquiry data), Integrity (preventing unauthorized content modification), and Availability (resisting basic automated abuse), without over-engineering the solution beyond the scope of a modular monolithic Spring Boot application.

================================================================================
## 2. SECURITY PRINCIPLES
================================================================================
1.  **Secure by Default:** All admin endpoints must deny access unless explicitly authorized.
2.  **Least Privilege:** Users and database connections operate with the minimum necessary permissions.
3.  **Never Trust the Client:** Client-side (JS) validation is UX only. Server-side validation is authoritative.
4.  **Zero Secret Exposure:** Credentials and keys must never exist in source code, logs, or public API responses.
5.  **Fail Securely:** Exceptions must be caught and sanitized before reaching the client. No stack traces.
6.  **Data Minimization:** Only collect inquiry data explicitly required to initiate contact.

================================================================================
## 3. SECURITY BOUNDARY
================================================================================
**Flow of Trust:**
`Browser (Untrusted)` → `Spring Security Filter Chain (Authentication/Authorization)` → `REST Controllers (Validation)` → `Services (Business Logic)` → `MySQL (Trusted)`

*   The browser MUST NEVER connect directly to MySQL.
*   The frontend code MUST NEVER contain DB credentials, JWT signing keys, or admin passwords.

================================================================================
## 4. PUBLIC VS ADMIN SECURITY
================================================================================
*   **PUBLIC Endpoints (`/api/v1/public/**`):** Accessible to all. Restricted strictly to reading published content and submitting contact/consultation forms.
*   **ADMIN Endpoints (`/api/v1/admin/**`):** Requires authentication and authorization. Cannot rely on frontend route hiding.
*   **AUTH Endpoints (`/api/v1/auth/**`):** Publicly accessible for login, but rate-limited to prevent brute-force attacks.

================================================================================
## 5. AUTHENTICATION
================================================================================
*   **Framework:** Spring Security 6.x.
*   **Mechanism:** Session-based authentication using secure HttpOnly cookies (standard for Monolithic applications where frontend and backend share the same domain) OR stateless JWTs if deployed across separate origins. *(Note: The final session vs token decision depends on deployment architecture, but the rules below apply to the chosen method).*
*   **Failure Handling:** Authentication failures must return generic `401 Unauthorized` messages. Do not reveal if an email exists in the system.

================================================================================
## 6. PASSWORD SECURITY
================================================================================
*   **Storage:** Passwords must be hashed using `BCryptPasswordEncoder` (or a stronger adaptive encoder like Argon2 if standard in the environment).
*   **Prohibitions:** NEVER store passwords in plaintext. NEVER log them. NEVER return the `password_hash` in an API response (always strip it in the DTO mapper). NEVER commit default passwords to `.data.sql` for production.

================================================================================
## 7. SESSION / TOKEN SECURITY
================================================================================
*   **Session Cookies (If used):** Must have flags: `HttpOnly`, `Secure` (in HTTPS environments), and `SameSite=Strict` or `Lax`.
*   **JWT (If used):** Must be signed with a strong, externalized secret (HMAC-SHA256+). Must have a short expiration (e.g., 1 hour).
*   **Logout:** Must explicitly invalidate the session context or instruct the client to drop the token.

================================================================================
## 8. CSRF PROTECTION
================================================================================
*   **Session-Based Auth:** CSRF protection MUST BE ENABLED for all state-changing operations (POST, PUT, PATCH, DELETE). Do not disable CSRF merely to make API testing easier.
*   **Stateless JWT Auth:** If the architecture is strictly stateless and relies on `Authorization: Bearer <token>` headers instead of cookies, CSRF may be safely disabled, as tokens are not automatically attached by the browser.

================================================================================
## 9. CORS (Cross-Origin Resource Sharing)
================================================================================
*   **Strict Policy:** Do NOT use `Access-Control-Allow-Origin: *` with `allowCredentials(true)`.
*   **Implementation:** Define explicit allowed origins matching the frontend production and development URLs via Spring's `CorsConfigurationSource`.
*   Restrict allowed HTTP methods to exactly what the API requires.

================================================================================
## 10. HTTP SECURITY HEADERS
================================================================================
Spring Security provides secure defaults. Ensure the following are active:
*   `X-Content-Type-Options: nosniff`
*   `X-Frame-Options: DENY` (or `SAMEORIGIN` if framing is required internally) to prevent Clickjacking.
*   `Strict-Transport-Security` (HSTS) in production.
*   `Content-Security-Policy` (CSP) configured to prevent inline script execution where possible.

================================================================================
## 11. HTTPS / TRANSPORT SECURITY
================================================================================
*   Production traffic MUST use HTTPS.
*   Local development may use HTTP on `localhost`.
*   Sensitive tokens and passwords must never traverse unencrypted networks.

================================================================================
## 12. INPUT VALIDATION
================================================================================
*   **Rule:** All data received from the client is hostile.
*   **Implementation:** Use Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@Size`) on all Request DTOs.
*   Controllers must reject malformed JSON and invalid fields with a `400 Bad Request` containing specific field errors, but no internal server details.

================================================================================
## 13. SQL INJECTION PREVENTION
================================================================================
*   **Implementation:** Enforced natively by utilizing Spring Data JPA and Hibernate.
*   **Prohibition:** NEVER construct native SQL queries by concatenating user input strings. If native `@Query` is required, use strict named parameters (e.g., `:email`).

================================================================================
## 14. XSS (Cross-Site Scripting) PROTECTION
================================================================================
*   **Input:** Prevent stored XSS by validating length and formatting of inputs.
*   **Output:** The Vanilla JS frontend must use `.textContent` or `.innerText` when rendering untrusted data (like Contact Messages in the admin panel). NEVER use `.innerHTML` with untrusted data.
*   **Rich Text:** If the Article CMS requires HTML, the backend must sanitize the HTML before persistence using an allowlist library (e.g., OWASP Java HTML Sanitizer), stripping dangerous tags like `<script>` and `onclick` attributes.

================================================================================
## 16. MASS ASSIGNMENT / OVERPOSTING
================================================================================
*   **Rule:** Clients cannot modify fields they aren't authorized to touch.
*   **Implementation:** Achieved by strictly mapping Request DTOs to Entities. Never pass an incoming JSON payload directly to a JPA `save()` method.
*   Internal fields like `id`, `created_at`, or `password_hash` must be omitted from update DTOs.

================================================================================
## 17. API AUTHORIZATION
================================================================================
*   Enforced via `@PreAuthorize("hasRole('ADMIN')")` on service methods or via `SecurityFilterChain` `requestMatchers()`.
*   Public users must be explicitly denied access to `/api/v1/admin/**`.

================================================================================
## 18. OBJECT-LEVEL AUTHORIZATION (IDOR/BOLA)
================================================================================
*   While an Admin can see all consultations, if a future role is added (e.g., a restricted user), knowing an ID (`/consultations/123`) must not bypass authorization checks. The backend must verify the authenticated user has rights to that specific object.

================================================================================
## 20. LOGIN & BRUTE-FORCE PROTECTION
================================================================================
*   Login endpoints must return generic failures: "Invalid email or password."
*   If infrastructure permits, implement basic rate limiting (e.g., via Bucket4j or at the reverse-proxy/Nginx level) to prevent brute-forcing the `/api/v1/auth/login` endpoint.

================================================================================
## 22. BOT / SPAM PROTECTION
================================================================================
*   Public Consultation and Contact forms are targets for bots.
*   Implement server-side validation and consider a simple invisible honeypot field.
*   (CAPTCHA is deferred unless spam becomes an operational issue).

================================================================================
## 23. REQUEST SIZE LIMITS
================================================================================
*   Spring Boot defaults (usually 2MB for request size) should be maintained or lowered to prevent Denial of Service (DoS) via massive payload parsing. Limit text blocks (`message` fields) to reasonable business lengths (e.g., 5,000 characters).

================================================================================
## 24. FILE UPLOAD SECURITY
================================================================================
*   **NO FILE UPLOADS ARE CURRENTLY APPROVED.**
*   If added later, they must enforce strict MIME-type checks, file size limits, extension allowlists, and store files outside the executable path.

================================================================================
## 25. SENSITIVE LEGAL INFORMATION & PRIVACY
================================================================================
*   **Data Minimization:** The intake forms collect only Name, Phone, Email, Subject, and Message.
*   Do NOT collect highly sensitive government IDs, financial records, or detailed evidentiary documents via the public form.

================================================================================
## 27. DATABASE SECURITY
================================================================================
*   The application connects via a least-privilege MySQL user (not `root`).
*   Database credentials must be injected via environment variables (`SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`).

================================================================================
## 29. SECRETS MANAGEMENT
================================================================================
*   **Git Prohibition:** The `.gitignore` must explicitly exclude `.env` files and `application-prod.yml` if it contains secrets.
*   Never commit Database passwords, JWT keys, SMTP passwords, or API keys to the repository.

================================================================================
## 31. LOGGING SECURITY
================================================================================
*   Logs must NEVER contain passwords, password hashes, auth tokens, session cookies, or the full text of confidential consultation messages.

================================================================================
## 32. ERROR HANDLING & INFORMATION DISCLOSURE
================================================================================
*   Standardized error DTOs (`@RestControllerAdvice`) must catch all `Exception` classes.
*   Return a generic `500 Internal Server Error` message to the client. Stack traces must only be visible in secure server logs.

================================================================================
## 40. ADMIN UI SECURITY
================================================================================
*   The frontend Admin UI must react to `401/403` responses by clearing local state and redirecting to the login page.
*   Never store sensitive data (other than required tokens) in `localStorage`.
*   Backend API security is the true boundary; frontend hiding is merely for UX.

================================================================================
## 53. SECURE DEVELOPMENT RULES FOR ANTIGRAVITY
================================================================================
Before modifying code, Google Antigravity MUST:
1. Ensure the change does not expose JPA entities directly to the client.
2. Ensure input is validated via DTOs.
3. Ensure the correct Spring Security role (`ADMIN`) is applied to new endpoints.
4. Ensure no secrets are hardcoded in the generated files.
5. Do not disable CSRF or use wildcard CORS merely to fix a dev environment issue.

================================================================================
## 55. SECURITY ANTI-PATTERNS (PROHIBITED)
================================================================================
*   Plaintext passwords or weak MD5 hashing.
*   Database credentials in frontend JS.
*   `Access-Control-Allow-Origin: *` with credentials.
*   Direct JPA entity exposure in APIs.
*   SQL string concatenation.
*   Trusting client-side validation.
*   Unrestricted key-value data storage.

================================================================================
## 57. SECURITY DOCUMENT OWNERSHIP
================================================================================
`SECURITY_RULES.md` is the authoritative source for security policy, authentication logic, secret handling, input validation, and protection mechanisms.
It does NOT own exact API endpoint paths (`API_CONTRACTS.md`), exact database schema definitions (`DB_SCHEMA.md`), or overall system architecture (`SYSTEM_ARCHITECTURE.md`). In conflicts regarding security enforcement, this document wins.

================================================================================
## 59. FINAL SECURITY SUMMARY
================================================================================
Veritas Chambers utilizes a secure-by-default Spring Security implementation. Authentication ensures only Administrators access content management and confidential inquiries. Passwords are cryptographically hashed using BCrypt. The architecture enforces strict Data Transfer Objects (DTOs) and Jakarta Bean Validation to neutralize injection attacks and mass assignment. The database operates behind a firewall, accessible only by the application using externalized credentials. Public forms are data-minimized to protect client privacy. All error handling is sanitized to prevent information disclosure, ensuring a resilient, professional-grade security posture.