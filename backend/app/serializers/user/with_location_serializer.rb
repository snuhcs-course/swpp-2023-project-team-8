# frozen_string_literal: true

class User::WithLocationSerializer < ApplicationSerializer
  attributes :id, :name, :email, :created_at, :updated_at,
    :latitude, :longitude
end
