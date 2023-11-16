class AddPlaceModel < ActiveRecord::Migration[7.1]
  def change
    create_table :places do |t|
      t.string :name
      t.string :kind
      t.float :latitude
      t.float :longitude
    end
  end
end
