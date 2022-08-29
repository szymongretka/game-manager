# game-manager
Aplikacja menadżer gier dla zarządzania danymi, aplikacjami, statystykami itd.

Warunki wstępne
-zainstalowany docker
-MongoDB (port 27017)
-wolne porty: 8080, 8082, 8045, 8050, 8099, 6379, 3000, 4200

Aby uruchomić aplikację należy:
1. Uruchomić MongoDB
2. Uruchomić skrypty z pliku build.sh
3. Po zbudowaniu wszyskich obrazów z poprzedniego kroku, będąc w folderze projektu, wywołać komendy:
   - docker-compose.yml up -d keycloak
   - docker-compose.yml up -d redis
   - docker-compose.yml up -d eureka
   - docker-compose.yml up -d gateway
   - docker-compose.yml up -d app-service
   - docker-compose.yml up -d forum-service
   - docker-compose.yml up -d scoreboard-service
     (opcjonalnie)
   - docker-compose.yml up -d prometheus
   - docker-compose.yml up -d grafana
4. 

Aby otworzyć konsolę keycloaka, należy otworzyć przeglądarkę i wpisać http://localhost:8080
Aby otworzyć UI aplikacji, należy otworzyć przeglądarkę i wpisać http://localhost:4200


