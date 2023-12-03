class MeetUpSerializer < ApplicationSerializer
  attributes :id, :title, :description, :meet_at, :is_public, :created_at

  has_many :users, serializer: UserSerializer
  belongs_to :place, serializer: PlaceSerializer
end
