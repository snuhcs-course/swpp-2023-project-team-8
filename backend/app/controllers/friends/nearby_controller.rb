# frozen_string_literal: true

class Friends::NearbyController < ApplicationController
  def index
    render json: current_user.nearby_friends
  end
end
