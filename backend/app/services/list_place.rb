# frozen_string_literal: true

class ListPlace
  def initialize
    @http = ApplicationHttpClient.new("map.snu.ac.kr")
  end

  def get(id)
    response = @http.get("/api/amenities.action", params(id))
    response["rows"].map { |h| Place.from_snu_hash(h) }
  end

  def params(id)
    {
      "convin_inst_knd_cd": id,
      "current_lat_val": "",
      "current_lon_val": ""
    }
  end
end