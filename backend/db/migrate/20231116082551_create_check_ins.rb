class CreateCheckIns < ActiveRecord::Migration[7.1]
  def change
    create_table :check_ins, primary_key: [:user_id, :created_at] do |t|
      t.float :latitude
      t.float :longitude
      t.integer :user_id

      t.timestamps
    end
  end
end
