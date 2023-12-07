Rails.application.routes.draw do
  get 'users/search', to: 'users#search', as: 'user_search'
  resources :verification_mails, only: [:create]
  post '/auth/login', to: 'authentication#create'

  resources :users, only: [:index, :show, :create, :destroy] do
    put "change_image_id", on: :member
    resources :pending_requests, controller: "users/pending_requests", only: [:index]
  end

  resources :friends, only: [:index, :create, :destroy] do
    post 'confirm', on: :member

    collection do
      resources "nearby", controller: "friends/nearby", only: [:index]
    end
  end

  resources :places do
    get :recommend, on: :collection
  end

  resources :missions, only: [:index]

  get "up" => "rails/health#show", as: :rails_health_check

  resources :check_ins, only: [:index, :create]

  resources :meet_ups, only: [:create, :index, :show]

  resources :access, only: [:create]
end
