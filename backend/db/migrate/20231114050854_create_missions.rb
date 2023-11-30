class CreateMissions < ActiveRecord::Migration[7.1]
  def change
    create_table :missions do |t|
      t.string :name
      t.text :description
      t.string :mission_type
      t.integer :target

      t.timestamps
    end
  end
end
