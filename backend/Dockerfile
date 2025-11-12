FROM debian:stable-slim

#install maven and other dependencies
RUN apt-get update && apt-get install -y \
    wget curl unzip maven libgl1-mesa-dev \
    && rm -rf /var/lib/apt/lists/*

# --- Install Java 25 (manual, arch-aware) ---
RUN set -eux; \
  #get the arc of the container (amd64 or arm64)
  arch="$(dpkg --print-architecture)"; \
  #download correct jdk tar
  case "$arch" in \
    amd64)  JDK_URL="https://download.oracle.com/java/25/latest/jdk-25_linux-x64_bin.tar.gz" ;; \
    arm64)  JDK_URL="https://download.oracle.com/java/25/latest/jdk-25_linux-aarch64_bin.tar.gz" ;; \
    *) echo "Unsupported arch: $arch" && exit 1 ;; \
  esac; \
  #download and extract
  curl -L "$JDK_URL" -o /tmp/jdk.tar.gz; \
  mkdir -p /opt/jdk-25; \
  tar -xzf /tmp/jdk.tar.gz -C /opt/jdk-25 --strip-components=1; \
  rm /tmp/jdk.tar.gz

ENV JAVA_HOME=/opt/jdk-25
ENV PATH="$JAVA_HOME/bin:$PATH"


#install MariaDB basically mysql
RUN apt-get update && apt-get install -y mariadb-server && rm -rf /var/lib/apt/lists/*


EXPOSE 443 3306

WORKDIR /app
COPY . .

# Build shaded JAR
RUN mvn clean package -DskipTests

# Simple startup: start MariaDB, wait, then run your Server
#CMD ["bash","-lc","service mariadb start && sleep 5 && mysql < /app/db_schema.sql && java -jar target/backend-1.0.0-shaded.jar"]
CMD ["bash", "-c", "service mariadb start && sleep 5 && java -jar target/backend-*.jar && tail -f /dev/null"]

