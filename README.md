# passport
A Kotlin-based Android text validation library.

Usage
-----
#### Create a Rule
````
val rule = Rule({ view, text -> !text.isNullOrEmpty() }, { "A value is required." })
````
#### Add to View
````
editText.validator().add(rule)
textInputLayout.validator().add(rule)
````
#### Validate
````
if(editText.validator().validate())
    // Valid!
````

See [an example activity](https://github.com/deadpixelsociety/passport/blob/master/example/src/main/kotlin/com/thedeadpixelsociety/passport/example/MainActivity.kt) for more in-depth usage.

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