class User < ApplicationRecord
  include Authenticatable
  include Friendable
  include CheckInable

  has_many :user_missions
  has_many :missions, through: :user_missions

  has_secure_password

  validates :email, presence: true, uniqueness: true, snu_mail: true
  validates :name, presence: true, uniqueness: true

  before_validation :trim_name!, :trim_email!

  scope :search_by_email_local_part, ->(keyword) { where('email LIKE ?', "#{keyword}%") }

  def nearby_friends
    User.where(id: CheckIn.order_by_distance(last_check_in.latitude, last_check_in.longitude)
      .where(user: all_friends)
      .select(:user_id))
  end

  private

  def trim_name!
    self.name = name.strip unless name.nil?
  end

  def trim_email!
    self.email = email.strip unless email.nil?
  end
end
