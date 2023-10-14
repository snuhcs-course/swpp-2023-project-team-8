class MailVerificationTokenMailer < ApplicationMailer
  default from: "shafshaf@gmail.com"

  def new_mail
    @mail_verification_token = params[:mail_verification_token]

    mail(to: @mail_verification_token.email, subject: "이메일을 인증해주세요!")
  end
end


