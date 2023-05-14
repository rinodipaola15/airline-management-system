import requests

airports_to_insert = [['Fontanarossa', 'Catania'], ['Gatwick', 'Londra'], ['Adolfo Suárez Madrid-Barajas', 'Madrid'], ['Josep Tarradellas Barcellona-El Prat', 'Barcellona']]

if __name__ == '__main__':
    
    # insert airports
    
    addAirport_url = 'http://airline-management-system.app.loc/flight-service/airports/addAirport'
    
    for i in range(len(airports_to_insert)):
        airport = airports_to_insert[i]
        document = {"name": airport[0], "city": airport[1]}
        x = requests.post(addAirport_url, json = document)
        print(x.text)