# frozen_string_literal: true

class Place < ApplicationRecord
  include SnuAdapter
  include IdInOrdered
end
