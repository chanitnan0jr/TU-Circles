# AGENTS.md — TU Circles

## Context
อ่าน docs/PROJECT_CONTEXT.md ก่อนเริ่มทุก task

## Build & Test
- Backend: `cd backend && ./mvnw clean verify`
- Frontend: `cd liff-frontend && yarn install && yarn build`
- ต้องรัน test ผ่านทั้งหมดก่อนเปิด PR

## Conventions
- Java: ตาม Google Java Style Guide, package-by-feature เท่านั้น
- ห้าม Controller คืน Entity ตรง ๆ ต้องผ่าน DTO
- ทุกการเรียก TU API ต้องผ่าน backend/.../shared/client/TuApiClient.java เท่านั้น
- Frontend: Tailwind สำหรับ style, Motion สำหรับ animation ห้ามผสมหน้าที่กัน

## Forbidden Patterns
- ห้าม hardcode Application-Key หรือ secret ใน source code — ใช้ env var เท่านั้น
- ห้ามเริ่มเขียนฟีเจอร์ Phase 2 (badge, gamification, AI agent) ก่อน MVP 4 โมดูล pass test
- ห้ามเพิ่ม WebSocket/chat server เข้ามาโดยไม่ได้รับอนุมัติ scope ก่อน

## Scope Reference
ดู docs/PROJECT_CONTEXT.md หัวข้อ MVP vs Phase 2 ก่อนรับ task ใหม่ทุกครั้ง
