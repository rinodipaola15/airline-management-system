import requests
import random
import names
import sys

args = sys.argv
num_flight_reservations = int(args[1])

if __name__ == '__main__':
        
    # insert luggage
    
    addLuggage_url = 'http://airline-management-system.app.loc/flight-reservation-service/luggage/addLuggage'
    
    luggage1 = {"maxWeight": 30, "maxSize": "10x10x20", "description": "Luggage1 test description", "price": 30.00}
    luggage2 = {"maxWeight": 50, "maxSize": "15x15x25", "description": "Luggage2 test description", "price": 50.00}
    
    x = requests.post(addLuggage_url, json = luggage1)
    print(x.text)
    x = requests.post(addLuggage_url, json = luggage2)
    print(x.text)