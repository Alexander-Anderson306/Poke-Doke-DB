#!/bin/bash

echo "Generating self-signed certificate for secure https connection"

openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 9999

#mv serverkeystore.jks ./src/main/resources/.
mv key.pem ./src/main/resources/.
cp cert.pem ./src/main/resources/.
mv cert.pem ../frontend/src/main/resources/.