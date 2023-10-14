class CreateMailVerificationTokens < ActiveRecord::Migration[7.1]
  def change
    create_table :mail_verification_tokens do |t|
      t.string :code
      t.string :email
      t.datetime :verified_at, null: true

      t.timestamps
    end
  end
end
