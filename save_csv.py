import matplotlib.pyplot as plt
import numpy as np
import matplotlib.pyplot as plt
from mongodb_lib import *
from prometheus_lib import *
import csv

microservices = ['user-service', 'flight-service', 'flight-reservation-service']

if __name__ == '__main__':
    
    # db connection data
    database = "metricsDB"
    
    # connection to local mongodb
    client = MongoClient('mongodb://localhost:27017/')

    # db connection
    db = client[database]
    
    for microservice in microservices:

        RT_collection = db[microservice+"_response_time"]
        RPS_collection = db[microservice+"_requests_per_second"]

        # get all documents from collections
        RT_documents = RT_collection.find()
        RPS_documents = RPS_collection.find()

        # numpy arrays initialization
        x = np.array([], dtype=np.float64)
        y = np.array([], dtype=np.float64)
        
        # add RT values to x
        for RT_doc in RT_documents:
            RT_value = RT_doc['value']
            y = np.append(y, float(RT_value)) 
            
        # add RPS values to x
        for RPS_doc in RPS_documents:
            RPS_value = RPS_doc['value']
            x = np.append(x, float(RPS_value)) 

        # apertura del file in modalità scrittura
        file = microservice + "_5m_stages.csv"
        with open(file, mode='w', newline='') as file:
            # definizione del writer CSV
            writer = csv.writer(file)
            
            # scrittura dell'intestazione delle colonne
            writer.writerow(['requests_per_second', 'response_time'])
            
            # scrittura dei dati
            for i in range(len(x)):
                writer.writerow([x[i], y[i]])

