# frozen_string_literal: true

class PlaceSerializer < ApplicationSerializer
  attributes :id, :name, :kind, :latitude, :longitude
end
