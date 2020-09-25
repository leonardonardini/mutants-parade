# mutants-parade
This app is aim to check if given DNA correspond or not to a mutant ... it's a direct order from Magneto!

The app functionality includes a DNA verification utility to check if a given DNA code corresponds or not to a mutant being, and stats about DNA verifitation usability
For accessing this funtionalities the app provides an API with two endPoits:

1.
POST
/api/mutant
(requires the DNA to be verified, as JSON format body)

2.
GET
/api/stats
(no parameters are required)

To detailed documentation please refer to Swagger API Documentation: https://mutantdetected.herokuapp.com/v2/api-docs

For testing purpoeses use Swagger Endpoints Testing utility: https://mutantdetected.herokuapp.com/swagger-ui.html
