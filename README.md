# le-bon-sandwich

BENIER Hugo\
LEMMER Richard\
ROBERT Alexandre

### Installation
  + mvn clean install
  + java -jar .\target\le-bon-sandwich-1.0-SNAPSHOT.jar
  
  OU 
   + mvn clean install
   + docker-compose up --build

### Routes principales

   + /commandes ... (Authentification nécessaire)
   + /categories ...
   + /sandwichs ...
   + /users

### Authentification
Methods : POST
URL : /oauth/token

Header: 
- Content-Type : application/x-www-form-urlencoded

Body : 
- username: john.doe
- password: jwtpass
- scope: read
- grant_type: password

Authorization : Basic Auth
- username : html5
- password : icmVhZCJdLCJ