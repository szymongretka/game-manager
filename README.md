# game-manager
Aplikacja menadżer gier dla zarządzania danymi, aplikacjami, statystykami itd.

Warunki wstępne
-zainstalowany docker
-MongoDB (port 27017)
-wolne porty: 8080, 8082, 8045, 8050, 8099, 6379, 3000, 4200

Aby uruchomić aplikację należy:
1. Uruchomić MongoDB
2. Uruchomić redisa (docker run -d --name redis -p 6379:6379)
3. Uruchomić skrypty z pliku build.sh
4. Po zbudowaniu wszyskich obrazów z poprzedniego kroku, będąc w folderze projektu, wywołać komendę docker-compose up -d
5. Wszystkie serwisy powinny się uruchomić. 

Aby otworzyć konsolę keycloaka, należy otworzyć przeglądarkę i wpisać http://localhost:8080
Aby otworzyć UI aplikacji, należy otworzyć przeglądarkę i wpisać http://localhost:4200


# ENGLISH VERSION
Game manager app for providing managing data, apps, stats etc.

In order to run the application:

start mongodb

-run scripts from build.sh

-redis cache server (docker run -d --name redis -p 6379:6379 redis)

-keycloak server

-Eureka Server

-Api Gateway

-Rest of the services (order does not matter)

-optionally monitoring servers (prometheus & grafana)


