#Use x86_64 so Conscrypt works everywhere
FROM --platform=linux/amd64 eclipse-temurin:25-jdk-jammy

#Install tools and dependencies
RUN apt-get update && apt-get install -y \
    wget curl unzip maven libgl1-mesa-dev mariadb-server \
    && rm -rf /var/lib/apt/lists/*

#Set working directory
WORKDIR /app

#Copy everything into the container
COPY . .

#Build shaded JAR
RUN mvn clean package -DskipTests

#Expose port for ngrok
EXPOSE 8080

# Start MariaDB and backend
CMD ["bash", "-lc", "\
service mariadb start && \
sleep 5 && \
mysql < /app/db_schema.sql && \
java -jar target/backend-1.0.0.jar \
mvn test \
"]
