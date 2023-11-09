# frozen_string_literal: true

class Place
  PLACE_CODE_MAP = {
    "도서관": 1,
    "식당": 4,
    "카페/편의점": 5,
    "학습공간": 46,
  }.with_indifferent_access.freeze

  class << self
    def fetch_snu_code(name)
      PLACE_CODE_MAP[name]
    end
  end
end
