# TextInlineImage-Library
A simple android library to add inline images with text in a TextView.

[![](https://jitpack.io/v/princ-bhardwaj/TextInlineImage-Library.svg)](https://jitpack.io/#princ-bhardwaj/TextInlineImage-Library)

## Prerequisites

Add this in your root build.gradle file (not your module build.gradle file):
  ```sh
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

## Dependency

Add this to your module's build.gradle file (make sure the version matches the JitPack badge above):
  ```sh
dependencies {
	...
	implementation 'com.github.princ-bhardwaj:TextInlineImage-Library:VERSION'
}
```

## Usage

Use this in your layout xml:

```sh
<com.android.lib.textinlineimage.TextInlineImage
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Here is the example of center: [img src=ic_emoji/]"
        android:textSize="16sp"
        android:layout_margin="20dp"
        app:imageSize="24dp"
        app:imageAlignment="center"/>
```
Here "[img src=ic_emoji/]" will replace resource drawable named with "ic_emoji".


## License

   Copyright 2019 Prince Bhardwaj

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.



