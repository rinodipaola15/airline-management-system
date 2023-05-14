from mongodb_lib import *
from prometheus_lib import *
from datetime import datetime
import csv

microservices = ['user-service', 'flight-service', 'flight-reservation-service']
mode = "PROVA_saturation"

if __name__ == '__main__':
    
    prom = PrometheusConnect(url ="http://airline-management-system.app.loc:30000/", disable_ssl=True)
    
    # prometheus query settings
    start_time = parse_datetime("300s")
    end_time = parse_datetime("now")
    step = "15s"
    
    # db connection data
    hostname = "localhost"
    port = 27017 
    database = "metricsDB"
    
    # db connection
    db = db_connect(hostname, port, database)    
    
    mycol = db[mode + "_user-service_response_time"]
    mycol.drop()
    mycol = db[mode + "_flight-service_response_time"]
    mycol.drop()
    mycol = db[mode + "_flight-reservation-service_response_time"]
    mycol.drop()
    
    mycol = db[mode + "_user-service_requests_per_second"]
    mycol.drop()
    mycol = db[mode + "_flight-service_requests_per_second"]
    mycol.drop()
    mycol = db[mode + "_flight-reservation-service_requests_per_second"]
    mycol.drop()
    
    mycol = db[mode + "_user-service_cpu_usage"]
    mycol.drop()
    mycol = db[mode + "_flight-service_cpu_usage"]
    mycol.drop()
    mycol = db[mode + "_flight-reservation-service_cpu_usage"]
    mycol.drop()
    
    mycol = db[mode + "_user-service_pod_number"]
    mycol.drop()
    mycol = db[mode + "_flight-service_pod_number"]
    mycol.drop()
    mycol = db[mode + "_flight-reservation-service_pod_number"]
    mycol.drop()
      

    for microservice in microservices:
        
        # prometheus queries
        
        RT_query = 'sum(rate(http_server_requests_seconds_sum{app="' + microservice + '", exception="None", uri!="/actuator/prometheus"}[30s])) / sum(rate(http_server_requests_seconds_count{app="' + microservice + '",exception="None", uri!="/actuator/prometheus"}[30s]))'
        RPS_query = 'sum(rate(http_server_requests_seconds_count{app="' + microservice + '", exception="None", uri!="/actuator/prometheus"}[30s]))'
        cpu_usage_query = 'max(rate(container_cpu_usage_seconds_total{pod=~"' + microservice + '.*", pod!~"' + microservice + '-db.*"}[1m])) * 100' # da 0 a 400% (se divido per il numero di core -> da 0 a 100%)
        #pod_number_query = 'count(kube_pod_info{pod=~"' + microservice + '.*", pod!~"' + microservice + '-db.*"})'
        #cpu_usage_query = 'max(rate(container_cpu_usage_seconds_total{pod=~"' + microservice + '.*", pod!~"' + microservice + '-db.*"}[10s])) / sum(kube_pod_container_resource_limits{container="' + microservice + '", resource="cpu"}) * 100'
        #memory_usage_query = 'max(rate(container_memory_working_set_bytes{pod=~"' + microservice + '.*", pod!~"' + microservice + '-db.*"}[1m])) * 100'
        #memory_usage_query = 'max(rate(container_memory_working_set_bytes{pod=~"' + microservice + '.*", pod!~"' + microservice + '-db.*"}[10s])) / sum(kube_pod_container_resource_limits{container="' + microservice + '", resource="memory"}) * 100'
       
        RT_data = prom.custom_query_range(RT_query, start_time=start_time, end_time=end_time, step=step)
        RPS_data = get_metric_values(prom, RPS_query, start_time, end_time, step)
        cpu_data = get_metric_values(prom, cpu_usage_query, start_time, end_time, step)
        
        #if microservice=="flight-reservation-service":
        #    pod_number_data = get_metric_values(prom, pod_number_query, start_time, end_time, step)
            
        #memory_data = get_metric_values(prom, memory_usage_query, start_time, end_time, step)

        
        # apertura del file in modalità append
        file = mode + "_" + microservice + "_RT.csv"
        with open(file, mode='a', newline='') as file:
            # definizione del writer CSV
            writer = csv.writer(file)
            writer.writerow(['timestamp', 'response_time'])
        
            # insert RT values into db
            print("Response time values:")
            RT_values = RT_data[0]["values"]
            for res in RT_values:
                timestamp = res[0]
                value = res[1]
                if(value=="NaN"):
                    value = "0"
                print(timestamp, value)
                doc = {"timestamp": datetime.fromtimestamp(float(timestamp)), "value": value}
                coll = mode + "_" + microservice + "_response_time"
                insert_document_into_collection(db, coll, doc)
                # insert RT values into cvs file
                writer.writerow([timestamp, value])        
            
        # apertura del file in modalità append
        file = mode + "_" + microservice + "_RPS.csv"
        with open(file, mode='a', newline='') as file:
            # definizione del writer CSV
            writer = csv.writer(file)
            writer.writerow(['timestamp', 'requests_per_second'])

            # insert RPS values into db
            print("Requests per second values:")
            RPS_values = RPS_data[0]["values"]
            for res in RPS_values:
                timestamp = res[0]
                value = res[1]
                print(timestamp, value)
                doc = {"timestamp": datetime.fromtimestamp(float(timestamp)), "value": value}
                coll = mode + "_" + microservice + "_requests_per_second"
                insert_document_into_collection(db, coll, doc)
                # insert RPS values into cvs file
                writer.writerow([timestamp, value])
            
        # apertura del file in modalità append
        file = mode + "_" + microservice + "_CPU.csv"
        with open(file, mode='a', newline='') as file:
            # definizione del writer CSV
            writer = csv.writer(file)
            writer.writerow(['timestamp', 'CPU_usage'])
            
            # insert cpu values into db
            print("CPU usage values:")
            cpu_values = cpu_data[0]["values"]
            for res in cpu_values:
                timestamp = res[0]
                value = res[1]
                print(timestamp, value)
                doc = {"timestamp": datetime.fromtimestamp(float(timestamp)), "value": value}
                coll = mode + "_" + microservice + "_cpu_usage"
                insert_document_into_collection(db, coll, doc)
                # insert CPU values into cvs file
                writer.writerow([timestamp, value])
                
        '''if microservice=="flight-reservation-service":        
            # apertura del file in modalità append
            file = mode + "_" + microservice + "_pod_number.csv"
            with open(file, mode='a', newline='') as file:
                # definizione del writer CSV
                writer = csv.writer(file)
                writer.writerow(['timestamp', 'pod_number'])
                
                # insert pod number values into db
                print("Pod number values:")
                pod_number_values = pod_number_data[0]["values"]
                for res in pod_number_values:
                    timestamp = res[0]
                    value = res[1]
                    print(timestamp, value)
                    doc = {"timestamp": datetime.fromtimestamp(float(timestamp)), "value": value}
                    coll = mode + "_" + microservice + "_pod_number"
                    insert_document_into_collection(db, coll, doc)
                    # insert pod number values into cvs file
                    writer.writerow([timestamp, value])'''