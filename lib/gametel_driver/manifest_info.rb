require 'childprocess'

class ManifestInfo
  def initialize(apk)
    @apk = apk
    manifest
  end

  private
  def manifest
    process = ChildProcess.build('aapt', 'dump', 'xmltree', @apk, 'AndroidManifest.xml')
    process.wait
  end
end
