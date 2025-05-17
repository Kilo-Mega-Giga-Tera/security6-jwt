# Spring Security With JWT

### Requirement

Java21, Docker, Docker Compose

### Start PostgreSQL, Redis

```shell
cd docker
```

```shell
docker compose up -d
```

#### Postgres and Redis password default: 1234

### Create Table

Check directory ddl

### Spring boot start

#### default port: 9999

### Entrypoint
| Method | Path         |
|:------:|:-------------|
|  POST  | /api/login   |
|  POST  | /api/refresh |
|  GET   | /api/todo    |
|  POST  | /api/todo    |
| ..more | ...more      |

