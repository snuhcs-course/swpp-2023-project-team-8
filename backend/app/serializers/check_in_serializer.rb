# frozen_string_literal: true

class CheckInSerializer < ApplicationSerializer
  attributes :latitude, :longitude, :created_at
end
