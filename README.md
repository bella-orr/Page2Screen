# Page2Screen REST API (com.page2screen)

The main deployment is visible at <https://page2screen.azurewebsites.net/>

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

## Development: Rebuild On Changes + Admin Panel

Run the compiler in continuous mode to automatically rebuild the app on changes. This should make development faster since developers will not have to manually stop and restart the application for every change.

The Admin panel is available at <http://localhost:8080/admin> and provides an easy way to seed and clear the database.

Start the app in one terminal (keep it running):

```bash
gradle bootRunLocal
```

In another terminal, run Gradle in continuous mode to rebuild classes when sources change

```bash
gradle -t classes
```

### Postgres Database

Access the database using DBeaver or another compatible database tool with the following connection URL:

<jdbc:postgresql://localhost:15432/page2screen>

Username: `page2screen`
Password: `page2screen`

## Testing

Test with VS Code REST Client using api.http.
