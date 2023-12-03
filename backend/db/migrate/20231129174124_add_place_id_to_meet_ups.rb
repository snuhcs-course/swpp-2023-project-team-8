class AddPlaceIdToMeetUps < ActiveRecord::Migration[7.1]
  def change
    add_column :meet_ups, :place_id, :integer
  end
end
