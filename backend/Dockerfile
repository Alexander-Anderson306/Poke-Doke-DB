# Use x86_64 explicitly so Conscrypt's JNI works on all hosts
FROM --platform=linux/amd64 eclipse-temurin:25-jdk-jammy

#install maven and other dependencies
RUN apt-get update && apt-get install -y \
    wget curl unzip maven libgl1-mesa-dev \
    && rm -rf /var/lib/apt/lists/*


#install MariaDB basically mysql
RUN apt-get update && apt-get install -y mariadb-server && rm -rf /var/lib/apt/lists/*

# Install ngrok
RUN curl -s https://ngrok-agent.s3.amazonaws.com/ngrok.asc | \
    tee /etc/apt/trusted.gpg.d/ngrok.asc >/dev/null && \
    echo "deb https://ngrok-agent.s3.amazonaws.com buster main" | tee /etc/apt/sources.list.d/ngrok.list && \
    apt-get update && apt-get install -y ngrok

# Set ngrok authtoken
RUN ngrok config add-authtoken #<put your ngrok authtoken here>

WORKDIR /app
COPY . .

# Build shaded JAR
RUN mvn clean package -DskipTests

# Simple startup: start MariaDB, wait, then run your Server
CMD ["bash","-lc","\
service mariadb start && \
sleep 5 && \
mysql < /app/db_schema.sql && \
ngrok http 8080 --log stdout > /var/log/ngrok.log 2>&1 & \
java -jar target/backend-1.0.0.jar \
"]

