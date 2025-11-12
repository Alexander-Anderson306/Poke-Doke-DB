#!/bin/bash

echo "Generating self-signed certificate for secure https connection"

openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:4096 -out key.pem

openssl req -new -x509 -key key.pem -out cert.pem -days 9999 -sha256 \
  -subj "/C=US/ST=State/L=City/O=MyOrg/CN=localhost"

openssl pkcs8 -topk8 -in key.pem -out key_enc.pem -v2 des3 -passout pass:password

#password is password B)
keytool -importcert \
  -file cert.pem \
  -alias serverCert \
  -keystore truststore.p12 \
  -storetype PKCS12 \
  -storepass password \
  -noprompt

#key.pem must stay in the backend!
mv key.pem ./src/main/resources/.
mv cert.pem ./src/main/resources/.
#public key anyone can use
mv truststore.p12 ../frontend/src/main/resources/.
#cleanup junk
rm key_enc.pem