task :dump do
  Place::SnuAdapter::PLACE_CODE_MAP.values.each do |place_code|
    ListPlace.new.get(place_code).each do |place|
      place.save
    end
  end
end