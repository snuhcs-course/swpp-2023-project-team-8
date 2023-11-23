# frozen_string_literal: true

class Friends::NearbyController < ApplicationController
  def index
    render json: {
      friends: current_user.nearby_friends
    }, each_serializer: User::WithLocationSerializer
  rescue ActiveRecord::RecordNotFound => e
    render json: {error: "#{e.model} not found"}, status: :not_found
  end
end
