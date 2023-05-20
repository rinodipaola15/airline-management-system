import numpy as np
import matplotlib.pyplot as plt
from mongodb_lib import *

microservices = ['user-service', 'flight-service', 'flight-reservation-service']
mode = "test_collection"
num_stages = 4

if __name__ == '__main__':
    
    # db connection data
    database = "metricsDB"
    
    # connection to local mongodb
    client = MongoClient('mongodb://localhost:27017/')

    # db connection
    db = client[database]
    
    for microservice in microservices:
    
        RT_collection = db[mode + "_" + microservice + "_response_time"]

        # get all documents from collections
        RT_documents = RT_collection.find()
    
        # numpy arrays initialization
        y_RT = np.array([], dtype=np.float64)
                
        # add RT values to y
        for RT_doc in RT_documents:
            RT_value = RT_doc['value']
            y_RT = np.append(y_RT, float(RT_value)) 
            
        y_RT*=1000 # s to ms
        
        print(microservice + " - RT values:")
        print(y_RT)

        #define bin edges
        bins = list(range(0, 500, 10))
        
        # create a histogram for each block of samples
        for i in range(num_stages):
            block = y_RT[i*((len(y_RT))//num_stages):(i+1)*((len(y_RT))//4)]
            print("RT block " + str(i+1) + ":")
            print(block)
    
            plt.hist(block, bins=bins, edgecolor='black')
            plt.xlabel('Response Time')
            plt.ylabel('Count')
            plt.show()
