class CreateMeetUps < ActiveRecord::Migration[7.1]
  def change
    create_table :meet_ups do |t|
      t.string :title
      t.string :description
      t.datetime :meet_at
      t.boolean :public

      t.timestamps
    end
  end
end
