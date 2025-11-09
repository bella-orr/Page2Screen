# Page2Screen REST API (com.page2screen)

## Build and Run

Start backend database in docker

> **Important:** Make sure the docker engine is running

```bash
docker compose up postgres
```

Start frontend in seperate terminal

```bash
gradle bootRun
```

Visit the application at <http://localhost:8080/>

## Testing

Test with VS Code REST Client using api.http.
