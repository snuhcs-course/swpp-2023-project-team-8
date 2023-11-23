# frozen_string_literal: true

class CheckInsController < ApplicationController
  def index
    render json: {check_ins: current_user.check_ins}
  end

  def create
    check_in = current_user.check_in!(
      latitude: check_in_params[:latitude],
      longitude: check_in_params[:longitude]
    )
    render json: {check_in: check_in}, status: :created
  rescue ActiveRecord::RecordInvalid, ArgumentError => e
    render json: {error: e.message}, status: :unprocessable_entity
  end

  private

  def check_in_params
    params.require(:check_in).permit(:latitude, :longitude)
  end
end
