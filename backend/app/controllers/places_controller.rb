# frozen_string_literal: true

class PlacesController < ApplicationController
  def recommend
    user_ids = params[:user_ids].map(&:to_i) + [current_user.id]

    checkins = CheckIn.where(user_id: user_ids).last_check_in_by_user.select(:latitude, :longitude).to_ary

    latitude = checkins.sum { |x| x.latitude } / checkins.size
    longitude = checkins.sum { |x| x.longitude } / checkins.size

    render json: {
      places: RecommendPlaces.new(latitude: latitude, longitude: longitude).run
    }
  end
end
