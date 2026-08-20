# API Schema Notes

## Ground Truth vs Legacy Spec
- Real API responses captured via Postman / live calls serve as the single source of truth (`docs/api-samples/`).
- Confirmed student fields: `studentid`, `displayname_th`, `displayname_en`, `type`, `statsid`, `statusname`, `level_name`, `faculty`, `department`.
- Confirmed employee fields: `username`, `displayname_th`, `displayname_en`, `type`, `employee_type`, `organization`.
- Any unconfirmed fields (e.g. email, phone, GPA) are strictly avoided until confirmed by live API sample captures.
