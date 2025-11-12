FROM debian:stable-slim

# --- Install dependencies ---
RUN apt-get update && apt-get install -y \
    wget unzip curl maven mysql-server libgl1-mesa-dev \
    && rm -rf /var/lib/apt/lists/*

# --- Install Java 25 (Temurin) ---
RUN wget https://github.com/adoptium/temurin25-binaries/releases/latest/download/OpenJDK25U-jdk_x64_linux_hotspot.tar.gz -O /tmp/jdk25.tar.gz && \
    mkdir -p /opt/jdk && \
    tar -xzf /tmp/jdk25.tar.gz -C /opt/jdk --strip-components=1 && \
    rm /tmp/jdk25.tar.gz
ENV JAVA_HOME=/opt/jdk
ENV PATH="$JAVA_HOME/bin:$PATH"

# --- Expose ports ---
EXPOSE 443 3306

# --- Copy project and set workdir ---
WORKDIR /app
COPY . .

# --- Build the Java project ---
RUN mvn clean package -DskipTests

# --- Prepare MySQL directories ---
RUN mkdir -p /var/run/mysqld /var/lib/mysql && chown -R mysql:mysql /var/run/mysqld /var/lib/mysql

# --- Startup script ---
RUN echo '#!/bin/bash\n\
service mysql start\n\
sleep 5\n\
echo "Launching backend server..."\n\
exec java -jar target/backend-1.0.0-shaded.jar\n' > /start.sh && chmod +x /start.sh

# --- Start MySQL + Server ---
ENTRYPOINT ["/bin/bash", "/start.sh"]

