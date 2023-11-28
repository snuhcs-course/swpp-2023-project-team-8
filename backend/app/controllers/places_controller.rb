# frozen_string_literal: true

class PlacesController < ApplicationController
  skip_before_action :require_login

  def recommend
    # TODO: User 입력으로 변경하기
    latitude = params[:latitude].to_f
    longitude = params[:longitude].to_f

    render json: {
      places: RecommendPlaces.new(latitude: latitude, longitude: longitude).run
    }
  end
end
