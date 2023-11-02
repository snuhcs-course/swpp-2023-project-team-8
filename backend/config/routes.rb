Rails.application.routes.draw do
  get 'users/search', to: 'users#search', as: 'user_search'
  resources :users, only: [:show, :create, :destroy]
  resources :verification_mails, only: [:create]
  post '/auth/login', to: 'authentication#create'

  resources :users, only: [:show, :create, :destroy] do
    resources :pending_requests, controller: "users/pending_requests", only: [:index]
  end

  resources :friends, only: [:index, :create, :destroy] do
    post 'confirm', on: :member
  end
  get 'friends/pending_requests', to: 'friendships#pending_requests'

  get "up" => "rails/health#show", as: :rails_health_check
end
