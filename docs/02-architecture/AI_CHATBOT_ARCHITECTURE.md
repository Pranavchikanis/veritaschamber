# VERITAS CHAMBERS — AI CHATBOT ARCHITECTURE SPECIFICATION
**Target Agent:** Google Antigravity
**Document Scope:** High-Level Technical Architecture & System Design for AI Chatbot

================================================================================
## 1. EXECUTIVE SUMMARY
================================================================================
This document specifies the architecture and implementation design for an AI chatbot integrated into the Veritas Chambers website. The chatbot acts as an intelligent, conversational interface to the firm's verified information. The initial implementation utilizes the Google Gemini API. Crucially, the architecture strictly separates the chatbot's business logic from the underlying AI provider and implements strict legal safety bounds to prevent hallucinations, fabrication of credentials, or the unauthorized provision of legal advice.

================================================================================
## 2. CURRENT PROJECT CONTEXT
================================================================================
Veritas Chambers is a Spring Boot 3.x modular monolith with a MySQL 8.x database and a Vanilla JS/Bootstrap frontend. The system relies on strict layered architecture (`Controller` → `Service` → `Repository`), RESTful APIs, and DTO boundaries. A Google Form is currently pending completion by Advocate Dhiraj Sawant to collect the actual, verified source-of-truth business data. Therefore, the chatbot must be built to ingest this verified data dynamically from the database, distinguishing between `VERIFIED` and `NOT_YET_VERIFIED` states.

================================================================================
## 3. CHATBOT GOALS
================================================================================
- Answer user questions regarding Veritas Chambers based *only* on verified data.
- Explain general legal terminology in plain language.
- Provide firm contact details, office hours, and consultation procedures.
- Guide users to the appropriate consultation forms or contact numbers.
- Serve as an accessible, high-availability initial touchpoint for prospective clients.

================================================================================
## 4. SCOPE
================================================================================
- Website-only integration via a floating chat UI.
- Backend Spring Boot chat controller and AI service abstraction.
- Integration with Google Gemini via API.
- Database-backed knowledge retrieval.

================================================================================
## 5. NON-GOALS
================================================================================
- WhatsApp integration (deferred to a future phase).
- Establishing an attorney-client relationship.
- Providing definitive, personalized legal advice or predicting case outcomes.
- Replacing the standard consultation request form.

================================================================================
## 6. SYSTEM ARCHITECTURE
================================================================================
The chatbot functionality is integrated directly into the existing Spring Boot monolith.
```mermaid
flowchart TD
    Client[Browser Frontend (Vanilla JS)]
    ChatController[Chat REST Controller]
    ChatService[Chat Service]
    KnowledgeService[Knowledge Service]
    AiService[AI Service Interface]
    GeminiImpl[Gemini AI Implementation]
    MySQL[(MySQL Database)]
    GeminiAPI((Google Gemini API))

    Client -->|POST /api/public/chat| ChatController
    ChatController -->|Request DTO| ChatService
    ChatService -->|Context Request| KnowledgeService
    KnowledgeService -->|Fetch Verified Data| MySQL
    MySQL -->|Entities| KnowledgeService
    KnowledgeService -->|Formatted Context| ChatService
    ChatService -->|Prompt + Context| AiService
    AiService --> GeminiImpl
    GeminiImpl -->|HTTP/REST| GeminiAPI
```

================================================================================
## 7. COMPONENT ARCHITECTURE
================================================================================
- **`ChatController`:** Handles HTTP mapping, rate limiting, and request validation.
- **`ChatRequestDto` & `ChatResponseDto`:** Defines the payload structure.
- **`ChatService`:** Orchestrates the knowledge retrieval, session management, and AI provider interaction.
- **`KnowledgeService`:** Fetches and formats verified database records (LawyerProfile, PracticeAreas, FAQs, Settings).
- **`AiService` (Interface):** Defines the contract for AI text generation.
- **`GeminiAiServiceImpl`:** The concrete implementation interacting with the Gemini API.

================================================================================
## 8. AI PROVIDER ABSTRACTION
================================================================================
To prevent vendor lock-in, the system relies on an interface:
```java
public interface AiService {
    AiResponseDto generateResponse(List<ChatMessage> conversationHistory, String systemContext);
}
```
The `ChatService` interacts only with `AiService`. This allows future integration of OpenAI or Anthropic simply by creating a new `@Service` implementing this interface and swapping the active `@Qualifier` or `@ConditionalOnProperty`.

================================================================================
## 9. GEMINI INTEGRATION ARCHITECTURE
================================================================================
- **Implementation:** `GeminiAiServiceImpl`.
- **API Communication:** Utilizes Spring's `RestClient` or `WebClient` to call the Gemini REST API.
- **Configuration:** API keys and model names are injected via `application.yml` referencing environment variables (`${GEMINI_API_KEY}`). API keys are **never** exposed to the frontend.
- **Error Handling:** Network timeouts or API 5xx errors are caught and transformed into a graceful fallback response ("The assistant is currently unavailable. Please call the office.").

================================================================================
## 10. KNOWLEDGE ARCHITECTURE
================================================================================
The chatbot does not use a massive, hardcoded system prompt.
Information is structured in the database:
- `LawyerProfile`
- `PracticeArea`
- `WebsiteSetting` (Office hours, contact info)
- `FAQ`

Every entity requiring verification must have a `status` field (e.g., `VERIFIED`, `DRAFT`, `NOT_YET_VERIFIED`). 
The `KnowledgeService` strictly filters: `WHERE status = 'VERIFIED'`. If data is `NOT_YET_VERIFIED`, the chatbot is instructed: "State that this information is currently unavailable and direct the user to contact the office."

================================================================================
## 11. KNOWLEDGE RETRIEVAL STRATEGY
================================================================================
**Decision:** Simple Context Retrieval (Dynamic Prompt Injection) rather than RAG (Vector Database).
**Reasoning:** The total volume of information for Veritas Chambers (Practice areas, lawyer bio, basic FAQs, contact info) is small enough (under 10,000 tokens) to be comfortably injected directly into the Gemini context window on every request. Introducing a Vector DB (like Milvus or Pinecone) for a standard law firm website is premature optimization and violates the architectural constraint against unnecessary complexity.

================================================================================
## 12. LEGAL SAFETY ARCHITECTURE
================================================================================
The `systemContext` injected into every AI request must contain strict bounding rules:
1. "You are an informational assistant for Veritas Chambers, not a lawyer."
2. "You must never provide personalized legal advice."
3. "You must never guarantee legal outcomes."
4. "If you do not know the answer based strictly on the provided context, you must state that you do not know and advise the user to book a consultation."
5. "Never invent credentials, fees, or case histories."

================================================================================
## 13. PRIVACY AND DATA HANDLING
================================================================================
- **Data Minimization:** The chatbot UI will explicitly warn users: *"Do not share sensitive, confidential, or personally identifying legal information in this chat."*
- **Logging:** Chat transcripts are **not** persisted to the MySQL database in this phase to avoid liability associated with storing unsolicited confidential legal data. Error logs will strip user input payloads.

================================================================================
## 14. BACKEND API ARCHITECTURE
================================================================================
**Endpoint:** `POST /api/v1/public/chat`
**Access:** Public (Unauthenticated)
**Request Body:**
```json
{
  "sessionId": "uuid-for-context-tracking",
  "message": "Do you handle property disputes?"
}
```
**Response Body:**
```json
{
  "reply": "Yes, Veritas Chambers handles property disputes under our Civil Litigation practice. We assist with partition suits and specific performance. Would you like to know how to book a consultation?",
  "requiresDisclaimer": false
}
```

================================================================================
## 15. CONVERSATION ARCHITECTURE
================================================================================
To maintain conversation memory without persisting to MySQL, the backend utilizes in-memory session caching (e.g., `ConcurrentHashMap` with TTL, or Spring Session) keyed by `sessionId`. The frontend generates a UUID on load and passes it with each request. History is limited to the last 10 turns to conserve tokens.

================================================================================
## 16. FRONTEND CHATBOT UX ARCHITECTURE
================================================================================
- **Placement:** A floating action button (FAB) in the bottom right corner.
- **Design:** Clean, professional, matching the Veritas Chambers brand (dark/gold/white). No generic "robot" avatars.
- **States:**
  - *Loading:* Subtle pulse or typing indicator.
  - *Empty State:* 3-4 suggested quick-action chips ("Where are you located?", "What areas do you practice?").
  - *Error:* Graceful fallback message with a direct link to the Contact page.
- **Accessibility:** Fully keyboard navigable (`Tab`, `Enter`, `Escape` to close), with proper `aria-live` regions for screen readers announcing incoming messages.

================================================================================
## 17. SECURITY ARCHITECTURE
================================================================================
- **API Key Security:** `${GEMINI_API_KEY}` lives strictly in the backend environment.
- **Input Sanitization:** User messages are sanitized to prevent Prompt Injection attacks (e.g., stripping instructions like "Ignore all previous prompts").
- **CORS:** The `/api/v1/public/chat` endpoint respects the global CORS policy, rejecting requests not originating from the Veritas Chambers domain.

================================================================================
## 18. ERROR HANDLING
================================================================================
If Gemini API fails, times out, or returns a 429 Too Many Requests, the `AiService` catches the exception and returns a localized fallback message rather than throwing a 500 Internal Server Error to the client.

================================================================================
## 19. RATE LIMITING AND ABUSE PREVENTION
================================================================================
To prevent API abuse and cost overruns:
- Implement a basic rate limiter (e.g., using Bucket4j) at the `ChatController` level.
- Restrict to X messages per IP address per hour.
- Restrict maximum characters per user message (e.g., 500 characters) to prevent token exhaustion attacks.

================================================================================
## 20. FUTURE WHATSAPP ARCHITECTURE
================================================================================
To support future WhatsApp integration, the architecture isolates the HTTP/Web context from the business logic.
- `ChatService` accepts a standard internal `ChatMessage` object, completely agnostic to whether it came from the Web or WhatsApp.
- In the future, a `WhatsAppWebhookController` will receive Twilio/Meta webhooks, map the WhatsApp payload to the internal `ChatMessage`, and pass it to the exact same `ChatService`.

================================================================================
## 21. IMPLEMENTATION STAGES
================================================================================
**Stage A — Architecture:** (Current) Define the system design.
**Stage B — AI Provider Integration:** Implement `AiService`, `GeminiAiServiceImpl`, and externalized configuration. (No UI).
**Stage C — Knowledge Layer:** Implement `KnowledgeService` to aggregate `VERIFIED` DB entities.
**Stage D — Chat Backend:** Implement `ChatController`, session memory, and rate limiting.
**Stage E — Website Chat UI:** Develop the Vanilla JS/Bootstrap frontend chat panel.
**Stage F — Safety Testing:** Perform adversarial testing (prompt injection, hallucination checks).
**Stage G — Production Hardening:** Finalize rate limits, timeout bounds, and error logging.

================================================================================
## 22. LAWYER INFORMATION DEPENDENCIES
================================================================================
Before the chatbot can go into production, the following data must be provided via the pending Google Form:
### Awaiting Lawyer Verification (Blocking for Chatbot)
- Accurate Firm Name & Core Philosophy.
- Specific Practice Areas and exact matter types handled.
- Consultation rules (Fees, appointments, documents required).
- Office Address, Phone, and Working Hours.
- Explicit constraints on jurisdictions (e.g., "Only Sangli District Court").

### Not Required Yet
- Client Testimonials, Published Legal Articles.

================================================================================
## 23. TESTING STRATEGY
================================================================================
- **Unit Tests:** Verify `KnowledgeService` properly filters out `NOT_YET_VERIFIED` data.
- **Integration Tests:** Mock the Gemini REST API using WireMock to ensure `AiService` gracefully handles timeouts and 5xx errors.
- **Manual QA:** Attempt to force the bot to give fake legal advice; verify the system prompt bounds hold firm.

================================================================================
## 24. PRODUCTION READINESS CRITERIA
================================================================================
- Rate limiting is active.
- Google Form data is received, injected into MySQL, and marked `VERIFIED`.
- System prompt reliably rejects legal-advice solicitations.
- No frontend API key exposure.

================================================================================
## 25. OPEN QUESTIONS
================================================================================
- Which specific Gemini model version should be targeted initially? (Recommendation: `gemini-1.5-flash` for high speed and low latency in conversational flows).

================================================================================
## 26. RECOMMENDED NEXT DEVELOPMENT STAGE
================================================================================
Proceed to **Stage B — AI Provider Integration**, establishing the `AiService` interface and basic Gemini HTTP client connectivity within the Spring Boot backend.
