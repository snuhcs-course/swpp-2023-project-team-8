# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `bin/rails
# db:schema:load`. When creating a new database, `bin/rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema[7.1].define(version: 2023_11_29_174124) do
  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"
  enable_extension "postgis"

  create_table "check_ins", primary_key: ["user_id", "created_at"], force: :cascade do |t|
    t.float "latitude"
    t.float "longitude"
    t.integer "user_id", null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "friendships", force: :cascade do |t|
    t.integer "user_id"
    t.integer "friend_id"
    t.boolean "confirmed", default: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["friend_id"], name: "index_friendships_on_friend_id"
    t.index ["user_id", "friend_id"], name: "index_friendships_on_user_id_and_friend_id", unique: true
    t.index ["user_id"], name: "index_friendships_on_user_id"
  end

  create_table "mail_verification_tokens", force: :cascade do |t|
    t.string "code"
    t.string "email"
    t.datetime "verified_at"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "meet_ups", force: :cascade do |t|
    t.string "title"
    t.string "description"
    t.datetime "meet_at"
    t.boolean "public"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.integer "place_id"
  end

  create_table "missions", force: :cascade do |t|
    t.string "name"
    t.text "description"
    t.string "mission_type"
    t.integer "target"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "places", force: :cascade do |t|
    t.string "name"
    t.string "kind"
    t.float "latitude"
    t.float "longitude"
  end

  create_table "regions", force: :cascade do |t|
    t.geometry "geom", limit: {:srid=>4326, :type=>"multi_polygon"}, null: false
    t.string "name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "user_locations", force: :cascade do |t|
    t.bigint "user_id", null: false
    t.geography "location", limit: {:srid=>4326, :type=>"st_point", :geographic=>true}
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["location"], name: "index_user_locations_on_location", using: :gist
    t.index ["user_id"], name: "index_user_locations_on_user_id"
  end

  create_table "user_meet_ups", force: :cascade do |t|
    t.bigint "user_id", null: false
    t.bigint "meet_up_id", null: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["meet_up_id"], name: "index_user_meet_ups_on_meet_up_id"
    t.index ["user_id"], name: "index_user_meet_ups_on_user_id"
  end

  create_table "user_missions", force: :cascade do |t|
    t.bigint "user_id", null: false
    t.bigint "mission_id", null: false
    t.integer "progress", default: 0
    t.boolean "completed", default: false
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["mission_id"], name: "index_user_missions_on_mission_id"
    t.index ["user_id"], name: "index_user_missions_on_user_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "name"
    t.string "email"
    t.string "password_digest"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "auth_token"
    t.index ["auth_token"], name: "index_users_on_auth_token", unique: true
  end

  add_foreign_key "user_locations", "users"
  add_foreign_key "user_meet_ups", "meet_ups"
  add_foreign_key "user_meet_ups", "users"
  add_foreign_key "user_missions", "missions"
  add_foreign_key "user_missions", "users"
end
