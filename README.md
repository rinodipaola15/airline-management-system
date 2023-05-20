# Airline Management System
This is a thesis project, realized for my master's thesis in Computer Engineering. It consists of the development of a microservices application using Spring Boot, Docker and Kubernetes and the application of a horizontal autoscaling mechanism, with the goal of improving the performance, in terms of response time, of the application.

## Kubernetes

Start minikube 
```
minikube start --cpus 4 --memory 4096
eval $(minikube docker-env)
minikube addons enable ingress
```   

Build docker images
```
docker build -t user-service:v1 ./user-service 
docker build -t flight-service:v1 ./flight-service
docker build -t flight-reservation-service:v1 ./flight-reservation-service
```  

Apply YAML files
```  
kubectl apply -f k8s/
kubectl apply -f prometheus/
kubectl apply -f node_exporter/
kubectl apply -f autoscaler/custom-hpa
```  

Enable metrics-server
```  
minikube addons enable metrics-server
```  

Enable kube-state-metrics
```  
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm install kube-state-metrics prometheus-community/kube-state-metrics
```  

Install Prometheus Adapter
```  
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm install my-release -f myvalues.yaml prometheus-community/prometheus-adapter
```  

### /etc/hosts
```sh
echo "$(minikube ip) airline-management-system.app.loc" | sudo tee -a /etc/hosts
```

### Demo

NB: Remember to replace the ObjectIds with the correct ones.

```
# Create a user
curl airline-management-system.app.loc/user-service/users/addUser -X POST -H "Content-Type: application/json" -d '{"firstName": "Rino", "lastName": "Di Paola", "email": "rino.dipaola@gmail.com", "birthDate": "15-07-1998", "country": "Italy", "phoneNumber": "123456789"}'

# Create an airport
curl airline-management-system.app.loc/flight-service/airports/addAirport -X POST -H "Content-Type: application/json" -d '{"name": "Fontanarossa", "city": "Catania"}'

# Create a flight
curl airline-management-system.app.loc/flight-service/flights/addFlight -X POST -H "Content-Type: application/json" -d '{"maxPassengersNumber": 100, "departureCity": "Catania", "departureAirportId": "63fcb0385f15030007b7ec5e", "departureDateTime": "01-08-2022 10:30", "destinationCity": "London", "destinationAirportId": "63fcb0395f15030007b7ec5f", "arrivalDateTime": "01-08-2022 12:40", "price": 150.00}'

curl airline-management-system.app.loc/flight-service/flights/addFlight -X POST -H "Content-Type: application/json" -d '{"maxPassengersNumber": 100, "departureCity": "London", "departureAirportId": "63fcb0395f15030007b7ec5f", "departureDateTime": "01-08-2022 10:30", "destinationCity": "Catania", "destinationAirportId": "63fcb0385f15030007b7ec5e", "arrivalDateTime": "01-08-2022 12:40", "price": 150.00}'

# Create a flight fare
curl airline-management-system.app.loc/flight-reservation-service/flight_fares/addFlightFare -X POST -H "Content-Type: application/json" -d '{"name": "Regular", "description": "This is an example flight fare", "price": 59.90}'

# Create a luggage
curl airline-management-system.app.loc/flight-reservation-service/luggage/addLuggage -X POST -H "Content-Type: application/json" -d '{"maxWeight": 30.00, "maxSize": "10x10x20", "description": "This is an example luggage", "price": 30.00}'

# Create a one-way flight reservation
curl airline-management-system.app.loc/flight-reservation-service/flight_reservations/addFlightReservation -X POST -H "Content-Type: application/json" -d '{"userId": "639643ad44b5d07c75b9b5e2", "ticketType": "ONE_WAY", "passengersNumber": 1, "flightsReservationDetails": [{"flightId": "63948144a4fd805c8a2a375c", "passengerData": {"name": "Rino", "surname":"Di Paola", "birthDate": "15-07-1998", "flightFare": "639481e3a4fd805c8a2a375e", "extraLuggage": {"6394823b3a0c070cf5271fe4": 2}, "travelInsurance": 1, "fastTrack": 1}}], "amount": 250.00}'

# Create a round-trip flight reservation
curl airline-management-system.app.loc/flight-reservation-service/flight_reservations/addFlightReservation -X POST -H "Content-Type: application/json" -d '{"userId": "6409de43c9e77c0008bec2ce", "ticketType": "ROUND_TRIP", "passengersNumber": 2, "flightsReservationDetails": [{"flightId": "64086947e21b840008a4e836", "passengersData": [{"name": "Rino", "surname":"Di Paola", "birthDate": "15-07-1998", "flightFare": "6409d1985908010008f6fe8d", "extraLuggage": {"6409d1985908010008f6fe8e": 1}, "travelInsurance": 1, "fastTrack": 1}, {"name": "Giulia", "surname":"Marano", "birthDate": "30-09-1999", "flightFare": "6409d1985908010008f6fe8d", "extraLuggage": {"6409d1985908010008f6fe8e": 2}, "travelInsurance": 1, "fastTrack": 1}]}, {"flightId": "64086947e21b840008a4e833", "passengersData": [{"name": "Rino", "surname":"Di Paola", "birthDate": "15-07-1998", "flightFare": "6409d1985908010008f6fe8d", "extraLuggage": {"6409d1985908010008f6fe8e": 1}, "travelInsurance": 1, "fastTrack": 1}, {"name": "Giulia", "surname":"Marano", "birthDate": "30-09-1999", "flightFare": "6409d1985908010008f6fe8d", "extraLuggage": {"6409d1985908010008f6fe8e": 2}, "travelInsurance": 1, "fastTrack": 1}]}]}'

# Get all users
curl insurance.app.loc/users/

# Get all flights
curl insurance.app.loc/policies/

# Get all flight reservations
curl insurance.app.loc/optionals/

# Delete a user
curl airline-management-system.app.loc/user-service/users/deleteUser{id} -X DELETE

# Delete a flight
curl airline-management-system.app.loc/flight-service/flights/deleteFlight{id} -X DELETE

# Delete a flight reservation
curl airline-management-system.app.loc/flight-reservation-service/flight_reservations/deleteFlightReservation/{id} -X DELETE

# Delete all users
curl airline-management-system.app.loc/user-service/users/deleteAllUsers -X DELETE

# Delete all flights
curl airline-management-system.app.loc/flight-service/flights/deleteAllFlights -X DELETE

# Delete all flight reservations
curl airline-management-system.app.loc/flight-reservation-service/flight_reservations/deleteAllFlightReservations -X DELETE
```
