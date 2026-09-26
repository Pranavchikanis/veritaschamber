```markdown
# VERITAS CHAMBERS: PRODUCT REQUIREMENTS DOCUMENT (PRD)
**Target Agent:** Google Antigravity
**Document Scope:** High-Level Product Requirements & Objectives

================================================================================
## 1. EXECUTIVE PRODUCT SUMMARY
================================================================================
The Veritas Chambers website is the official, premium digital presence for Advocate Dhiraj Sawant and his legal practice, Veritas Chambers, located in Sangli, Maharashtra. 

The website solves the problem of digital discoverability and professional credibility for prospective clients seeking legal counsel. Its primary business purpose is to establish a trustworthy online identity, clearly communicate available legal services, and provide a secure, frictionless pathway for clients to request a legal consultation or contact the office. 

Unlike generic lawyer templates or startup-style landing pages, this product is designed to project the traditional authority and intellectual rigor of a serious legal chamber while delivering a modern, accessible, and highly performant user experience.

================================================================================
## 2. PRODUCT VISION
================================================================================
The website will serve as a credible digital representation of Veritas Chambers that seamlessly combines:
**Professional Legal Authority + Trust + Clarity + Modern Usability + Premium Presentation**

A visitor must be able to quickly and intuitively understand:
*   Who Veritas Chambers is.
*   Who Dhiraj Sawant is.
*   What legal services are offered.
*   Where the practice is located (Sangli).
*   How to contact the practice.
*   How to request a consultation.
*   Why the practice should be considered for their legal matter.

*Note: The product will achieve this without making unsupported claims about superiority, rankings, or guaranteed legal outcomes.*

================================================================================
## 3. PRODUCT OBJECTIVES
================================================================================
**PRIMARY OBJECTIVES:**
1.  Establish a strong, professional digital presence for Veritas Chambers.
2.  Build visitor trust immediately upon arrival.
3.  Clearly communicate the specific legal services offered.
4.  Present the lawyer (Dhiraj Sawant) and practice professionally.
5.  Make contacting the practice frictionless and accessible.
6.  Generate and securely capture consultation/contact enquiries.

**SECONDARY OBJECTIVES:**
7.  Provide useful, generalized legal information (Insights) where appropriate.
8.  Support local discoverability for users searching for legal services in Sangli.
9.  Provide a scalable CMS foundation for future content management.
10. Provide a secure administrative interface to manage enquiries and published content.

================================================================================
## 4. TARGET USERS
================================================================================
**CONFIRMED TARGET AUDIENCES:**
*   Individuals seeking legal assistance in Sangli and surrounding areas.
*   Families requiring legal guidance (e.g., property, civil matters).
*   People researching local legal services.
*   Existing clients seeking contact information and office hours.

**PROVISIONAL / TO BE CONFIRMED AUDIENCES:**
*   Business owners.
*   Startups.
*   Corporate/professional clients.

================================================================================
## 5. USER PROBLEMS
================================================================================
Without this product, target users face the following frictions:
*   Struggling to determine whether Advocate Dhiraj Sawant handles their specific type of legal matter.
*   Lacking clear, reliable contact information or office location details.
*   Uncertainty about how to formally request a consultation or what information to provide.
*   Inability to independently assess the professionalism and operational seriousness of the practice before calling.

The website must reduce this uncertainty without ever crossing the line into providing personalized legal advice.

================================================================================
## 6. USER NEEDS
================================================================================
To solve the identified problems, users need:
*   Clear, unambiguous information.
*   A trustworthy, professional visual presentation.
*   Easy, intuitive navigation.
*   Clearly defined Practice Areas.
*   Accurate Lawyer Profile and credentials.
*   Readily available Contact information.
*   A guided Consultation request pathway.
*   Clear Office location details.
*   Mobile usability for on-the-go searches.
*   Fast loading speeds.
*   Accessible content.
*   Clear next actions (CTAs).

================================================================================
## 7. CORE USER JOURNEYS
================================================================================
**Journey 1: General Discovery & Conversion**
Visitor discovers Veritas Chambers -> Understands the practice -> Reviews services -> Reviews lawyer profile -> Contacts the practice.

**Journey 2: Specific Need Resolution**
Visitor has a specific legal requirement -> Navigates to relevant Practice Area -> Determines if the practice handles this -> Requests a consultation.

**Journey 3: Immediate Contact**
Visitor wants to contact the office -> Finds phone/contact details in Header/Footer -> Finds office location -> Calls or visits the practice.

**Journey 4: Due Diligence**
Visitor wants to understand the practice's ethos -> Home -> About -> Lawyer profile -> Practice areas -> Contact.

**Journey 5: Returning User**
Returning visitor -> Opens website -> Quickly finds phone/location/consultation CTA without navigating deep into the site.

**Journey 6: Administration**
Admin -> Authenticates -> Opens dashboard -> Manages permitted website content (e.g., FAQs, Articles) -> Saves changes -> Changes become immediately available on the public website.

================================================================================
## 8. PRODUCT SCOPE (MVP)
================================================================================
**PUBLIC WEBSITE:**
*   Home
*   About Veritas Chambers
*   Lawyer Profile
*   Practice Areas (Directory)
*   Individual Practice Area Pages
*   Legal Insights / Articles (Directory)
*   Individual Article Pages
*   Consultation Request Form
*   Contact Page
*   FAQ
*   Privacy Policy
*   Terms & Conditions
*   Legal Disclaimer

**ADMINISTRATIVE AREA:**
*   Admin Authentication / Login
*   Dashboard (Overview)
*   Lawyer Profile Management
*   Practice Area Management
*   Consultation / Enquiry Management (Inbox)
*   Contact Message Management
*   Article Management (CMS)
*   Article Category Management
*   FAQ Management
*   Testimonial Management (If approved for use)
*   Website Settings
*   Admin User Management

================================================================================
## 9. OUT-OF-SCOPE FEATURES
================================================================================
The following features are **NOT** part of the initial product scope:
*   Full legal case-management software.
*   Public client case tracking.
*   Online legal marketplaces.
*   Legal document generation/marketplaces.
*   Social networks or public discussion forums.
*   Ecommerce or online payment processing.
*   Complex CRM integrations.
*   AI-generated legal advice.
*   Automated legal decision-making or case outcome prediction.
*   Public client records.
*   Unverified online legal consultations presented as legal advice.

================================================================================
## 10. WEBSITE INFORMATION ARCHITECTURE
================================================================================
```text
Home
├── About
├── Lawyer Profile
├── Practice Areas
│   ├── Practice Area Detail
│   └── ...
├── Legal Insights
│   └── Article Detail
├── Consultation
├── Contact
├── FAQ
├── Privacy Policy
├── Terms & Conditions
└── Legal Disclaimer

Admin
├── Authentication
├── Dashboard
├── Lawyer Profile
├── Practice Areas
├── Consultations
├── Contact Messages
├── Articles
├── Categories
├── FAQs
├── Testimonials
├── Settings
└── Admin Users

```

================================================================================

## 11. PAGE-LEVEL PRODUCT REQUIREMENTS

================================================================================
**HOME:**

* *Purpose:* Primary entry point and brand establishment.
* *Primary CTA:* Request Consultation.
* *Secondary CTA:* Explore Practice Areas.
* *Success:* User immediately understands the brand, lawyer, location, and how to proceed.

**ABOUT:**

* *Purpose:* Detail the "Veritas" philosophy and chamber history.
* *Primary CTA:* Contact Us.
* *Success:* User trusts the ethical foundation of the practice.

**LAWYER PROFILE:**

* *Purpose:* Present Dhiraj Sawant's professional credentials.
* *Primary CTA:* Schedule Consultation.
* *Success:* User verifies the lawyer's authority and relevance.

**PRACTICE AREAS & DETAIL PAGES:**

* *Purpose:* Detail specific legal services.
* *Primary CTA:* Book Consultation for this specific area.
* *Success:* User self-qualifies their legal need against the firm's services.

**LEGAL INSIGHTS & ARTICLE DETAIL:**

* *Purpose:* Demonstrate intellectual rigor through published articles.
* *Primary CTA:* Read Article / Book Consultation.
* *Success:* User gains generalized knowledge and views the firm as an authority.

**CONSULTATION / CONTACT:**

* *Purpose:* Secure capture of user enquiries and display of office location.
* *Primary CTA:* Submit Enquiry / Call Now.
* *Success:* Form submitted successfully without errors.

**FAQ:**

* *Purpose:* Answer operational questions (billing, hours, process).
* *Success:* Reduces administrative phone calls for basic queries.

**LEGAL PAGES (Privacy, Terms, Disclaimer):**

* *Purpose:* Risk mitigation and regulatory compliance.
* *Success:* Clear communication that the website does not constitute legal counsel.

================================================================================

## 12. HOME PAGE REQUIREMENTS

================================================================================
The homepage must quickly communicate:

* Veritas Chambers identity (Logo).
* Core value proposition (Truth, Precision, Integrity).
* Lawyer identity (Dhiraj Sawant).
* Legal services/practice areas.
* Trust/professional positioning.
* Consultation/contact pathway.
* Location (Sangli).

*Sections required:* Hero, Introduction, Practice Areas summary, Lawyer snippet, Why Veritas Chambers, Consultation CTA, Recent Insights, Contact/Location, Footer.

================================================================================

## 13. PRACTICE AREA REQUIREMENTS

================================================================================
**CRITICAL REQUIREMENT:** Confirmed Practice Areas are currently `[MOCK / TO BE CONFIRMED]`. **DO NOT INVENT THEM.**

The product must structurally support:

* Practice area title
* Short description
* Detailed description
* Relevant FAQs
* Consultation CTA
* SEO metadata
* Optional related insights

The final list will be supplied and verified by the user prior to production.

================================================================================

## 14. LAWYER PROFILE REQUIREMENTS

================================================================================
**CRITICAL REQUIREMENT:** Lawyer credentials are currently `[MOCK / TO BE CONFIRMED]`. **DO NOT INVENT THEM.**

The profile page must structurally support:

* Name
* Professional title
* Biography
* Credentials (Bar enrollment, education)
* Practice focus
* Professional philosophy
* Contact/consultation CTA
* Professional photograph (placeholder until supplied)

================================================================================

## 15. CONSULTATION FUNCTIONALITY

================================================================================
The consultation form provides a secure pathway for initial contact.
*Provisional Fields:* Name, Phone, Email, Preferred Date, Preferred Time, Legal Matter/Category, Brief Description, Preferred Contact Method.

*Requirements:*

* Must include clear consent/privacy messaging.
* Must provide strict input validation.
* Must have clear success and failure states.
* Submitted information must be protected and routed to the Admin panel.
* Must explicitly state that submitting the form does not create an attorney-client relationship.

================================================================================

## 16. CONTACT FUNCTIONALITY

================================================================================
Provides immediate operational details to the user.
*Requirements:*

* Phone (+91 89835 12124)
* Email (`[MOCK EMAIL]`)
* Office address (Opposite building of Vijay Nagar Court, below Aurum Films, Ground Floor, Sangli.)
* Map/location support where appropriate.
* Contact form.
* Office hours (`[MOCK OFFICE HOURS]`).

================================================================================

## 17. LEGAL INSIGHTS / BLOG

================================================================================
Allows the practice to publish useful legal information.
*Requirements:*

* Support for Title, Category, Author, Date, Featured Image, Summary, Content, and SEO metadata.
* Articles must include a disclaimer that they are not personalized legal advice.
* Admin users must be able to draft, publish, edit, and delete articles.

================================================================================

## 18. FAQ

================================================================================
Answers common operational questions about the practice, consultations, contact, the office, and general processes.
*Requirements:*

* Content must not provide individualized legal advice.
* Must be fully editable via the Admin system.

================================================================================

## 19. ADMIN PRODUCT REQUIREMENTS

================================================================================
The admin system allows authorized administrators to manage approved public-facing content and enquiries without modifying application source code.
*Requirements:*

* **Dashboard:** Overview of system activity.
* **Lawyer Profile / Practice Areas / FAQs / Testimonials / Settings:** Full CRUD capability to update public information.
* **Consultations / Contact Messages:** Inbox to view, status-track, and safely delete client enquiries.
* **Articles / Categories:** Content management system for Legal Insights.
* **Admin Users:** Ability to manage who has access to the dashboard.

================================================================================

## 20. CONTENT MANAGEMENT REQUIREMENTS

================================================================================
The CMS must distinguish between:

* Verified content
* Mock content (during development)
* Draft content
* Published content

The product must prevent mock information from accidentally being treated as verified production information. (Refer to `MOCK_DATA_STRATEGY.md`).

================================================================================

## 21. BRAND EXPERIENCE REQUIREMENTS

================================================================================
The website must evoke a Premium, Serious, Sophisticated, Trustworthy, Professional, Calm, Precise, Discreet, Modern, and Legally Credible aesthetic.

**Avoid:** Generic templates, overly playful interfaces, Startup/SaaS visual language, excessive animation, and unnecessary decorative visual noise.

================================================================================

## 22. TRUST REQUIREMENTS

================================================================================
Trust is the primary product objective. It is built through:

* Clear identity and transparent information.
* Professional presentation and accurate contact details.
* Appropriate legal disclaimers.
* Secure handling of user enquiries.
* **Absolute prohibition of fabricated credentials, fake reviews, or manufactured social proof.**

================================================================================

## 23. LOCAL PRESENCE

================================================================================
The product must support local discoverability in Sangli, Maharashtra.
*Requirements:*

* Clear presentation of the Sangli office address and local contact details.
* Local SEO fundamentals.
* **Prohibited:** Unsupported marketing claims (e.g., "Best lawyer in Sangli", "No. 1 lawyer").

================================================================================

## 24. SEO PRODUCT REQUIREMENTS

================================================================================
The website must be structured for discoverability without misleading users.
*Requirements:*

* Dynamic Page titles and Meta descriptions.
* Semantic headings (H1, H2, H3).
* Clean URLs.
* Image alt text.
* Sitemap and Robots configuration.
* Open Graph metadata.

================================================================================

## 25. ACCESSIBILITY REQUIREMENTS

================================================================================
The product must be usable by people with different accessibility needs.
*Requirements:* Keyboard navigation, semantic structure, form labels, visible focus states, sufficient color contrast, alternative text for images, and accessible error states.

================================================================================

## 26. PERFORMANCE REQUIREMENTS

================================================================================
The website must prioritize fast page loading, optimized images, efficient assets, minimal unnecessary JavaScript, and highly responsive mobile performance.

================================================================================

## 27. RESPONSIVE REQUIREMENTS

================================================================================
The website must provide a seamless experience across Mobile, Tablet, Laptop, and Desktop viewports. Critical journeys (Navigation, Practice Discovery, Consultation, Contact) must remain fully functional and frictionless on small screens.

================================================================================

## 28. SECURITY AND PRIVACY PRODUCT REQUIREMENTS

================================================================================

* Public users must only access public information.
* Admin functionality must be strictly restricted to authenticated users.
* Contact and consultation submissions must be protected in transit and at rest.
* Sensitive information must not be publicly exposed.
* The website must provide appropriate privacy and legal disclaimers.

================================================================================

## 29. FUNCTIONAL REQUIREMENTS

================================================================================

* **FR-001:** The system shall allow visitors to view the Veritas Chambers homepage.
* **FR-002:** The system shall allow visitors to view the lawyer profile.
* **FR-003:** The system shall allow visitors to view available practice areas.
* **FR-004:** The system shall allow visitors to submit a contact enquiry form.
* **FR-005:** The system shall allow visitors to submit a consultation request form.
* **FR-006:** The system shall display validation errors if required form fields are missing or invalid.
* **FR-007:** The system shall allow visitors to read published Legal Insights and FAQs.
* **FR-008:** The system shall restrict the `/admin` dashboard to authenticated users only.
* **FR-009:** The system shall allow admins to view submitted consultations and contact messages.
* **FR-010:** The system shall allow admins to create, edit, draft, and publish Legal Insights.
* **FR-011:** The system shall allow admins to update Practice Areas and FAQ content.
* **FR-012:** The system shall allow admins to update global website settings (Phone, Email, Hours).

================================================================================

## 30. NON-FUNCTIONAL REQUIREMENTS

================================================================================

* **NFR-001 (Performance):** The public website shall load quickly and efficiently on mobile networks.
* **NFR-002 (Security):** The system shall protect against SQL injection, XSS, and CSRF attacks.
* **NFR-003 (Accessibility):** The UI shall meet basic WCAG contrast and keyboard navigation standards.
* **NFR-004 (Responsiveness):** The UI shall scale dynamically from 320px mobile screens to 4K desktop monitors.
* **NFR-005 (SEO):** The system shall output valid HTML5 with appropriate semantic tags and metadata.
* **NFR-006 (Privacy):** The system shall not expose PII collected via forms to public endpoints.

================================================================================

## 31. CONTENT REQUIREMENTS

================================================================================
**Required Content Categories:**

* Firm information
* Lawyer biography
* Practice areas
* Consultation information
* Contact information
* FAQs
* Legal Insights
* Legal disclaimer, Privacy policy, Terms and conditions

**UNAVAILABLE INFO (MOCK PLACEHOLDERS REQUIRED):**

* Lawyer credentials (`[MOCK DATA]`)
* Confirmed practice areas (`[TO BE CONFIRMED]`)
* Official email (`[MOCK EMAIL]`)
* Office hours (`[MOCK OFFICE HOURS]`)
* Domain (`[DOMAIN_NAME]`)

================================================================================

## 32. DATA / CONTENT SAFETY

================================================================================
**ABSOLUTE PROHIBITION:** AI agents and developers must NEVER invent fabricated professional information. Do not invent qualifications, Bar enrollment, experience, case results, client names, awards, testimonials, rankings, certifications, or memberships. Refer to `MOCK_DATA_STRATEGY.md`.

================================================================================

## 33. MVP DEFINITION

================================================================================
**MVP REQUIRED:**

1. Professional public website architecture.
2. Clear lawyer/practice identity.
3. Practice-area presentation.
4. Lawyer profile.
5. Contact functionality & Location info.
6. Consultation/enquiry pathway.
7. Basic Legal Insights capability.
8. FAQ capability.
9. Secure administrative content management (CMS + Inbox).
10. Responsive and accessible experience.
11. Basic SEO.
12. Required legal/privacy pages.

================================================================================

## 34. FUTURE ENHANCEMENTS

================================================================================
**FUTURE CONSIDERATION (Not in MVP):**

* Advanced analytics.
* CRM integrations.
* Appointment/calendar integrations (e.g., Calendly/Google Calendar sync).
* Email/SMS auto-notifications for clients.
* WhatsApp API integration.
* Advanced search functionality.
* Multilingual content support.
* Secure Client Document Portal.

================================================================================

## 35. SUCCESS CRITERIA

================================================================================
The product is successful when:

* Visitors can understand the practice and identify relevant services within seconds.
* Visitors can easily find the lawyer's information and office location.
* Consultation enquiries can be submitted successfully and securely without errors.
* Administrators can intuitively manage approved content without touching code.
* The website is highly performant on mobile and desktop.
* **No fabricated professional claims are displayed.**
* Public/private data boundaries are strictly respected.
* The website visually presents a premium, trustworthy legal brand.

================================================================================

## 36. ACCEPTANCE CRITERIA

================================================================================

* **AC-001:** A visitor can reach the primary consultation/contact action from the homepage.
* **AC-002:** A visitor can identify the legal practice and lawyer.
* **AC-003:** A visitor can navigate to relevant practice areas.
* **AC-004:** A visitor can access contact and office information.
* **AC-005:** Consultation/contact forms provide clear validation and submission feedback.
* **AC-006:** Administrative functionality is not publicly accessible.
* **AC-007:** Mock/unverified information is not presented as verified fact.
* **AC-008:** The website remains usable on mobile, tablet, and desktop.

================================================================================

## 37. PRODUCT RISKS

================================================================================

* **Risk:** Unverified lawyer information or practice areas presented as fact.
* *Impact:* Severe reputational and ethical risk.
* *Mitigation:* Strict adherence to MOCK placeholder strategies.


* **Risk:** Privacy/confidentiality breach of consultation data.
* *Impact:* Critical trust and legal risk.
* *Mitigation:* Implementation of Spring Security, protected admin routes, no public data exposure.


* **Risk:** Poor mobile UX preventing conversions.
* *Impact:* Loss of prospective clients.
* *Mitigation:* Mobile-first testing of navigation and form submission.


* **Risk:** Scope expansion (trying to build a CRM).
* *Impact:* Delayed launch.
* *Mitigation:* Strict adherence to the MVP scope defined in this PRD.



================================================================================

## 38. PRODUCT DEPENDENCIES

================================================================================
**USER/CONTENT DEPENDENCIES:**

* Verified lawyer information and professional photograph.
* Confirmed list of practice areas.
* Official email and final office hours.
* Final legal/disclaimer content.
* Final domain choice.

**TECHNICAL DEPENDENCIES:**

* Production hosting/deployment environment decisions.
* Logo/brand asset provisioning.

================================================================================

## 39. ASSUMPTIONS

================================================================================

* *Assumption:* "Veritas Chambers" is the final, official practice name.
* *Assumption:* Dhiraj Sawant is the primary/sole lawyer represented by the website at launch.
* *Assumption:* Sangli is the primary target location for local SEO.
* *Assumption:* The supplied phone number (+91 89835 12124) is the intended contact number.
* *Assumption:* The logo supplied in the project workspace is the final official logo.
* *Assumption:* All `[MOCK]` information will be replaced by the user before production deployment.

================================================================================

## 40. OPEN QUESTIONS

================================================================================

1. What will be the final domain name?
2. What is the official contact email address?
3. What are the specific Lawyer credentials and Bar Enrollment details?
4. What are the confirmed Practice Areas to be listed?
5. What are the exact office hours?
6. Will a professional photograph be supplied?
7. What is the exact wording for the Legal Disclaimer, Privacy Policy, and Terms?
8. Will client testimonials be utilized (subject to Bar Council rules)?
9. Will the Legal Insights (Blog) module be populated with content at launch?
10. What are the final production deployment infrastructure details?

================================================================================

## 41. PRODUCT BOUNDARIES

================================================================================
The Veritas Chambers website is a professional digital presence and enquiry/consultation platform.
**It is explicitly NOT:**

* A substitute for qualified legal counsel.
* An automated legal-advice system.
* A legal case-management or tracking system.
* A public database of court cases or client records.
* A secure client document portal (unless approved in a future phase).

================================================================================

## 42. DOCUMENT MAINTENANCE RULES

================================================================================
`PRD_OVERVIEW.md` changes **only** when product requirements or scope change.
Do NOT update this document for: Minor bug fixes, CSS changes, code refactoring, internal implementation details, or temporary development states. Update it only when there is a meaningful change to Product Scope, User Requirements, Major Functionality, MVP Definition, or Product Boundaries.

================================================================================

## 43. PRD SOURCE-OF-TRUTH RULE

================================================================================
This document is the authoritative source for high-level product requirements. It dictates WHAT is being built.
It **does not** override:

* Explicit instructions from the user.
* `CONSTRAINTS.md` for hard technical limits.
* `SECURITY_RULES.md` for security implementation.
* `DB_SCHEMA.md` and `API_CONTRACTS.md` for technical contracts.
* `BRAND_AND_UI.md` for visual rules.

When conflicts occur, they must be identified and resolved, not silently ignored.

================================================================================

## 44. FINAL PRODUCT SUMMARY

================================================================================
We are building a premium, highly secure, monolithic Java/Spring Boot web platform for Veritas Chambers (Advocate Dhiraj Sawant) in Sangli, Maharashtra. The MVP provides a professional digital presence, details the firm's practice areas, and offers a secure pathway for prospective clients to request legal consultations. It includes a protected admin dashboard for content and enquiry management. It intentionally excludes complex case-management or automated legal advice features. The product relies on strict mock-data placeholders for credentials, domains, and specific content until the user verifies and supplies the final production data.

```

```