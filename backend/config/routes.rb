Rails.application.routes.draw do
  get 'users/search', to: 'users#search', as: 'user_search'
  resources :users, only: [:show, :create, :destroy]
  resources :verification_mails, only: [:create]
  post '/auth/login', to: 'authentication#create'

  resources :friends, controller: 'friendships', only: [:index, :create, :destroy] do
    post 'confirm', on: :member
  end
  get 'friends/pending_requests', to: 'friendships#pending_requests'

  get "up" => "rails/health#show", as: :rails_health_check
end
