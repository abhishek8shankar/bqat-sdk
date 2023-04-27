# bqat-sdk

### Application.properties File Changes
```text
bqat.server.ipaddress=(host)
bqat.server.port=:(port)
bqat.server.path=/base64?urlsafe=true
bqat.content.type=application/json
bqat.content.charset=utf-8
bqat.jsonkey.finger.quality.score=NFIQ2
bqat.jsonkey.iris.quality.score=quality
bqat.jsonkey.face.quality.score=confidence
bqat.json.results=results
```

### Check service status
```text
http://{host}:8848/docs/

In case of localhost:
http://localhost:8848/docs
```
You will see response like 
```text
BQAT-Stateless
 1.2.0 beta 
OAS3
/openapi.json
BQAT-Stateless API provide you with biometric quality assessment capability as stateless service. 🚀

File
You can submit biometric file as is for assessment.

Base64
You can submit biometric file as Base64 encoded string for assessment.

Apache 2.0

```
## Run as docker

### Pull docker image

docker pull ghcr.io/biometix/bqat-stateless:latest

### Run docker image

docker compose up -d
