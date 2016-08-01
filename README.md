AmazonEchoApi
=============
We use seliunum to login echo console and do some operations.
The reason that we don't use API is because Amazon login page has a wired metadata1 hidden field, which is really difficult to parse it.


Basic Usage
=============
```
mvn eclipse:eclipse

mvn compile
mvn exec:java -Dexec.mainClass=amazonechoapi.AmazonEchoApi -Dexec.args="<username> <password>"
```  
