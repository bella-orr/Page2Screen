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

## Development: Rebuild On Changes

Run the compiler in continuous mode to automatically rebuild the app on changes. This should make development faster since developers will not have to manually stop and restart the application for every change.

Start the app in one terminal (keep it running):

```bash
gradle bootRun
```

In another terminal, run Gradle in continuous mode to rebuild classes when sources change

```bash
gradle -t classes
```

## Testing

Test with VS Code REST Client using api.http.
