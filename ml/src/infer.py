from math import radians, sin, cos, sqrt, atan2

import pandas as pd  # data processing, CSV file I/O (e.g. pd.read_csv)
from sklearn.cluster import KMeans


# Assuming df is your dataframe containing venue information, and kmeans is your KMeans model.

def haversine(lat1, lon1, lat2, lon2):
    # Function to calculate the distance between two points on the Earth's surface using Haversine formula
    R = 6371  # Radius of the Earth in kilometers

    dlat = radians(lat2 - lat1)
    dlon = radians(lon2 - lon1)

    a = sin(dlat / 2) ** 2 + cos(radians(lat1)) * cos(radians(lat2)) * sin(dlon / 2) ** 2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))

    distance = R * c  # Distance in kilometers
    return distance


# Assuming kmeans is your trained KMeans model and X_sample contains the longitude and latitude.

def recommend_venues(gdf, kmeans, longitude, latitude):
    # Predicting the cluster for the input coordinates
    predicted_cluster = kmeans.predict([[longitude, latitude]])[0]

    # Filter venues in the predicted cluster
    cluster_venues = gdf[gdf['cluster'] == predicted_cluster]
    # print(cluster_venues)

    distances = cluster_venues.apply(lambda row: haversine(latitude, longitude, row['latitude'], row['longitude']),
                                     axis=1)
    # min_distance_index = distances.idxmin()
    min_distance_index = distances.nsmallest(4).index

    # Get the recommended venue name
    # venue_name = gdf.loc[min_distance_index, 'spotname']
    venue_ids = list(gdf.loc[min_distance_index, 'spotid'].values)
    return venue_ids


# recommendation = recommend_venues(venues, kmeans, 37.4552, 126.95206)
# print(recommendation)


class Infer:
    """
    추론 과정을 담당하는 클래스
    """

    @classmethod
    def from_input_path(cls, input_path):
        """input_path에는 cluster 정보가 있어야한다."""

        infer = cls()
        infer.init_veunes(input_path)
        infer.init_kmeans()
        return infer

    def __init__(self, venues=None, kmeans=None):
        self.venues = venues
        self.kmeans = kmeans

    def init_veunes(self, path):
        self.venues = pd.read_csv(path, sep='\t', header=0)

    def init_kmeans(self):
        self.kmeans = KMeans(n_clusters=6, init='k-means++')
        X_sample = self.venues[['longitude', 'latitude']]
        self.kmeans.fit(X_sample)

    def run(self, longitude: float, latitude: float):
        """추론 결과를 반환한다. longitude. latitude에 유저의 위치를 넣는다"""

        return recommend_venues(self.venues, self.kmeans, longitude, latitude)
