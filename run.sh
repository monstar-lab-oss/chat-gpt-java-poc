#!/bin/sh
export DATABASE_HOST=database
export DATABASE_PORT=5432
export DATABASE_NAME=postgres
export DATABASE_USER=postgres
export DATABASE_PASSWORD=postgres
export ACTIVE_PROFILE=dev
docker-compose up