# frozen_string_literal: true

module HashUtils
  def stringify(h)
    h.map { |k, v| [k.to_s, v.to_s] }.to_h
  end
end
