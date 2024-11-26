# description

insurancePolicies is a rest application providing crud functionalities.

Important endpoints are :

- /insurancePolicies
  - create, list, delete, update insurance policies
- /actuator/health
  - status of the application
- /v3/api-docs
  - rest endpoints documentation in open api format

# build docker image from Dockerfile

mvn clean install
docker build -t insurancepolicies .

# build application without running IT tests

If you don't have docker on your machine, you can build
the application using :

mvn clean install -DskipITs=true

# run application

Build the docker image, then do :

docker-compose up -d

The insurancepolicies application will start on port 8080.

A postgres database will be started on port 5432.

# stop application

docker-compose down

# how to add a new field in db ?

Database schema is managed by flyway.

To modify the schema, add an sql script in resources/migration.