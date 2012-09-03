require 'gametel_driver/version'
require 'gametel_driver/device'
require 'gametel_driver/request'

module GametelDriver
  def method_missing(method, *args)
    call_method_on_driver(convert_to_java_call(method.to_s), args)
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

  def convert_to_java_call(target)
    return target if target !~ /_/ && target =~ /[A-Z]+.*/
    camel = target.split('_').map{|e| e.capitalize}.join
    camel.sub(camel[0], camel[0].downcase)
  end

  def device
    @device ||= GametelDriver::Device.new
  end

  def request
    @request ||= GametelDriver::Request.new
  end
end
