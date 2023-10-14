# frozen_string_literal: true

class UserSerializer < ApplicationSerializer
  attributes :id, :name, :email, :created_at, :updated_at
end
