from locust import HttpUser, TaskSet, task, constant
from locust import LoadTestShape


class UserTasks(TaskSet):
    @task
    def get_users(self):
        self.client.get("user-service/users/getAllUsers")    
    @task
    def get_flights(self):
        self.client.get("flight-service/flights/getAllFlights")
    @task
    def get_flight_reservations(self):
        self.client.get("flight-reservation-service/flight_reservations/getAllFlightReservations")


class WebsiteUser(HttpUser):
    wait_time = constant(1)
    tasks = [UserTasks]


class StagesShape(LoadTestShape):

    stages = [
        {"duration": 300, "users": 300, "spawn_rate": 1},
    ]

    def tick(self):
        run_time = self.get_run_time()

        for stage in self.stages:
            if run_time < stage["duration"]:
                tick_data = (stage["users"], stage["spawn_rate"])
                return tick_data

        return None