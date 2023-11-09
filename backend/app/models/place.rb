# frozen_string_literal: true

class Place
  include SnuAdapter
  include ActiveModel::Model
  include ActiveModel::Attributes

  attribute :id
  attribute :name # 어차피 이게 Unique할 거 같긴 한데?
  attribute :latitude, :float
  attribute :longitude, :float
  attribute :kind # 도서관, 식당, 카페/편의점, 학습공간
end
