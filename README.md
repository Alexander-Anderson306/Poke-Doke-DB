# Poké-Doke-DB
**The Pokémon card collection and inventory management software simulator.**

## Project Description:
Poke-Doke is desktop application that allows users to collect, view, and manage virtual Pokémon trading cards. The system
includes a graphical user interface built with JavaFX, a Java backend using Javalin to handle HTTP requests, and a MySQL
database that stores users, cards, packs, and inventory data.
The goal of the application is to simulate the experience of collecting Pokémon cards. Users can create an account, log in,
purchase card packs, and view the cards in their inventory. When a user purchases a pack, the backend randomly selects cards
based on pack rarity and updates the user’s stored inventory. The frontend then displays these cards, along with details such
as card type, rarity, and artwork.

## System Requirments
To run Poke-Doke, the following system requirments must be met:
- Java 25 must be installed on the user’s machine.
- Maven must be installed to manage project dependencies and build the application.
- Docker must be installed to run the MySQL database in a containerized environment.
- ngrok must be installed to create a secure tunnel for remote database access.

Refere to [docker](https://www.docker.com/products/docker-desktop/), [ngrok](https://ngrok.com/), and [maven](https://maven.apache.org/) for instalation and set up.

## User Guide
### Hosting The Database And HTTP Server On Docker:
1. Open Docker Desktop if not already open.
2. Open a terminal window and navigate to
3. if the container does not exist, run the command *docker build -f docker server_docker/Dockerfile* -t poke_backend . to build the docker image.
4. Run the command docker run -p 8080:8080 poke_backend to start the docker container.
5. Keep the docker terminal window open.


### Using Ngrok For the HTTP Address
1. In the terminal window enter the command ngrok http 8080 and hit enter.
2. Copy the ”Forwarding” link and share it with anyone who desires to connect to your database remotely.
3. Keep the ngrok terminal window open.

### Running The Front End
1. Obtain a forwarding link from someone who is hosting the server and database.
2. Open the file [PATH_TO_POKE-DOKE]/frontend/src/main/java/com/poke_frontend Client.java in a text editor.
3. Locate the line that reads private String baseURL = "http://localhost:8080";.
4. Replace http://localhost:8080 with the forwarding link
5. Save and close the file.
6. Open a terminal window and navigate to [PATH_TO_POKE-DOKE]/frontend/.
7. Run the command mvn clean compile install -DskipTests to build the frontend application.
8. After the build is complete, run the command *mvn exec:java -Dexec.mainClass="com.poke_frontend.App"* to start the application.
