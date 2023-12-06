class MissionSerializer < ActiveModel::Serializer
  attributes :id, :name, :description, :user_completed, :user_progress

  def user_completed
    # Convert to boolean as 'user_completed' can be nil
    object.user_completed == true
  end
end
