import os
from concurrent import futures

import grpc

from swpp.v1 import place_pb2
from swpp.v1.place_pb2_grpc import PlaceServiceServicer, add_PlaceServiceServicer_to_server


class PlaceService(PlaceServiceServicer):
    def Ping(self, request, context):
        print("Ping")
        return place_pb2.PingResponse(message='pong')

    # TODO: Implement
    def ListPlaces(self, request, context):
        raise NotImplementedError()


if __name__ == '__main__':
    port = os.getenv("PORT", "50051")

    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    add_PlaceServiceServicer_to_server(PlaceService(), server)
    server.add_insecure_port(f"[::]:{port}")
    server.start()
    print(f"Server started on port {port}")
    server.wait_for_termination()
