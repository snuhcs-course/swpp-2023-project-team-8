# frozen_string_literal: true

class RecommendPlaces
  HOST = 'localhost'
  PORT = 50051

  def initialize(latitude:, longitude:)
    @latitude = latitude
    @longitude = longitude
  end

  def run
    response = stub.list_places(Swpp::V1::ListPlacesRequest.new(location: latlng))
    ids = response.place_ids.to_ary

    Place.id_in_ordered(ids)
  end

  private

  def stub
    @stub ||= Swpp::V1::PlaceService::Stub.new("#{HOST}:#{PORT}", :this_channel_is_insecure)
  end

  def latlng
    Google::Type::LatLng.new(latitude: @latitude, longitude: @longitude)
  end
end
