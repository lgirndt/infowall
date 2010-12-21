# Infowall

## Quickstart

1. Pull from github `git clone git@github.com:lgirndt/infowall.git`
1. Build `mvn clean install`
1. Setup the example HSQLDB TODO
`java -cp /Users/lars/.m2/repository/org/hsqldb/hsqldb/2.0.0/hsqldb-2.0.0.jar:/Users/lars/.m2/repository/org/hsqldb/sqltool/2.0.0/sqltool-2.0.0.jar org.hsqldb.cmdline.SqlTool --inlineRc=url=jdbc:hsqldb:file:/tmp/infowall-prod.db,user=sa,password= src/main/resources/sql/create-schema.sql`
1. Insert some example data into infowall
1. run the server `mvn jetty:run`




