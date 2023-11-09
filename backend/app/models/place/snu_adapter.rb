# frozen_string_literal: true

module Place::SnuAdapter
  extend ActiveSupport::Concern

  PLACE_CODE_MAP = {
    "도서관": 1,
    "식당": 4,
    "카페/편의점": 5,
    "학습공간": 46,
  }.with_indifferent_access.freeze

  class_methods do
    def fetch_snu_code(name)
      Place::SnuAdapter::PLACE_CODE_MAP[name]
    end

    # 서울대 응답 예시
    # {
    #       "flr_cd": "FL01",
    #       "convin_inst_seq": 76,
    #       "flr_nm": "지상1층",
    #       "lon_val": "126.95518",
    #       "convin_inst_knd_cd": "6",
    #       "convin_inst_kor_nm": "파스쿠치",
    #       "convin_inst_eng_nm": "Pascucci",
    #       "dis": "0m",
    #       "path": null,
    #       "lat_val": "37.45922",
    #       "vil_dong_nm": "111",
    #       "convin_inst_knd_kor_nm": "카페",
    #       "up_convin_inst_knd_cd": "5"
    # }
    def from_snu_hash(h)
      new.tap do |place|
        place.id = h["convin_inst_seq"]
        place.name = h["convin_inst_kor_nm"]
        place.latitude = h["lat_val"]
        place.longitude = h["lon_val"]
        place.kind = h["convin_inst_knd_kor_nm"]
      end
    end
  end
end
