#!/bin/bash

echo "Generating self-signed certificate for secure https connection"

openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 9999

#the password for the truststore is password 😎
keytool -importcert \
    -file cert.pem \
    -alias serverCert \
    -keystore truststore.p12 \
    -storetype PKCS12 \
    -storepass password

#key.pem must stay in the backend!
mv key.pem ./src/main/resources/.
mv cert.pem ./src/main/resources/.
#public key anyone can use
mv truststore.p12 ../frontend/src/main/resources/.