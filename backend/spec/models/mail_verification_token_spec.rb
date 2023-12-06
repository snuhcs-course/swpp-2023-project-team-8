require 'rails_helper'

RSpec.describe MailVerificationToken, type: :model do
  let(:snu_mail) { "heka1024@snu.ac.kr" }

  describe "#code" do
    context "생성 시에" do
      it "6자리의 랜덤한 문자열이 설정된다" do
        mail_verification_token = MailVerificationToken.create!(email: snu_mail)

        expect(mail_verification_token.code).to be_a(String)
        expect(mail_verification_token.code.length).to eq(6)
      end

      it "중복되지 않는다" do
        mail_verification_token = MailVerificationToken.create!(email: snu_mail)

        try_duplicate = MailVerificationToken.new(email: snu_mail, code: mail_verification_token.code)

        expect(try_duplicate.valid?).to be_falsey
      end
    end
  end

  describe ".valid_code?" do
    context "존재하지 않는 코드를 입력했을 때" do
      it "false를 반환한다" do
        expect(MailVerificationToken.valid_code?("ABCDEFG")).to be_falsey
      end
    end

    context "존재하는 코드를 입력했을 때" do
      context "유효기간이 지나지 않았을 때" do
        it "true를 반환한다" do
          mail_verification_token = MailVerificationToken.create!(email: snu_mail)

          expect(MailVerificationToken.valid_code?(mail_verification_token.code)).to be_truthy
        end
      end

      context "유효기간이 지났을 때" do
        it "false를 반환한다" do
          mail_verification_token = MailVerificationToken.create!(email: snu_mail)
          mail_verification_token.update(created_at: Time.now - MailVerificationToken::VALID_TIME - 1.second)

          expect(MailVerificationToken.valid_code?(mail_verification_token.code)).to be_falsey
        end
      end

      context "개발자 코드를 입력했을 때" do
        it "true를 반환한다" do
          dev_code = MailVerificationToken::DEV_TOKEN
          expect(MailVerificationToken.valid_code?(dev_code)).to be_truthy
        end
      end
    end
  end

  context "이미 가입된 이메일로 발송을 시도한다면" do
    let!(:user) { User.create!(email: snu_mail, name: "test", password: "1234") }

    it "실패한다" do
      mail_verification_token = MailVerificationToken.new(email: snu_mail)

      expect(mail_verification_token.valid?).to be_falsey
    end
  end
end
