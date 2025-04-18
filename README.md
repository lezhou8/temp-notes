# Temp notes

Create temporary notes without cluttering your local computer.

## About

Tired of the mess on your device left by the creation of ad-hoc notes? Our site offers a cleaner and simpler way to take notes. Just write and go. Your notes persist across browser sessions, either through cookies our a unique URL. No account needed. Notes last for a week, so you can focus on what matters, not file management.

Try it [here](https://tempnotes.me).

## Run it yourself

Make sure you have a Docker daemon running on your machine:

```
dockerd
```

Clone the repo.

```
git clone https://github.com/lezhou8/temp-notes.git
```

Navigate into the repo.

```
cd temp-notes
```

Create a `.env` file:

```
cat <<EOF > .env
DL_URL=jdbc:postgresql://db:5432/postgres
POSTGRES_USER=<username>
POSTGRES_PASSWORD=<password>
POSTGRES_DB=postgres
EOF
```

Replace `<username>` and `<password>` with your desired credentials.

Then start the app using Docker Compose:

```
docker compose up
```

Then visit `http://localhost` in your browser.

### Dependencies

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Testing

Ensure a Docker daemon is running on your machine, due to [Testcontainers](https://testcontainers.com/) being used:

```
dockerd
```

Navigate to the `backend` directory:

```
cd backend
```

Run the test suite.

On Unix-based systems:

```
./gradlew test
```

On Windows:

```
gradlew.bat test
```

## Built with

* ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
* ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
* ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
* ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
* ![Docker Compose](https://img.shields.io/badge/Docker_Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)
* ![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)
* ![Testcontainers](https://img.shields.io/badge/Testcontainers-2F8ACB?style=for-the-badge&logo=docker&logoColor=white)
* ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
* ![DigitalOcean](https://img.shields.io/badge/DigitalOcean-%230167ff.svg?style=for-the-badge&logo=digitalOcean&logoColor=white)

## Miscellaneous

- [Backend API](backend/README.md)
