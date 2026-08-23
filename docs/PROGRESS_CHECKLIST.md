# TU Circles — MVP Progress Checklist

## MVP Modules Progress

- [x] **Identity Verification (TU API Integration)**
  - Module folder: `backend/src/main/java/th/ac/tu/circles/identity`
  - Migration file: `backend/src/main/resources/db/migration/V1__init_identity.sql`
  - Merged PR / Commit: PR #14 (4086918)
  - Blocker / Note: Blocked on human-provided real TU REST API Postman response samples (`student-profile.json` and `employee-profile.json` in `docs/api-samples/`).

- [ ] **Study Buddy Matching**
  - Module folder: `backend/src/main/java/th/ac/tu/circles/matching` (missing)
  - Migration file: missing
  - Merged PR / Commit: none
  - Blocker / Note: Blocked on human-provided real TU REST API Postman response samples required for preceding round (Identity Verification).

- [ ] **Open Call Board**
  - Module folder: `backend/src/main/java/th/ac/tu/circles/opencall` (missing)
  - Migration file: missing
  - Merged PR / Commit: none

- [x] **LINE LIFF Distribution / Frontend Scaffold**
  - Frontend folder: `liff-frontend`
  - Merged PR / Commit: PR #14 (4086918)
