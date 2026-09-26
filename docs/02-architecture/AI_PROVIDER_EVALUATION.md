# VERITAS CHAMBERS — AI PROVIDER EVALUATION
**Target Agent:** Google Antigravity
**Document Scope:** Rigorous evaluation of AI API providers to determine the optimal initial integration for the Veritas Chambers website chatbot.

## 1. Evaluation Date
September 20, 2026

## 2. Project Requirements
The Veritas Chambers chatbot is a public-facing conversational agent on a professional legal website. The primary requirements are:
- **Strict Privacy:** Users may inadvertently submit sensitive legal information despite warnings. The provider must guarantee that API inputs/outputs are NOT used for model training.
- **Reliability:** The chatbot must respond consistently without dropping requests due to free-tier capacity limits (e.g., 503 errors).
- **Latency:** As a website widget, response times must be fast to maintain a good user experience.
- **Reasoning:** Frontier-level reasoning (e.g., GPT-4, Opus) is unnecessary. The bot only needs to retrieve verified context (FAQs, Practice Areas) and refuse to give personalized legal advice.
- **Architecture:** The application uses an `AiService` abstraction to prevent vendor lock-in.

---

## 3. Provider Comparison

| Feature | Google Gemini API | Groq API | OpenRouter API |
| :--- | :--- | :--- | :--- |
| **Primary Advantage** | Deep context window, natively multimodal | Blisteringly fast inference | One API for 500+ models |
| **Free Tier Available** | Yes | Yes | Yes (Limited) |
| **Data Training (Free)** | **Yes (Major Privacy Risk)** | No (Data is ephemeral) | Upstream provider dependent |
| **Data Training (Paid)** | No | No | No (Opt-out available) |
| **Latency** | Moderate (1-3s) | Extremely Low (< 0.5s) | Variable (Depends on upstream) |

---

## 4. Gemini Evaluation
Google's Gemini API offers powerful models (`gemini-1.5-flash`) with massive context windows (1M+ tokens). 
- **Strengths:** Excellent instruction following; native JSON mode; very cheap paid tier ($0.075 / 1M input tokens).
- **Limitations:** The Free Tier explicitly allows Google to use API prompts and responses to train their models. Furthermore, the free tier is plagued by intermittent `503 Service Unavailable` errors during high global demand.

## 5. Groq Evaluation
Groq uses custom LPU (Language Processing Unit) hardware to serve open-source models (like Meta's Llama 3.1 8B/70B) at unprecedented speeds.
- **Strengths:** Near-instantaneous response times (perfect for website chatbots); very generous free tier; cheap paid tier; does not use API data for training.
- **Limitations:** Strict organization-level rate limits on the free tier; smaller context windows compared to Gemini (though easily sufficient for this project).

## 6. OpenRouter Evaluation
OpenRouter acts as a unified gateway to nearly all LLM providers (OpenAI, Anthropic, Google, Meta).
- **Strengths:** Ultimate flexibility. You integrate OpenRouter once, and can swap between Gemini, Llama, and Claude simply by changing a string in the configuration.
- **Limitations:** Requires a $10 minimum deposit to unlock meaningful free-tier rate limits (1,000 requests/day). Introduces an extra network hop (slightly higher latency).

---

## 7. Free-Tier Analysis
- **Gemini Free:** Highly capable, but legally disqualified for this project because user inputs are subject to human review and model training.
- **Groq Free:** Excellent for development and early production. No credit card required, fast, and privacy-respecting.
- **OpenRouter Free:** Too restricted (50 requests/day) without a $10 top-up, and free model routing is subject to heavy capacity limits.

## 8. Rate-Limit Analysis
- **Gemini (Free):** ~15 Requests Per Minute (RPM), 1,500 Requests Per Day (RPD).
- **Groq (Free):** 30 RPM, 14,400 RPD (for Llama 3 8B).
- **OpenRouter (Free):** 20 RPM, 50 RPD (increases to 1,000 RPD after $10 spend).

## 9. Reliability Analysis
- **Gemini Free Tier:** Known to aggressively return `503 Service Unavailable` or `429 Too Many Requests` when Google's capacity is constrained.
- **Groq Free Tier:** Returns strict `429` errors if the 30 RPM limit is exceeded, but otherwise highly reliable and rarely goes down.
- **OpenRouter:** If querying a free model through OpenRouter, `429` errors are common because the upstream provider is saturated. Paid models through OpenRouter are highly reliable.

## 10. Model Suitability
For retrieving practice areas and refusing legal advice, **Llama 3.1 8B** (via Groq or OpenRouter) or **Gemini 1.5 Flash** are perfectly suited. They are cheap, fast, and follow system prompts rigorously. Frontier models (GPT-4o) would be a waste of money for this use case.

## 11. API Integration Analysis
All three provide standard REST APIs.
- **Groq & OpenRouter:** Both are 100% compatible with the OpenAI REST API specification. This means a standard Java HTTP client can easily send a chat completion request to either provider using the exact same JSON structure.
- **Gemini:** Uses a proprietary Google JSON structure, requiring a slightly different DTO mapping in Java.

## 12. Privacy/Data Handling Analysis (CRITICAL)
As a law firm website, users *will* ignore disclaimers and submit sensitive personal data.
- Using **Gemini's Free Tier** is a critical security and privacy violation, as Google logs this data for training.
- To use Gemini, the client MUST use the **Paid Tier**, which explicitly excludes API data from training.
- **Groq** and **OpenRouter** (on paid tiers) offer zero-data-retention policies, meaning they process the prompt and immediately discard it.

## 13. Cost Analysis (Paid Tier Projections)
Assuming 100 conversations/day (approx. 500 API calls, using 1,000 input tokens and 200 output tokens per call):
- **Groq (Llama 3.1 8B):** ~$0.05 / million tokens = **~$0.003 / day**.
- **Gemini 1.5 Flash:** ~$0.075 / million input = **~$0.005 / day**.
*Conclusion:* On paid tiers, the AI costs for a small legal website are effectively zero (under $0.20 per month). There is no financial justification for risking client privacy on a free tier.

---

## 14. Failure Handling
The `AiService` implementation must handle:
- **429 (Too Many Requests):** Implement an exponential backoff retry (e.g., wait 1s, then 2s, max 3 retries).
- **503 / 500 / Timeout:** Catch the exception, do NOT retry endlessly. Return a graceful fallback to the user: *"I am currently experiencing technical difficulties. Please contact Veritas Chambers directly at [Phone Number]."*

---

## 15. Recommended Provider
**Recommendation: GROQ API (using Llama 3.1 8B)**
**Why:**
1. **Speed:** Groq's sub-second latency provides a superior user experience for a website chatbot.
2. **Privacy:** Groq's API policies protect user data without forcing the developer to configure complex paid Google Cloud billing accounts immediately.
3. **OpenAI Compatibility:** Because Groq uses the OpenAI REST schema, writing the Java integration once means we can easily swap to OpenAI or OpenRouter in the future with zero code changes.

## 16. Recommended Provider Architecture
```java
AiService
   │
   ├── GroqAiServiceImpl (Primary, implements OpenAI REST schema)
   └── GeminiAiServiceImpl (Deferred, unnecessary right now)
```

## 17. Future Fallback Strategy
If Groq experiences an outage, the `ChatService` can catch the exception. In a future stage, an `OpenRouterAiServiceImpl` can be implemented as a fallback to route the request to a different provider. For Stage 1, a simple graceful failure message to the user is sufficient.

## 18. Risks and Mitigations
- **Risk:** Users submit sensitive data.
  **Mitigation:** Use a paid/privacy-respecting API (Groq). Explicitly instruct the AI not to acknowledge sensitive data and to purge it from its conversational context.
- **Risk:** Token exhaustion attacks.
  **Mitigation:** The `ChatController` must truncate user input to 500 characters and limit conversation history to 10 turns.

---

## 19. Decision
The Veritas Chambers chatbot will be implemented using the **Groq API** as the primary provider, utilizing the `Llama-3.1-8b-instant` model via standard REST calls. 

## 20. Next Implementation Stage
**Stage AI-02 (Groq Provider Integration)**
- Implement `AiService`.
- Implement `GroqAiServiceImpl` using Spring `RestClient`.
- Configure `application.yml` for Groq API keys and base URLs.
- *Wait to proceed until the Google Form data is verified.*
