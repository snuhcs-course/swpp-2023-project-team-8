# frozen_string_literal: true
require "net/http"

class ApplicationHttpClient
  include HashUtils

  OPTS = {}.freeze
  PARSER = JSON
  ENCODING = "euc-kr"

  def initialize(host)
    @host = host
  end

  def get(path, params = OPTS)
    PARSER.parse(Net::HTTP.get(build_url(path, params)).force_encoding(ENCODING))
  end

  private

  def build_url(path, params)
    URI::HTTPS.build(host: @host, path: path, query: stringify(params).to_query)
  end
end
