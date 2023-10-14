Rails.application.routes.draw do
  resources :users, only: [:show, :create, :destroy]
  resources :verification_mails, only: [:create]

  get "up" => "rails/health#show", as: :rails_health_check
end
