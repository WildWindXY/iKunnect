# Debugging Log
## 2023/10/16
Problem: UTF-8 and DefaultChar conflict for translation (in particular Chinese characters) \
Solutions (for IntelliJ users):
- Click Help->Edit Custom VM Options，add the following commands:\
    -Xmx4096m\
    -Dfile.encoding=UTF-8\
    -Duser.language=zh\
    -Duser.country=CN
- Click Setting->Build, Execution, Deployment ->Build Tools ->Gradle\
find iKunnect Project, and set "Build and run using" to "IntelliJ IDEA";
  "Run tests" using "IntelliJ IDEA"
- Note: if iKunnect is not found, then click on the drop-down bar to the left of the run button in the upper right corner, \
Edit Configuration-> "+" sign on the left upper side ->Application, the rest is as following:
![img.png](img.png)
- Click Setting->Editor->File Encodings, set the settings as below:
![img_1.png](img_1.png)
- Run the code again and then succeeded.