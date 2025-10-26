#!/bin/bash

echo "Generating self-signed certificate for secure https connection"

echo -n "Enter the password for the keystore file: "
read -s keystore_password

keytool -genkeypair \
    -alias server \
    -keyalg RSA \
    -keysize 2048 \
    -validity 9999 \
    -keystore serverkeystore.jks \
    -storepass $keystore_password

keytool -export \
    -alias server \
    -keystore serverkeystore.jks \
    -file server.cer \
    -storepass $keystore_password

chmod 600 serverkeystore.jks

mv serverkeystore.jks ./src/main/resources/.
cp server.cer ./src/main/resources/.
cp server.cer ../frontend/src/main/resources/.