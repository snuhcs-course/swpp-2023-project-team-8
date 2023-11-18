# frozen_string_literal: true

module IdInOrdered
  extend ActiveSupport::Concern

  included do
    scope :id_in_ordered, ->(ids) { where(id: ids).in_order_of(:id, ids) }
  end
end
