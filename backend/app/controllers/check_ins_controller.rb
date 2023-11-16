# frozen_string_literal: true

class CheckInsController < ApplicationController
  def index
    render json: {check_ins: current_user.check_ins}
  end
end
