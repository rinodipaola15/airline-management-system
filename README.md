# Airline Management System
This is a thesis project, developed for my master's thesis in Computer Engineering. It consists of the development of a microservices application using Spring Boot, Docker and Kubernetes and the application of a horizontal autoscaling mechanism, with the goal of improving the performance, in terms of response time, of the application.

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

Enable metrics-server and kube-state-metrics
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
