import requests
import random
import names
import radar
import sys
from bson import ObjectId

countries = ['United States','Italy', 'Japan', 'Spain', 'United Kingdom']
users = ['640fac7d5f1503000872446c', '640fac7e5f1503000872446d', '640fac7e5f1503000872446e', '640fac7e5f1503000872446f', '640fac7e5f15030008724470']
airports_to_insert = [['Fontanarossa', 'Catania'], ['Gatwick', 'Londra'], ['Adolfo Suárez Madrid-Barajas', 'Madrid'], ['Josep Tarradellas Barcellona-El Prat', 'Barcellona']]
#airports = [['Catania', '640fac7fe21b840008533d45'], ['Londra', '640fac7fe21b840008533d46'], ['Madrid', '640fac7fe21b840008533d47'], ['Barcellona', '640fac7fe21b840008533d48']]

airports = (requests.get('http://airline-management-system.app.loc/flight-service/airports/getAllAirports')).json()

flights = ['640fad41e21b840008533d49', '640fad41e21b840008533d4a']

args = sys.argv
num_flights = int(args[1])

if __name__ == '__main__':
    
    '''
    # insert airports
    
    addAirport_url = 'http://airline-management-system.app.loc/flight-service/airports/addAirport'
    
    for i in range(len(airports_to_insert)):
        airport = airports_to_insert[i]
        document = {"name": airport[0], "city": airport[1]}
        x = requests.post(addAirport_url, json = document)
        print(x.text)
    '''
        
    # insert flights
    
    addFlight_url = 'http://airline-management-system.app.loc/flight-service/flights/addFlight'
    
    for i in range(num_flights):
    
        # departure flight
        
        max_passengers_number1 = random.randint(50, 200)
        
        # inserire date in ordine cronologico e comprese tra un certo range
        
        departure_datetime1 = str(radar.random_datetime())
        departure_datetime1 = departure_datetime1[:len(departure_datetime1)-3]
        
        arrival_datetime1 = str(radar.random_datetime())
        arrival_datetime1 = arrival_datetime1[:len(arrival_datetime1)-3] 
        
        rand1 = random.randint(0, len(airports)-1)
        city1 = (airports[rand1])['city']
        airport_id1 = (airports[rand1])['_id']
        
        price1 = round(random.uniform(0, 1000), 2)
        
        # destination flight
        
        max_passengers_number2 = random.randint(50, 200)   
        
        # inserire date in ordine cronologico e comprese tra un certo range
        
        departure_datetime2 = str(radar.random_datetime())
        departure_datetime2 = departure_datetime2[:len(departure_datetime2)-3]
        
        arrival_datetime2 = str(radar.random_datetime())
        arrival_datetime2 = arrival_datetime2[:len(arrival_datetime2)-3] 
        
        while True:
            rand2 = random.randint(0, len(airports)-1)
            city2 = (airports[rand2])['city']
            airport_id2 = (airports[rand2])['_id']
            if rand2 != rand1:
                break
        
        price2 = round(random.uniform(0, 1000), 2)
        
        flight_data1 = {"maxPassengersNumber": max_passengers_number1, "departureCity": city1, "departureAirportId": airport_id1, "departureDateTime": departure_datetime1, "destinationCity": city2, "destinationAirportId": airport_id2, "arrivalDateTime": arrival_datetime1, "price": price1}
        
        x = requests.post(addFlight_url, json = flight_data1)
        print(x.text)
        
        flight_data2 = {"maxPassengersNumber": max_passengers_number2, "departureCity": city2, "departureAirportId": airport_id2, "departureDateTime": departure_datetime2, "destinationCity": city1, "destinationAirportId": airport_id1, "arrivalDateTime": arrival_datetime2, "price": price2}
        
        x = requests.post(addFlight_url, json = flight_data1)
        print(x.text)
    
    '''
        
    # insert flight fares
    
    addFlightFare_url = 'http://airline-management-system.app.loc/flight-reservation-service/flight_fares/addFlightFare'
    
    flight_fare = {"name": "Regular", "description": "Flight fare test description", "price": 59.90}
    
    x = requests.post(addFlightFare_url, json = flight_fare)
    print(x.text)
        
    # insert luggage
    
    addLuggage_url = 'http://airline-management-system.app.loc/flight-reservation-service/luggage/addLuggage'
    
    luggage1 = {"maxWeight": 30, "maxSize": "10x10x20", "description": "Luggage1 test description", "price": 30.00}
    luggage2 = {"maxWeight": 50, "maxSize": "15x15x25", "description": "Luggage2 test description", "price": 50.00}
    
    x = requests.post(addLuggage_url, json = luggage1)
    print(x.text)
    x = requests.post(addLuggage_url, json = luggage2)
    print(x.text)
    
    # insert flight reservation
    
    addFlightReservation_url = 'http://airline-management-system.app.loc/flight-reservation-service/flight_reservations/addFlightReservation'

    rand = random.randint(0, len(users)-1)
    user_id = users[rand]
    ticket_type = "ROUND_TRIP"
    passengers_number = 2
    flight_id1 = flights[0]
    flight_id2 = flights[1]

    # cambiare name e surname in firstName e lastName

    passenger1 = {"name": names.get_first_name(), "surname": names.get_last_name(), "birthDate": str(random.randint(1, 31))+"-"+str(random.randint(1, 12))+"-"+str(random.randint(1900, 2023)), "flightFare": "640fad42652205000731637d", "extraLuggage": {"640fad42652205000731637e": 1, "640fad42652205000731637f": 2}, "travelInsurance": 1, "fastTrack": 1}
    passenger2 = {"name": names.get_first_name(), "surname": names.get_last_name(), "birthDate": str(random.randint(1, 31))+"-"+str(random.randint(1, 12))+"-"+str(random.randint(1900, 2023)), "flightFare": "640fad42652205000731637d", "extraLuggage": {"640fad42652205000731637e": 3, "640fad42652205000731637f": 1}, "travelInsurance": 1, "fastTrack": 1}

    flight_reservation_data = {
        "userId": user_id, 
        "ticketType": ticket_type, 
        "passengersNumber": passengers_number, 
        "flightsReservationDetails": [
            {
                "flightId": flight_id1, 
                "passengersData": 
                    [
                        passenger1, 
                        passenger2
                    ]
            },  
            {
                "flightId": flight_id2, 
                "passengersData": 
                    [
                        passenger1, 
                        passenger2
                    ]
            }
        ]
    }    
    
    x = requests.post(addFlightReservation_url, json = flight_reservation_data)
    print(x.text)
    
    '''