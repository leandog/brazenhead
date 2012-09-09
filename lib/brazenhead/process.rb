require 'childprocess'
require 'tempfile'

module Brazenhead
  class Process

    attr_accessor :last_stdout, :last_stderr

    def run(process, *args)
      process = ChildProcess.build(process, *args)
      process.io.stdout, process.io.stderr = std_out_err
      process.start
      process.wait
      @last_stdout = output(process.io.stdout)
      @last_stderr = output(process.io.stderr)
    end

    def std_out_err
      return ::Tempfile.new("brazenhead-proc-out-#{now}"), ::Tempfile.new("brazenhead-proc-err-#{now}")
    end

    private
    def output(file)
      file.rewind
      out = file.read
      file.close
      file.unlink
      out
    end

    def now
      Time.now.gsub(':', '_')
    end
  end
end
