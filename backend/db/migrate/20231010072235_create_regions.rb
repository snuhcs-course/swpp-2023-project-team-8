class CreateRegions < ActiveRecord::Migration[7.1]
  def change
    create_table :regions do |t|
      t.geometry "geom", limit: {:srid=>4326, :type=>"multi_polygon"}, null: false
      t.string :name

      t.timestamps
    end
  end
end
