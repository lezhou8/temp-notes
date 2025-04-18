# Backend API

Base URL: `/api/note`

```
GET /api/note/:uuid
```

**Request body:** N/A.

**Description:** Retrieve a note by its UUID.

**Response example:**

```
{
    "uuid": "00000000-0000-0000-0000-000000000000",
    "content": "some text",
    "expiry": "2025-04-18T12:00:00Z"
}
```

```
POST /api/note
```

**Request body:** N/A.

**Description:** Create a new note.

**Response example:**

```
{
    "uuid": "00000000-0000-0000-0000-000000000000",
    "content": "some text",
    "expiry": "2025-04-18T12:00:00Z"
}
```

```
PATCH /api/note/:uuid
```

**Request body:**

```
{
    "content": "some text"
}
```

**Description:** Update the `"content"` field of a note.

**Response example:** No response body.
