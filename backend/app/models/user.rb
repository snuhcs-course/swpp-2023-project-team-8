class User < ApplicationRecord
  include Authenticatable

  has_secure_password

  validates :email, presence: true, uniqueness: true, snu_mail: true
  validates :name, presence: true, uniqueness: true

  before_validation :trim_name!, :trim_email!

  private

  def trim_name!
    self.name = name.strip unless name.nil?
  end

  def trim_email!
    self.email = email.strip unless email.nil?
  end
end
