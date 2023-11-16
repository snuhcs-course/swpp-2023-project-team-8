class CreateUserMissions < ActiveRecord::Migration[7.1]
  def change
    create_table :user_missions do |t|
      t.references :user, null: false, foreign_key: true
      t.references :mission, null: false, foreign_key: true
      t.integer :progress, default: 0
      t.boolean :completed, default: false

      t.timestamps
    end
  end
end
