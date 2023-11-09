from concurrent import futures

import grpc

from gen.swpp.v1 import place_pb2
from gen.swpp.v1.place_pb2_grpc import PlaceServiceServicer, add_PlaceServiceServicer_to_server


class PlaceService(PlaceServiceServicer):
    def Ping(self, request, context):
        print("Ping")
        return place_pb2.PingResponse(message='pong')

    # TODO: Implement
    def ListPlaces(self, request, context):
        raise NotImplementedError()


if __name__ == '__main__':
    # Create Unary Server
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    add_PlaceServiceServicer_to_server(PlaceService(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    server.wait_for_termination()
