require 'brazenhead/version'
require 'brazenhead/device'
require 'brazenhead/request'
require 'brazenhead/call_accumulator'
require 'brazenhead/core_ext/string'

module Brazenhead
  def method_missing(method, *args)
    call_method_on_driver(method.to_s.to_java_call, args)
  end

  def chain_calls(&block)
    accumulator.clear
    block.call accumulator
    @last_response = device.send(accumulator.message)
    @last_response
  end

  def last_response
    @last_response
  end

  def last_json
    device.last_json
  end

  private

  def call_method_on_driver(method, args)
    message = request.build(method, args)
    @last_response = device.send(message)
    @last_response
  end

  def device
    @device ||= Brazenhead::Device.new
  end

  def request
    @request ||= Brazenhead::Request.new
  end

  def accumulator
    @accumulator ||= Brazenhead::CallAccumulator.new
  end
end
