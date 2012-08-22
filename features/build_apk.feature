Feature:  Build the apk with the proper Manifast

We need to:
  * rewrite the manfiest to point to the package that they are trying to instrument
  *  sign the new apk with their keystore information
  * launch instrumentation to indicate both the package and the full launcherActivityName that starts their application

Also should maybe:
  *  rewrite the package name to be com.thier.package.gametel.test?
  *  ability to configure information (package, launcher activity, keystore information) (yml?)
  *  we only need to build this apk once (or when information changes)
  *  in the After_Configuration hook?


