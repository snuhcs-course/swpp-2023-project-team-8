import pandas as pd  # data processing, CSV file I/O (e.g. pd.read_csv)
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score

input_path = 'data/snuspots_latlon.txt'
output_path = "data/clustered_output.txt"

venues = pd.read_csv(input_path, sep='\t', header=0)
venues.head()

kmeans = KMeans(n_clusters=6, init='k-means++')
X_sample = venues[['longitude', 'latitude']]
kmeans.fit(X_sample)

y = kmeans.labels_
print("k = 10", " silhouette_score ", silhouette_score(X_sample, y, metric='euclidean'))
venues['cluster'] = kmeans.predict(venues[['longitude', 'latitude']])
venues.to_csv(output_path, index=False, sep='\t')
