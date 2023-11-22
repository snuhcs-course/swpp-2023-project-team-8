import google
import grpc

from swpp.v1 import place_pb2
from swpp.v1.place_pb2_grpc import PlaceServiceStub

with grpc.insecure_channel('localhost:50051') as channel:
    stub = PlaceServiceStub(channel)
    response = stub.ListPlaces(
        place_pb2.ListPlacesRequest(location=
                                    google.type.latlng_pb2.LatLng(latitude=37.1, longitude=126.1)))
    print("Greeter client received: " + response.place_ids)
