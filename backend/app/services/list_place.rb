# frozen_string_literal: true

class ListPlace
  PARAMS = {
    "convin_inst_knd_cd": 46,
    "current_lat_val": "",
    "current_lon_val": ""
  }

  def initialize
    @http = ApplicationHttpClient.new("map.snu.ac.kr")
  end

  def get
    response = @http.get("/api/amenities.action", PARAMS)
    response["rows"].map { |h| Place.from_snu_hash(h) }
  end
end