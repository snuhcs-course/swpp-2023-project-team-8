class AddImageIdToUser < ActiveRecord::Migration[7.1]
  def change
    add_column :users, :image_id, :integer, null: true
  end
end
