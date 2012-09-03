require 'gametel_driver/version'
require 'gametel_driver/device'
require 'gametel_driver/request'
require 'gametel_driver/call_accumulator'
require 'gametel_driver/core_ext/string'

module GametelDriver
  def method_missing(method, *args)
    call_method_on_driver(method.to_s.to_java_call, args)
  end

  def chain_calls(&block)
    block.call accumulator
    puts accumulator.message
    @last_response = device.send(accumulator.message)
    @last_response
  end

  def last_response
    @last_response
  end

  private

  def call_method_on_driver(method, args)
    message = request.build(method, args)
    @last_response = device.send(message)
    @last_response
  end

  def device
    @device ||= GametelDriver::Device.new
  end

  def request
    @request ||= GametelDriver::Request.new
  end

  def accumulator
    @accumulator ||= GametelDriver::CallAccumulator.new
  end
end
