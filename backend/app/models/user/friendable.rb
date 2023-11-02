# frozen_string_literal: true

# 친구 관련된 관심사를 담당한다
module User::Friendable
  extend ActiveSupport::Concern

  included do
    has_many :friendships, dependent: :destroy
    has_many :friends, through: :friendships

    # Friendships where this user is the friend_id
    has_many :inverse_friendships, class_name: "Friendship", foreign_key: "friend_id", dependent: :destroy
    has_many :inverse_friends, through: :inverse_friendships, source: :user
  end

  def befriend(user)
    return if user.id == id || already_friend?(user)

    friendships.create_or_find_by(friend_id: user.id)
  end

  def unfriend(user)
    friendships.find_by(friend: user)&.destroy
    inverse_friendships.find_by(user: user)&.destroy
  end

  def confirm_friendship(user)
    friendship = inverse_friendships.find_by(user: user)
    friendship.confirm if friendship
  end

  def friends
    confirmed_friends + inverse_confirmed_friends
  end

  def confirmed_friends
    friendships.confirmed.map(&:friend)
  end

  def inverse_confirmed_friends
    inverse_friendships.confirmed.map(&:user)
  end

  def pending_friendships
    inverse_friendships.pending
  end

  private

  def already_friend?(user)
    friends.include?(user) || inverse_friends.include?(user)
  end
end
