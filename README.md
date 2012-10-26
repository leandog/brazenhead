# Brazenhead
[![Build Status](https://secure.travis-ci.org/leandog/brazenhead.png?branch=master)](http://travis-ci.org/leandog/brazenhead)

A low-level driver for testing android applications.

## Basic Usage

Brazenhead works by modifying your Android application (apk).  Once modified and installed in an emulator or device, brazenhead can send request to the emulator or device and cause it to interact with your application via the [Robotium API](http://code.google.com/p/robotium/).

### Prerequisites

Before being able to instrument your application, you will need to add the `INTERNET` permission to your `AndroidManifest.xml`.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### Starting Instrumentation

To start instrumenting an application you must create an instance of the `Brazenhead::Server` by giving it the path to your android application (apk) an optionally some keystore information.  (**_note:_** _If no keystore information is provided, the `debug.keystore` will be used instead._)

```ruby
require 'brazenhead/server'

# using the debug.keystore
server = Brazenhead::Server.new "some/path/to/my.apk"
```

Now that you have a server instance, you can simply start any activity by giving it the name of the activity.  `brazenhead` will assume that it is a relative path based on the package name of your application.  If the package of your application is `com.example.some.awesome.app`, you can simply start the activity at `com.example.some.awesome.app.text.Flashy` like so:

```ruby
# create the server instance
server = Brazenhead::Server.new "some/path/to/my.apk"

#start the activity
server.start "text.Flashy"
```

### Stopping Tests

Stopping the server instance is just as easy.  Simply issue the following to stop the test server:

```ruby
server.stop
```

### Sending Commands

Messages sent to the emulator are json messages and  must take the form of 

````ruby
{ 
  name: 'theMethodName',  # The Robotium method to call
  arguments: [1, 2, 3],   # The arguments to be passed to the Robotium method
  variable: "@@variable_name_to_store_the_results@@",  # optional parameter to store the results 
                                                       # of a call - can be used in subsequent calls
  target:  'Robotium'     # optional parameter.  Valid values are 'Robotium', 'Brazenhead' or 'LastResultOrRobotium'
}
````

The `variable` parameter can be used in subsequent calls as an argument value.

In addition to a server process there is a ruby module that will build the json for you when you call corresponding methods.  Let's take a look at a few examples of how this works.

You can include the `Brazenhead` module in your class to provide the abilities.

````Ruby
class MyClass
  include Brazenhead
end
````

Once you do this you can call methods on an instance of `MyClass`.  These methods look like this:

### Calling a single method
````Ruby
my_class.scroll_down  # call the scrollDown method on Robotium
````

You can also pass parameters to methods like this:

````Ruby
my_class.click_on_text('Content')  # will call the method clickOnText passing 'Content'
````


### Calling multiple methods
````Ruby
my_class.chain_calls do |driver|
  driver.scroll_down                         # call scrollDown first
  driver.scroll_up({:target => 'Robotium'})  # then call scrollUp
end
````

In the case above we had to pass a `target` value to inform the server that we wished to make the second call directly on Robotium instead of on the return call from the previous call.  You can easily make the second call on the return value from the first call like this:

````Ruby
my_class.chain_calls do |driver|
  driver.get_current_list_views
  driver.size
end
my_class.last_response.body.should == '1'
````

In the above example the `size` method is called on the result of the call to `getCurrentListViews`.  If you want to use the result of a previous call as an argument in another call you can do this:


````Ruby
my_class.chain_calls do |driver|
  driver.get_text('Graphics', {:variable => "@@graphics@@"})
  driver.click_on_view("@@graphics@@", {:target => 'Robotium'})
end
````

In this final case we saved the result of the first call into a variable named `"@@graphics@@"` and used it as an argument in the second call.

## Known Issues

See [http://github.com/leandog/brazenhead/issues](http://github.com/leandog/brazenhead/issues)

## Contribute
 
* Fork the project.
* Test drive your feature addition or bug fix. Adding specs is important and I will not accept a pull request that does not have tests.
* Make sure you describe your new feature with a cucumber scenario.
* Make sure you provide RDoc comments for any new public method you add. Remember, others will be using this gem.
* Commit, do not mess with Rakefile, version, or ChangeLog.
  (if you want to have your own version, that is fine but bump version in a commit by itself I can ignore when I pull)
* Send me a pull request. Bonus points for topic branches.

## Copyright

Copyright (c) 2012 Jeffrey S. Morgan, Levi Wilson. See LICENSE for details.

