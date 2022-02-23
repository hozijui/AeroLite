### Project setup

- Run backend server for development

```
cd server
./gradlew bootRun
```

backend server will start on localhost:8080

- Compiles and hot-reloads for web development

```
cd web
yarn run serve
```

web server will start on localhost:8000, request to `/api` will be proxied to backend

- Build for production
```
cd server
./gradlew build
```
