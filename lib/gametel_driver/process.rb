module GametelDriver
  class Process
    def run(process, *args)
      process = ChildProcess.build(process, *args)
      process.io.stdout, process.io.stderr = std_out_err
      process.wait
    end

    def std_out_err
      return ::Tempfile.new("gametel-proc-out-#{Time.now}"), ::Tempfile.new("gametel-proc-err-#{Time.now}")
    end
  end
end
