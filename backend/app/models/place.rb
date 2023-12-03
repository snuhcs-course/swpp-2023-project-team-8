# frozen_string_literal: true

class Place < ApplicationRecord
  include SnuAdapter
  include IdInOrdered

  has_many :meet_up
end
