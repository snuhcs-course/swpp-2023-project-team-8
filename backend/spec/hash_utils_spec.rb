# frozen_string_literal: true

require "rails_helper"

RSpec.describe HashUtils do
  include described_class

  describe "#stringify" do
    context "when hash has symbol keys and integer values" do
      let(:hash) { { a: 1, b: 2 } }

      it "returns hash with string keys and string values" do
        expect(stringify(hash)).to eq({ "a" => "1", "b" => "2" })
      end
    end
  end
end
