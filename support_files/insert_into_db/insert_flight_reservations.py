import requests
import random
import names
import sys

users = (requests.get('http://airline-management-system.app.loc/user-service/users/getAllUsers')).json()
flights = (requests.get('http://airline-management-system.app.loc/flight-service/flights/getAllFlights')).json()

args = sys.argv
num_flight_reservations = int(args[1])

if __name__ == '__main__':
    
    # insert flight reservation
    
    addFlightReservation_url = 'http://airline-management-system.app.loc/flight-reservation-service/flight_reservations/addFlightReservation'
    
    for i in range(num_flight_reservations):

        rand = random.randint(0, len(users)-1)
        user_id = (users[rand])['_id']
        ticket_type = "ROUND_TRIP"
        passengers_number = 2
        
        rand1 = random.randint(0, len(flights)-1)
        flight_id1 = (flights[rand1])['_id']
        
        while True:
            rand2 = random.randint(0, len(flights)-1)
            flight_id2 = (flights[rand2])['_id']
            if rand2 != rand1:
                break

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