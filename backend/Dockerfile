FROM debian:stable-slim

# Install dependencies
RUN apt-get update && apt-get install -y wget unzip maven curl libgl1-mesa-dev && rm -rf /var/lib/apt/lists/*

# Install Java 25 (Temurin)
RUN wget https://github.com/adoptium/temurin25-binaries/releases/latest/download/OpenJDK25U-jdk_x64_linux_hotspot.tar.gz -O /tmp/jdk25.tar.gz && \
    tar -xzf /tmp/jdk25.tar.gz -C /opt && \
    rm /tmp/jdk25.tar.gz
ENV JAVA_HOME=/opt/jdk-25
ENV PATH="$JAVA_HOME/bin:$PATH"

# Install MySQL
RUN apt-get update && apt-get install -y mysql-server && rm -rf /var/lib/apt/lists/*

# Expose ports
EXPOSE 5001 3306

# Copy project
COPY . /app
WORKDIR /app

# Build with Maven
RUN mvn clean package -DskipTests

# Run server
CMD service mysql start && java -jar target/backend.jar
