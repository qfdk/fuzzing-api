# fuzzing-api [![Build Status](https://travis-ci.org/qfdk/fuzzing-api.svg?branch=master)](https://travis-ci.org/qfdk/fuzzing-api)

Fuzzing or Amazing is used to test the swagger application http requests.
We can find if a REST API server works well by using this application.
The application will search all the paths in the json file, because a swagger api applicaion
uses the json file to generate the code.

## Installation

```bash
git clone https://github.com/qfdk/fuzzing-api
cd fuzzing-api/fuzzing
mvn install && mvn jetty:run
#test
mvn test
```

You should see the web ui via your browser in this address **http://localhost:8080**.

![](img/img1.png)

default `swagger.json` : http://localhost:8080/swagger.json


## REST api/v1

Using commande line

```bash
#/getPath?url=
curl  -X GET --header 'Accept: application/json' 'http://localhost:8080/api/v1/getPath?url=http://petstore.swagger.io/v2/swagger.json'
#/analyse
curl  -X GET --header 'Accept: application/json' 'http://localhost:8080/api/v1/getPath?url=http://petstore.swagger.io/v2/swagger.json'
```

An example of reponse

```json
{
  "hostname": "petstore.swagger.io",
  "urls": [
    {
      "codes": [
        "405"
      ],
      "valided": false,
      "reponseCode": "400",
      "link": "http:\/\/petstore.swagger.io\/v2\/pet",
      "operationType": "POST",
      "parameters": {
        "body": "UmUDGptWZe"
      }
    },
    ... etc
    {

    }
  ]
}
```

## Todo list
- [X] POST
- [X] GET
- [X] DELETE
- [ ] MOVE ?
- [ ] WEB with Vue.js ?
- [X] .travis.yml

