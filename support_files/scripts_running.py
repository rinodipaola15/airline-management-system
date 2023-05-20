import subprocess

if __name__ == '__main__':

    load_generator_process = subprocess.Popen(["locust", "-f", "load_generator.py", "--host=http://airline-management-system.app.loc/", "--autostart", "--autoquit=0"])
    load_generator_process.wait()

    metrics_fetching_process = subprocess.Popen(["python3", "metrics_fetching.py"])
    metrics_fetching_process.wait()

    metrics_analysis_process = subprocess.Popen(["python3", "metrics_analysis.py"])
    metrics_analysis_process.wait()
    
    distribution_graph_process = subprocess.Popen(["python3", "distribution_graph.py"])
    distribution_graph_process.wait()
