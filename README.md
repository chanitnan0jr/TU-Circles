# TU Circles

TU Circles is a platform for Thammasat University students and staff featuring Identity Verification, Study Buddy Matching, and an Open Call Board delivered via a LINE LIFF Mini App.

## Project Structure

```
tu-circles/
├── AGENTS.md
├── docs/
│   ├── api-samples/
│   ├── api-schema-notes.md
│   ├── openapi/tu-api.yaml
│   └── PROJECT_CONTEXT.md
├── backend/
└── liff-frontend/
```

## Getting Started

### Prerequisites
- JDK 21
- Node.js 22+ & Yarn
- Docker & Docker Compose (for local PostgreSQL)

### Backend Development
```bash
cd backend
./mvnw clean verify
```

### Frontend Development
```bash
cd liff-frontend
yarn install
yarn dev
```
