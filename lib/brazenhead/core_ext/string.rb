class String
  def to_java_call
    return self if self !~ /_/ && self =~ /[A-Z]+.*/
    camel = self.split('_').map{|e| e.capitalize}.join
    camel.sub(camel[0], camel[0].downcase)
  end
end
