```markdown
# VERITAS CHAMBERS: BRAND & UI DESIGN SYSTEM
**Target Agent:** Google Antigravity
**Document Scope:** Visual Identity, UI Components, and Frontend Design Rules

================================================================================
## 1. BRAND POSITIONING & PERSONALITY
================================================================================
**Brand Name:** Veritas Chambers (Latin for truth, reality, accuracy).
**Brand Promise:** An elite, disciplined legal practice grounded in truth, facts, integrity, and strategic counsel.

**Visual Principle: "Quiet Authority"**
The website must not rely on visual noise to communicate importance. It relies on precise alignment, generous whitespace, rigorous typography, and an editorial, architectural composition.

**Primary Traits:** Authoritative, Precise, Trustworthy, Sophisticated, Discreet.
**Prohibited Traits:** Flashy, Loud, Sales-heavy, Startup-like, Neon, Aggressive, Cheap.

================================================================================
## 2. LOGO SYSTEM
================================================================================
The official Veritas Chambers logo (provided in the workspace) is the inviolable anchor of the brand.

*   **Primary Usage:** Placed in the top-left of the header and centered in the footer.
*   **Clear Space:** Maintain a padding equivalent to the height of the "V" around the logo at all times.
*   **Prohibitions:** Do NOT redraw, stretch, distort, rotate, or apply unapproved CSS filters (e.g., drop-shadows) to the logo file. Do not alter its typography.

================================================================================
## 3. COLOR SYSTEM
================================================================================
*(Note: Exact HEX values must be confirmed against the supplied logo. The palette below is the architectural standard derived from the brand positioning).*

**Palette Objective:** Deep, serious, and warm. No neon or cyberpunk colors.

*   **Primary (Brand Anchor):** Deep Navy / Charcoal (e.g., `#0F172A`)
*   **Background (Surface):** Warm Ivory / Off-White (e.g., `#FAFAF9`)
*   **Text Primary:** Near Black (e.g., `#1E293B`)
*   **Text Secondary/Muted:** Slate Gray (e.g., `#64748B`)
*   **Accent/Interactive:** Muted Gold / Champagne (e.g., `#D4AF37`) — *Use sparingly for Primary CTAs and subtle active states.*
*   **Borders/Dividers:** Soft Gray (e.g., `#E2E8F0`)

*Rule: Dark mode is DEFERRED. Do not implement a dark mode toggle unless explicitly required, as editorial legal designs often rely on high-contrast black-on-white.*

================================================================================
## 4. TYPOGRAPHY SYSTEM
================================================================================
The typography balances classical authority with modern digital readability.

*   **Display / Headings (H1, H2, H3):** A refined Serif (e.g., *Playfair Display*, *Merriweather*, or *Lora*).
*   **Body / UI / Metadata:** A highly readable Sans-Serif (e.g., *Inter*, *Lato*, or *Roboto*).

**Typographic Character:**
*   Strong hierarchy.
*   Moderate line-height for body text (`1.6` to `1.8`).
*   Restrained uppercase usage (e.g., for tiny metadata labels or tiny subheaders, always with tracking/letter-spacing).
*   **Avoid:** Script fonts, overly condensed fonts, or excessive ALL CAPS in body copy.

================================================================================
## 5. DESIGN TOKENS (CSS VARIABLES)
================================================================================
All styling should map back to CSS Custom Properties declared in a `:root` block to ensure Bootstrap 5 overrides remain consistent.

```css
:root {
  /* Colors */
  --vc-primary: #0F172A;
  --vc-bg-surface: #FAFAF9;
  --vc-text-main: #1E293B;
  --vc-text-muted: #64748B;
  --vc-accent: #D4AF37;
  --vc-border: #E2E8F0;

  /* Typography */
  --vc-font-serif: 'Playfair Display', serif;
  --vc-font-sans: 'Inter', sans-serif;

  /* Spacing Scale (rem-based) */
  --vc-space-xs: 0.5rem;   /* 8px */
  --vc-space-sm: 1rem;     /* 16px */
  --vc-space-md: 2rem;     /* 32px */
  --vc-space-lg: 4rem;     /* 64px */
  --vc-space-xl: 8rem;     /* 128px */

  /* UI Elements */
  --vc-radius: 2px;        /* Very subtle, architectural edges */
  --vc-shadow-subtle: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
  --vc-transition: all 0.3s ease-in-out;
}

```

================================================================================

## 6. LAYOUT & SPACING SYSTEM

================================================================================

* **Grid:** Utilize Bootstrap 5's responsive container and 12-column grid.
* **Whitespace:** Prefer generous padding (`--vc-space-lg` and `--vc-space-xl`) between major sections to let the content breathe.
* **Content Width:** Cap long-form reading text (like Articles or Biographies) at roughly `65-75 characters` wide (approx `max-width: 800px`) for optimal readability.

================================================================================

## 7. UI COMPONENTS

================================================================================

### A. Buttons

* **Primary:** Solid `--vc-primary` background, white text. Hover state slightly lightens the background.
* **Secondary/Outline:** Transparent background, solid `--vc-primary` border, `--vc-primary` text.
* **Shape:** Sharp or very slightly rounded (`2px`). Absolutely NO pill-shaped (fully rounded) buttons.
* **Microcopy:** Professional ("Request Consultation", "Contact Office"). No manipulative hype ("ACT NOW!").

### B. Cards (Practice Areas, Articles)

* **Surface:** Clean, flat background.
* **Border:** Subtle 1px `--vc-border`.
* **Shadow:** None by default. A very subtle shadow (`--vc-shadow-subtle`) may appear on hover to indicate interactivity.
* **Rule:** Avoid heavy drop-shadows and glassmorphism entirely.

### C. Forms (Consultation / Contact)

* **Inputs:** Clean borders (`1px solid var(--vc-border)`).
* **Focus State:** Border changes to `--vc-primary` with a subtle box-shadow. Do NOT remove focus outlines.
* **Labels:** Required. Placed clearly above inputs.

### D. Navigation & Header

* **Style:** Clean, minimal. White or `--vc-bg-surface` background.
* **Links:** Sans-serif, medium weight. Hover state introduces a subtle underline or color shift to `--vc-accent`.
* **Sticky:** If the header is sticky, it must have a subtle bottom border or shadow upon scrolling to separate from content.

================================================================================

## 8. IMAGERY & ICONOGRAPHY

================================================================================

* **Photography:** Authentic, architectural, editorial portraits, and refined office interiors.
* **Prohibited Imagery:** Cheesy gavels, generic scales of justice, AI-generated legal clichés, or staged aggressive handshakes.
* **Image Treatment:** Square or slightly rounded corners (`2px`). Use subtle grayscale or lowered saturation if images clash with the sophisticated color palette.
* **Icons:** Restrained, line-based, consistent stroke weight (e.g., Feather Icons or Phosphor). No cartoonish or emoji-style icons.

================================================================================

## 9. ACCESSIBILITY (WCAG 2.2 AA)

================================================================================
Accessibility is mandatory.

* **Contrast:** All text must meet at least a 4.5:1 contrast ratio against its background.
* **Focus States:** Keyboard focus (`:focus-visible`) MUST be distinctly visible. Never use `outline: none` without a substitute visual indicator.
* **Semantic HTML:** Use `<header>`, `<main>`, `<article>`, `<footer>`. Form inputs must have associated `<label>` tags.
* **Alt Text:** All images must have descriptive `alt` attributes. Decorative images should use `alt=""`.

================================================================================

## 10. MOTION & INTERACTION DESIGN

================================================================================

* **Style:** Motion must be subtle, fast, and professional (fades, slight upward translates).
* **Prohibitions:** No bouncy animations, continuous spinning, heavy parallax, or elaborate scroll-jacking.
* **Accessibility:** Respect `@media (prefers-reduced-motion: reduce)`. When active, all non-essential transition durations must instantly default to `0s`.

================================================================================

## 11. RESPONSIVE BEHAVIOR

================================================================================
Mobile experience is a first-class citizen.

* **Typography:** Scale down heading sizes on mobile to prevent text wrapping into awkward broken lines.
* **Navigation:** Collapse into a clean, accessible Hamburger menu on screens `< 992px` (Bootstrap `lg`).
* **Touch Targets:** All buttons and links must be at least `44x44px` on mobile devices.
* **Contact Info:** The phone number must be a clickable `tel:` link, highly visible on mobile.

================================================================================

## 12. DATA STATES (ERROR, LOADING, EMPTY)

================================================================================

* **Error States:** Calm, actionable text. (e.g., "Please provide a valid email address.") No red neon text. Use a muted crimson/brick red.
* **Loading:** Use subtle CSS spinners or skeleton loaders. No elaborate loading animations.
* **Empty States:** If an API returns no articles, display a branded, professional message: "No legal insights are currently available."

================================================================================

## 13. CONTENT DESIGN & MOCK DATA

================================================================================
**CRITICAL:** Google Antigravity MUST NOT invent legal claims, practice areas, or credentials.

* If testing the layout of the Lawyer Profile, use `[LAWYER_BIO_PENDING_VERIFICATION]` rather than generating a fake 15-year career history.
* If testing Testimonial cards (if approved), use `[TESTIMONIAL_TEST_RECORD]`. Do not fake client praise.

================================================================================

## 14. ADMIN UI DESIGN

================================================================================
The Admin Dashboard should share the same typography and color tokens but optimize for data density and efficiency.

* Use standard data tables for inquiries.
* Use Bootstrap utility classes for layout.
* Do not over-decorate the admin panel; clarity is the priority.

================================================================================

## 15. ANTIGRAVITY IMPLEMENTATION RULES

================================================================================

1. **Bootstrap 5:** Use Bootstrap classes (`mt-5`, `py-4`, `d-flex`) for structure to minimize custom CSS.
2. **Custom CSS:** Only write custom CSS in `main.css` to define the Design Tokens (Section 5) and override specific Bootstrap components to match the "Quiet Authority" aesthetic. Avoid `!important`.
3. **Vanilla JS:** Use plain JavaScript for mobile menu toggles and form submissions. DO NOT introduce React, Vue, or jQuery.
4. **No Clichés:** Do not add scales of justice icons unless specifically requested.

================================================================================

## 16. SOURCE-OF-TRUTH RULE

================================================================================
`BRAND_AND_UI.md` is the authoritative source for visual identity, typography, CSS architecture, and frontend interaction rules.
It does **NOT** override `CONSTRAINTS.md`, `API_CONTRACTS.md`, `DB_SCHEMA.md`, or `SECURITY_RULES.md`. If a visual design preference compromises security or backend architecture, the backend requirement takes precedence.

```

```