# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

from swpp.v1 import place_pb2 as swpp_dot_v1_dot_place__pb2


class PlaceServiceStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.ListPlaces = channel.unary_unary(
                '/swpp.v1.PlaceService/ListPlaces',
                request_serializer=swpp_dot_v1_dot_place__pb2.ListPlacesRequest.SerializeToString,
                response_deserializer=swpp_dot_v1_dot_place__pb2.ListPlacesResponse.FromString,
                )
        self.Ping = channel.unary_unary(
                '/swpp.v1.PlaceService/Ping',
                request_serializer=swpp_dot_v1_dot_place__pb2.PingRequest.SerializeToString,
                response_deserializer=swpp_dot_v1_dot_place__pb2.PingResponse.FromString,
                )


class PlaceServiceServicer(object):
    """Missing associated documentation comment in .proto file."""

    def ListPlaces(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def Ping(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_PlaceServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'ListPlaces': grpc.unary_unary_rpc_method_handler(
                    servicer.ListPlaces,
                    request_deserializer=swpp_dot_v1_dot_place__pb2.ListPlacesRequest.FromString,
                    response_serializer=swpp_dot_v1_dot_place__pb2.ListPlacesResponse.SerializeToString,
            ),
            'Ping': grpc.unary_unary_rpc_method_handler(
                    servicer.Ping,
                    request_deserializer=swpp_dot_v1_dot_place__pb2.PingRequest.FromString,
                    response_serializer=swpp_dot_v1_dot_place__pb2.PingResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'swpp.v1.PlaceService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class PlaceService(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def ListPlaces(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/swpp.v1.PlaceService/ListPlaces',
            swpp_dot_v1_dot_place__pb2.ListPlacesRequest.SerializeToString,
            swpp_dot_v1_dot_place__pb2.ListPlacesResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def Ping(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/swpp.v1.PlaceService/Ping',
            swpp_dot_v1_dot_place__pb2.PingRequest.SerializeToString,
            swpp_dot_v1_dot_place__pb2.PingResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)
