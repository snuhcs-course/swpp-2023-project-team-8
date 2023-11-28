import os
from concurrent import futures

import grpc

from src.infer import Infer
from swpp.v1 import place_pb2
from swpp.v1.place_pb2_grpc import PlaceServiceServicer, add_PlaceServiceServicer_to_server


class PlaceService(PlaceServiceServicer):
    def __init__(self, infer: Infer):
        self.infer = infer

    def Ping(self, request, context):
        print("Ping")
        return place_pb2.PingResponse(message='pong')

    def ListPlaces(self, request, context):
        location = request.location
        ids = self.infer.run(location.longitude, location.latitude)
        print(f"Infer: {ids}")
        return place_pb2.ListPlacesResponse(place_ids=ids)
        # raise NotImplementedError()


if __name__ == '__main__':
    port = os.getenv("PORT", "50051")

    input_path = "data/clustered_output.txt"
    infer = Infer.from_input_path(input_path)

    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    add_PlaceServiceServicer_to_server(PlaceService(infer), server)
    server.add_insecure_port(f"[::]:{port}")
    server.start()
    print(f"Server started on port {port}")
    server.wait_for_termination()
