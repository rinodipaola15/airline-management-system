import numpy as np
import matplotlib.pyplot as plt
from mongodb_lib import *
from prometheus_lib import *
from sklearn.linear_model import LinearRegression

microservices = ['flight-reservation-service']
mode = "saturation"

def linear_regression(x, y, title, xlabel_title, ylabel_title):

    # Fit della regressione lineare sui dati x e y
    regressor = LinearRegression()
    regressor.fit(x.reshape(-1, 1), y.reshape(-1, 1))   
    
    # Plot dei dati di input x e y
    plt.scatter(x, y, color='blue')
    
    # Calcolo dei valori di output della regressione lineare
    x_range = np.linspace(min(x), max(x), 100)
    y_pred = regressor.predict(x_range.reshape(-1, 1))

    # Plot della regressione lineare
    plt.plot(x_range, y_pred, color='red', linewidth=2)

    # Aggiunta di titoli e label degli assi
    plt.title(title)
    plt.xlabel(xlabel_title)
    plt.ylabel(ylabel_title)

    # Mostra il plot
    plt.show()


if __name__ == '__main__':
    
    # db connection data
    database = "metricsDB"
    
    # connection to local mongodb
    client = MongoClient('mongodb://localhost:27017/')

    # db connection
    db = client[database]
    
    for microservice in microservices:
    
        RT_collection = db[mode + "_" + microservice + "_response_time"]
        RPS_collection = db[mode + "_" + microservice + "_requests_per_second"]
        CPU_collection = db[mode + "_" + microservice + "_cpu_usage"]
        #PODN_collection = db[mode + "_" + microservice + "_pod_number"]

        # get all documents from collections
        RT_documents = RT_collection.find()
        RPS_documents = RPS_collection.find()
        CPU_documents = CPU_collection.find()
        #PODN_documents = PODN_collection.find()
    
        # numpy arrays initialization
        x_RPS = np.array([], dtype=np.float64)
        y_RT = np.array([], dtype=np.float64)
        y_CPU = np.array([], dtype=np.float64)
        #y_PODN = np.array([], dtype=np.float64)
        
        # add RPS values to x
        for RPS_doc in RPS_documents:
            RPS_value = RPS_doc['value']
            x_RPS = np.append(x_RPS, float(RPS_value))
        
        # add RT values to y
        for RT_doc in RT_documents:
            RT_value = RT_doc['value']
            y_RT = np.append(y_RT, float(RT_value)) 
            
        # add CPU values to y
        for CPU_doc in CPU_documents:
            CPU_value = CPU_doc['value']
            y_CPU = np.append(y_CPU, float(CPU_value))   
            
        # add Pod number values to y
        #for PODN_doc in PODN_documents:
        #    PODN_value = PODN_doc['value']
        #    y_PODN = np.append(y_PODN, float(PODN_value))    
    

        # RT linear regression
        linear_regression(x_RPS, y_RT, "Regressione Lineare RT - " + microservice, "Requests per second", "Response time")
        
        # CPU usage plot
        plt.scatter(x_RPS, y_CPU)
        plt.xlabel('Requests per second')
        plt.ylabel('CPU usage')
        plt.title('Plot CPU - ' + microservice)
        plt.ylim([0, 145])
        plt.show()
        
        #if microservice=="flight-reservation-service":
        
            # Pod number plot
            #x_r = np.repeat(x_RPS, len(y_PODN)//len(x_RPS))
            #plt.bar(x_r, y_PODN)
            #plt.xlabel('Requests per second')
            #plt.ylabel('Pods number')
            #plt.title('Plot pods number - ' + microservice)
            #plt.show()