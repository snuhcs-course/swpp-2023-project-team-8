class MailVerificationToken < ApplicationRecord
  DEV_TOKEN = "DEV_TOKEN"
  VALID_TIME = 5.minutes

  before_validation :set_code

  validates :email, presence: true, snu_mail: true
  validates :code, presence: true, uniqueness: true

  def too_old?
    created_at < VALID_TIME.ago
  end

  class << self
    def valid_code?(code)
      return true if bypass_email_verification?(code)

      token = find_by(code: code)
      token.present? && !token.too_old?
    end

    private

    def bypass_email_verification?(code)
      code == DEV_TOKEN
    end
  end

  private

  # Like "AF38A9"
  def set_code
    self.code = SecureRandom.hex(3).upcase if code.blank?
  end
end
