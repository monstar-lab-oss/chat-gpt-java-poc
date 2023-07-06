# chat-gpt-java-poc
Provides Sample of How we can integrate ChatGPT into a Java project (Spring Boot).
The project can serve as a POC.

## Requirements
* JDK 17
* Any IDE with [lombok](https://projectlombok.org/) plugin installed

## How to code
Import as a java project in an IDE of your choice.

## Working locally
### How to run the app (local with mvn commands, local Postgres)
Install and configure Postgres locally. For mac follow the [link](https://www.sqlshack.com/setting-up-a-postgresql-database-on-mac/).

Install locally a Postgres client (eg [PgAdmin](https://www.pgadmin.org/download/pgadmin-4-macos/) )

Migrate DB via command: `mvn flyway:migrate -Dflyway.url=XXX -Dflyway.user=XXX -Dflyway.password=XXX`

Run `mvn clean install`. This will generate the appropriate jar, that will be run later on.

Before running the app set the following env variables (take advantage of the IDEA run configuration)

`ACTIVE_PROFILE=` dev / qa / prod

`DATABASE_HOST=`

`DATABASE_PORT=`

`DATABASE_NAME=`

`DATABASE_USER=`

`DATABASE_PASSWORD=`

Run `mvn spring-boot:run -f pom.xml`

Access it into a browser at: `{{baseUrl}}/users`

