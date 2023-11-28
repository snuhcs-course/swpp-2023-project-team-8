# frozen_string_literal: true

module User::CheckInable
  extend ActiveSupport::Concern

  included do
    has_many :check_ins
  end

  def check_in!(latitude:, longitude:)
    CheckIn.create!(user_id: id, latitude:, longitude:)
  end

  def latest_check_in
    @latest_check_in ||= check_ins.order(created_at: :desc).first!
  end

  delegate :latitude, :longitude, to: :latest_check_in

  alias_method :last_check_in, :latest_check_in
end
