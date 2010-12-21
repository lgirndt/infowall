# Infowall

## Quickstart

1. Pull from github `git clone git@github.com:lgirndt/infowall.git`
1. Build `mvn clean install`

1. Setup the example HSQLDB TODO
<pre>
MVN_REPO=~/.m2/repository
export CLASSPATH=$MVN_REPO/org/hsqldb/hsqldb/2.0.0/hsqldb-2.0.0.jar:$MVN_REPO/org/hsqldb/sqltool/2.0.0/sqltool-2.0.0.jar
DB_FILE=/tmp/infowall-prod.db

java org.hsqldb.cmdline.SqlTool --inlineRc=url=jdbc:hsqldb:file:$DB_FILE,user=sa,password= src/main/resources/sql/create-schema.sql
</pre>

1. Insert some example data into infowall
1. run the server `mvn jetty:run`




