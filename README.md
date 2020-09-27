# mutants-parade app
This app is aim to check if given DNA correspond or not to a mutant ... it's a direct order from Magneto!

The app functionality includes a DNA verification utility to check if a given DNA code corresponds or not to a mutant being, and statistics about DNA verification usability.

## API
For accessing this funtionalities the app provides an API with two endPoits:

### POST
#### /api/mutant
(requires the DNA to be verified, as JSON format body)

command line execution example:
```
curl -X POST "https://mutantdetected.herokuapp.com/api/mutant" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"dna\": [    \"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"  ]}"
```

### GET
#### /api/stats
(no parameters are required)

command line execution example:
```
curl -X GET "https://mutantdetected.herokuapp.com/api/stats" -H  "accept: */*"
```

## Swagger
For detailed API documentation please refer to *Swagger* API Documentation: https://mutantdetected.herokuapp.com/v2/api-docs

For testing purpoeses youy could use *Swagger* Endpoints Testing utility: https://mutantdetected.herokuapp.com/swagger-ui.html

## Code Coverege
Code coverge was analized using JaCoCo tool reaching 99 %
![Code Coverage analisys](/mutantdetected_codecoverag_jacoco.png)

