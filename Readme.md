# Location Search Application

This is a simple Spring Boot application that allows users to log in and search for locations by ZIP code or city name.

## Features

- User login with any combination of user ID and password
- Logging of user login attempts
- Search locations by ZIP code or city name
- Display search results or an error message if the location is not found

## Technologies Used

- Spring Boot
- Thymeleaf for HTML templates
- Lombok for reducing boilerplate code
- SLF4J for logging

## Prerequisites

- Java 21
- Maven

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/NehaThawani44/location-service.git
cd location-service
```

### Build the Project

``./mvnw clean install
``
### Run the Application

``./mvnw spring-boot:run
``

### Access the Application
Open your web browser and go to http://localhost:8080/login to access the login page.
* Enter all the username and password.
* Search the zipcode '53111'

# CRUD Operations
## Add a Location
To add a location, send a POST request with JSON data to /add.

### Request:

````
POST http://localhost:8080/add
Content-Type: application/json

{
    "zipCode": "53111",
    "cityName": "Bonn"
}
````
### Response:
````
{
  "id": 1,
  "zipCode": "53111",
  "cityName": "Bonn"
}
````

## Get All Locations
To retrieve all locations, send a GET request to /.

### Request:
````
GET http://localhost:8080/
````
### Response:
````````
[
  {
    "id": 1,
    "zipCode": "60311",
    "cityName": "Frankfurt"
  },
  {
    "id": 2,
    "zipCode": "68165",
    "cityName": "Mannheim"
  }
]
````````
## Get a Location by ZIP Code
To retrieve a location by its ZIP code, send a GET request to /zipcode/{zipCode}.

### Request:
````````
GET http://localhost:8080/zipcode/10001
`````````

## Update a Location
To update the city name of a location, send a PUT request with JSON data to /locations/update/{zipCode}.

### Request:

``````
PUT http://localhost:8080/update/53111

Content-Type: application/json

{
  "cityName": "Bonn"
}

``````
### Response:
``````
{
  "id": 1,
  "zipCode": "21079",
  "cityName": "Harburg"
}
``````

## Delete a Location
To delete a location by its ZIP code, send a DELETE request to /delete/{zipCode}.

### Request:
``````
DELETE http://localhost:8080/delete/10001
``````
### Response:
````
{
  "message": "Location deleted successfully"
}
````
### License
This project is licensed under the MIT License. See the LICENSE file for details.

````
This `README.md` file now includes instructions for setting up and running the project, as well as details about the CRUD operations available in the API.
`````



