# Use x86_64 explicitly so Conscrypt's JNI works on all hosts
FROM --platform=linux/amd64 eclipse-temurin:25-jdk-jammy

#install maven and other dependencies
RUN apt-get update && apt-get install -y \
    wget curl unzip maven libgl1-mesa-dev \
    && rm -rf /var/lib/apt/lists/*


#install MariaDB basically mysql
RUN apt-get update && apt-get install -y mariadb-server && rm -rf /var/lib/apt/lists/*


EXPOSE 443 3306

WORKDIR /app
COPY . .

# Build shaded JAR
RUN mvn clean package -DskipTests

# Simple startup: start MariaDB, wait, then run your Server
CMD ["bash","-lc","service mariadb start && sleep 5 && mysql < /app/db_schema.sql && java -jar target/backend-1.0.0.jar"]
