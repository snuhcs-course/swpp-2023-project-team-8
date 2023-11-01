class User < ApplicationRecord
  include Authenticatable

  has_secure_password

  validates :email, presence: true, uniqueness: true, snu_mail: true
  validates :name, presence: true, uniqueness: true

  before_validation :trim_name!, :trim_email!

  has_many :friendships, dependent: :destroy
  has_many :friends, through: :friendships

  # Friendships where this user is the friend_id
  has_many :inverse_friendships, class_name: "Friendship", foreign_key: "friend_id", dependent: :destroy
  has_many :inverse_friends, through: :inverse_friendships, source: :user

  # Class method to search users by email's local part (before the "@")
  def self.search_by_email_local_part(keyword)
    where('email LIKE ?', "#{keyword}%")
  end

  def befriend(user)
    unless self == user || self.friends.include?(user)
      self.friendships.create(friend: user)
    end
  end

  def unfriend(user)
    friendships.find_by(friend: user)&.destroy
    inverse_friendships.find_by(user: user)&.destroy
  end

  def confirm_friendship(user)
    friendship = inverse_friendships.find_by(user: user)
    friendship.update(confirmed: true) if friendship
  end

  def friends
    confirmed_friends + inverse_confirmed_friends
  end

  def confirmed_friends
    friendships.where(confirmed: true).map(&:friend)
  end

  def inverse_confirmed_friends
    inverse_friendships.where(confirmed: true).map(&:user)
  end

  def pending_friend_requests
    inverse_friendships.where(confirmed: false).map(&:user)
  end

  private

  def trim_name!
    self.name = name.strip unless name.nil?
  end

  def trim_email!
    self.email = email.strip unless email.nil?
  end

end
