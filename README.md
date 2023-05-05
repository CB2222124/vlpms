# VLPMS | Vehicle License Plate Management System

RESTful web service and client-side application for a vehicle license plate management system.

## Getting started

```bash
git clone https://github.com/CB2222124/vlpms.git
cd vlpms
docker compose up --build -d
```

#### NOTE: API KEY

If you have been provided a VES API key, you can modify the environment variable `VES_KEY` for the backend service within `vlpms/docker-compose.yml`. This is not required to run the service, but will allow access to additional functionality.