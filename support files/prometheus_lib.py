from prometheus_api_client import PrometheusConnect, MetricsList
from prometheus_api_client.utils import parse_datetime

def get_metric_values(prom, query, start_time, end_time, step):
    return prom.custom_query_range(
        query = query, 
        start_time=start_time,
        end_time=end_time,
        step=step
    )