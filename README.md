# passport
A Kotlin-based Android view validation library with a simple DSL.

Usage
-----
#### Create Rules
Capture the returned Passport object to invoke validation when required.
````
passport {
    with(editText, EditTextValidator()) {
        rule({ !it.isNullOrEmpty() }, { "A value is required." })
        rule({ it?.length > 3 ?: false }, { "The value must be 3 characters or more." })
    }
}
````
#### Validate
A specific view, view group, fragment or activity can be targeted for validation.
````
val validator = passport { <snip> }
if(validator.validate(this)) {
    // Valid!
}
````
#### Custom Validators
Custom validators allow for any view type to be managed. See the [SwitchCompat example](https://github.com/deadpixelsociety/passport/blob/master/example/src/main/kotlin/com/thedeadpixelsociety/passport/example/SwitchCompatValidator.kt).

See the [example activity](https://github.com/deadpixelsociety/passport/blob/master/example/src/main/kotlin/com/thedeadpixelsociety/passport/example/MainActivity.kt).

License
-------
````
The MIT License (MIT)

Copyright (c) 2015 deadpixelsociety

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