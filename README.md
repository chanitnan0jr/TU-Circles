# TU Circles — Comprehensive Master Plan
> เอกสารนี้ใช้เป็นทั้ง Master Plan และเนื้อหาต้นแบบสำหรับไฟล์ `AGENTS.md` ที่ Google Jules
> จะอ่านอัตโนมัติจาก root ของ repo ก่อนเริ่มวางแผนงาน [Jules Docs](https://jules.google/docs/)

---

## 1. Scope Summary

**MVP (เทอมนี้):** Identity Verification (TU API) + Study Buddy Matching + Open Call Board + LINE LIFF distribution
**Phase 2 (Future Work):** Attendance Integrity, Verified Badge, Private Matching, AI Team Assistant, Gamification

ห้าม Jules Agent เริ่มงาน Phase 2 ก่อน MVP ทั้ง 4 โมดูล pass integration test แล้ว

---

## 2. Repository Structure

```
tu-circles/
├── AGENTS.md                     # <- เนื้อหาส่วนที่ 6 ของไฟล์นี้
├── docs/
│   ├── api-samples/               # JSON response จริงจาก Postman (ground truth)
│   ├── api-schema-notes.md        # จุดต่างระหว่าง Postman เก่า vs เอกสารสด
│   ├── openapi/tu-api.yaml        # แปลงจาก Postman collection (ดูหัวข้อ 4.1)
│   └── PROJECT_CONTEXT.md         # สรุป business context (จากไฟล์ summary เดิม)
├── backend/                       # Spring Boot (Java 21)
│   └── src/main/java/th/ac/tu/circles/
│       ├── shared/                # exception, util, config — ไม่มี business logic
│       ├── identity/               # feature package: controller/service/dto/client
│       ├── matching/               # feature package: study-buddy matching
│       └── opencall/               # feature package: open call board
├── liff-frontend/                  # LINE LIFF Mini App
│   └── src/
│       ├── pages/
│       ├── components/
│       └── styles/                # Tailwind config
├── .github/workflows/ci.yml
├── .editorconfig
└── README.md
```

หลักการแพ็กเกจ: **package-by-feature ไม่ใช่ package-by-layer** — แต่ละ feature (`identity`, `matching`, `opencall`) ห้าม import ข้ามกันตรง ๆ ต้องคุยผ่าน interface หรือ event เท่านั้น เพราะทำให้ไฟล์แต่ละ feature ย้าย/ลบได้โดยไม่กระทบกัน [dev.to guide][web:219][web:228]

---

## 3. Backend Best Practices (แปะให้ Jules อ้างอิงตรง)

| เรื่อง | Resource | ทำไมต้องอ่าน |
|---|---|---|
| Java code style | [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) | มาตรฐานการเขียน Java ที่ Google ใช้จริง กัน inconsistent formatting |
| Spring Boot structuring | [Spring.io: Structuring Your Code](https://docs.spring.io/spring-boot/reference/using/structuring-your-code.html) | เอกสารทางการของ Spring เอง เรื่อง package convention |
| Package-by-feature | [dev.to: Spring Boot Project Structure Best Practices](https://dev.to/kamlesh_patil/spring-boot-project-structure-best-practices-used-in-production-4h85) | อธิบายทำไมห้าม feature import ข้ามกัน |
| Layered structure ภายใน feature | [dev.to: Scalable Spring Boot — Feature-Based Structure](https://dev.to/karthikkorrayi/scalable-spring-boot-project-a-feature-based-structure-that-grows-with-you-57fd) | ตัวอย่าง `controller/service/dto/config` ต่อ feature |
| DTO/Entity separation | [dev.to: Real-World Spring Boot Structure](https://dev.to/amira_saiid_f45c6497b5544/understanding-a-real-world-spring-boot-project-structure-best-practices-1nfd) | กฎ "Never expose entities" ผ่าน Controller ตรง ๆ |

**กฎที่ต้องบังคับใน `AGENTS.md`:**
- Controller คืนแค่ DTO ห้ามคืน Entity ตรง ๆ
- Service layer ห้ามมี `@RestController` หรือ Spring annotation ฝั่ง web
- ทุก endpoint ที่เรียก TU API ต้องผ่าน `TuApiClient` เดียว ห้ามยิง `RestTemplate`/`WebClient` กระจายทั่วโค้ด

---

## 4. API Contract Layer

### 4.1 แปลง Postman Collection เป็น OpenAPI Spec ก่อนให้ Jules เขียนโค้ด
Postman มีฟีเจอร์ **Generate Specification** ที่แปลง collection เป็น OpenAPI YAML/JSON อัตโนมัติ (มี type จาก request/response ที่บันทึกไว้) — เก็บไฟล์นี้ไว้ที่ `docs/openapi/tu-api.yaml` แล้วให้ Jules เขียน `TuApiClient.java` โดยอ้างจากไฟล์นี้เป็น contract หลัก ไม่ใช่เดาจากคำอธิบาย [Postman Docs](https://learning.postman.com/docs/design-apis/collections/generate-specifications) [Postman Blog: Spec Linting][web:243]

ขั้นตอน: Collections → เลือก collection → View more actions → Generate specification → เลือก YAML → commit เข้า repo

### 4.2 กฎการเขียน Client
- ทุก field ที่ TU API คืนมาต้อง map เข้า DTO ที่มี validation (`@NotNull`, `@NotBlank`)
- ถ้า field ไหนไม่ตรงกับ Postman เก่า (5 ปีที่แล้ว) ให้บันทึกไว้ใน `docs/api-schema-notes.md` และใช้ค่าจากการยิงจริงเป็นหลัก

---

## 5. Frontend (LIFF) Best Practices

| เรื่อง | Resource | ทำไมต้องอ่าน |
|---|---|---|
| LIFF Starter | [LINE: Trying the LIFF starter app](https://developers.line.biz/en/docs/liff/trying-liff-app/) | Template พร้อมใช้ ลด boilerplate |
| Scaffold เร็ว | [create-liff-app CLI](https://github.com/line/create-liff-app) | `npx @line/create-liff-app` สร้างโครง Next.js/Vanilla ให้ทันที |
| LIFF SDK Integration | [LINE: Developing a LIFF app](https://developers.line.biz/en/docs/liff/developing-liff-apps/) | วิธี init, login, get profile ที่ถูกต้อง |
| Styling | [Tailwind CSS Docs](https://tailwindcss.com/docs) | Utility-first ลด custom CSS ที่ดูแลยาก |
| Animation | [Motion.dev: Tailwind CSS animations](https://motion.dev/docs/react-tailwind) | หลักการ "Tailwind ทำ style, Motion ทำ animation" — ห้ามผสมสอง library ทำหน้าที่ซ้อนกัน |

**กฎที่ต้องบังคับ:** ห้ามเขียน custom CSS animation ด้วยมือถ้า Motion.dev ทำได้ ห้ามผสม Tailwind config เกิน design token ที่กำหนดไว้ล่วงหน้า (สีหลัก, spacing scale) เพื่อไม่ให้ UI ไม่สม่ำเสมอ

---

## 6. เนื้อหาที่ควร Copy ไปเป็น `AGENTS.md` จริง

```markdown
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
```

---

## 7. CI/CD & Repo Hygiene

โครงสร้าง root file ที่ควรมีตั้งแต่ commit แรก (ตามแนวทาง production-grade Spring Boot repo):
`.editorconfig`, `.gitattributes`, `.gitignore`, `docker-compose.yml` (สำหรับ Postgres local), `.github/workflows/ci.yml` (build + test อัตโนมัติทุก PR) [dev.to: monorepo best practices][web:227]

---

## 8. Task Order สำหรับ Jules Agent (Sprint แนะนำ)

1. Scaffold repo structure (หัวข้อ 2) + `AGENTS.md` + CI config
2. แปลง Postman → OpenAPI spec, commit `docs/openapi/tu-api.yaml`
3. เขียน `identity` feature: `TuApiClient` + verify-on-signup flow + unit test (mock response จาก `docs/api-samples/`)
4. เขียน `matching` feature: DB schema สำหรับ interest tag + availability slot + matching query
5. เขียน `opencall` feature: CRUD endpoint + filter
6. Scaffold LIFF frontend ด้วย `create-liff-app` แล้วต่อ Tailwind + Motion
7. Integration test ครบ 4 โมดูล → เปิดทางให้เริ่ม Phase 2 ได้
