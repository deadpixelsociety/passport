# passport
A Kotlin-based Android view validation library with a simple DSL.

[![](https://jitpack.io/v/deadpixelsociety/passport.svg)](https://jitpack.io/#deadpixelsociety/passport)

Usage
-----
#### Create Rules
Use the existing DSL rules or create custom rules to fit your needs. Capture the returned Passport 
object to invoke validation when required.
````
passport {
    rules<String>(phoneEdit) {
        numeric(getString(R.string.valid_phone_required))
    }
    
    rules<String>(emailLayout) {
        email()
        length(8, 32)
    }
    
    rules<Boolean>(switchView) {
        rule({ it }, { "The switch must be on." })
    }
}
````

You can also use the property delegate ``validator`` to setup your Passport object.
````
val validationRules by validator {
    rules<String>(phoneEdit) {
        numeric(getString(R.string.valid_phone_required))
    }
    
    rules<String>(emailLayout) {
        email()
        length(8, 32)
    }
    
    rules<Boolean>(switchView) {
        rule({ it }, { "The switch must be on." })
    }
}
````

#### Validate
A specific view, view group, fragment or activity can be targeted for validation.
````
val validator = passport { <snip> }
if(validator.validate(this, ValidationMethod.IMMEDIATE)) {
    // Valid!
}
````
Validation supports batch, fail fast, and immediate modes:
* Batch - All views and rules are processed before validation is complete.
* Fail Fast - All views are processed. For each view all rules are processed until the first failure 
is encountered.
* Immediate - All views and rules are processed until a failure is found and validation completes 
immediately.

#### Validator Factories
Validators are provided to passport via validator factory functions.
````
Passport.validatorFactory({ TextViewValidator() })
````
Passport will assign the correct validator to a view during rule assignment based on it's class 
type. A specific validator can be assigned instead if desired. 

#### Custom Validators
Custom validators allow for any view type to be managed. See the [SwitchCompat example](https://github.com/deadpixelsociety/passport/blob/master/example/src/main/kotlin/com/thedeadpixelsociety/passport/example/SwitchCompatValidator.kt).

See the [example activity](https://github.com/deadpixelsociety/passport/blob/master/example/src/main/kotlin/com/thedeadpixelsociety/passport/example/MainActivity.kt).

#### Gradle
````
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}

dependencies {
    compile 'com.github.deadpixelsociety.passport:core:2.1.0'
    // 'design' includes a validator for the TextInputLayout view in the design support library. 
    //compile 'com.github.deadpixelsociety.passport:design:2.1.0'
    // 'support-fragment' includes support for the v4 Fragment class.
    //compile 'com.github.deadpixelsociety.passport:support-fragment:2.1.0'
}
````

License
-------
````
The MIT License (MIT)

Copyright (c) 2018 deadpixelsociety

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
````
