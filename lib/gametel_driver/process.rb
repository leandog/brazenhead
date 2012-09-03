module GametelDriver
  class Process
    def run(process, *args)
      process = ChildProcess.build(process, *args)
      process.wait
    end

  end
end
