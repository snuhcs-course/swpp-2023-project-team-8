class CreateUserMeetUps < ActiveRecord::Migration[7.1]
  def change
    create_table :user_meet_ups do |t|
      t.references :user, null: false
      t.references :meet_up, null: false

      t.timestamps
    end
  end
end
