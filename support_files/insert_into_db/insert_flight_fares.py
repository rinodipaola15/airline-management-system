import requests
import random
import names
import sys

args = sys.argv
num_flight_reservations = int(args[1])

if __name__ == '__main__':
    
    # insert flight fares
    
    addFlightFare_url = 'http://airline-management-system.app.loc/flight-reservation-service/flight_fares/addFlightFare'
    
    flight_fare = {"name": "Regular", "description": "Flight fare test description", "price": 59.90}
    
    x = requests.post(addFlightFare_url, json = flight_fare)
    print(x.text)