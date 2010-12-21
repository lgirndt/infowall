# Infowall

## Overview
Infowall is a Dashboard application, which displays a team's status on a wall
mounted display. A team status is broken down into single items of information. Each item is
displayed on its own and the dashboard cycles through all of them, displaying a single item for
distinct period of time.

Infowall maintains different "Dashboards", each keeping an seperate list of items to cycle through.

### Dashboard Items

Dashboard Items represent a unit of information displayed on the dashboard. They could be retrieved
from various resources and are stored as JSON in a database. There are different View types requiring
a certain format of the stored JSON item.

Currently there are the following types of Views:

#### Single Value View
Displays single value in a huge colored box. A Threshold could be configured for the value,
if the current value is better than the threshold, the background is green, otherwise red. The relation,
which determines "being better" could be configured (either "less than" or "greater than"). Optionally
a unit (like "%") could be shown.

Furthermore the View shows the amount of difference to the last update of the item, colored green if it has improved,
red otherwise and the duration, how long item has the current value.

This View should be used for distinct quantifiable, trackable pieces of the team's status, like:
* Code Coverage
* Known Issues
* Code Violations

An item is stored as
<pre>
{
    "value" : "10"
}
</pre>

### Table View
Displays a tabular listing of a descriptive texts with some value.

If the value is 0, the color is of the row is green, otherwise red.

An item is stored as
<pre>
{
    "table":[
        {"title":"This is the first row","value":"0"},
        {"title":"This is the second row","value":"14"},
        {"title":"This is the third row","value":"0"},
        {"title":"This is the 4th row,"value":"2"}
]
}
</pre>

## Storing Items

Independent of a respective view, the JSON items have to be stored by Infowall. There different
ways to provide the data to Infowall

### Using the REST API
You can push JSON documents to Infowall to the URL
`$APPLICATION_BASE_URL/app/item/$DASHBOARD_ID/$ITEM_NAME`

For instance, if the single item value is stored in the file value.json, you can use curl
to push it to Infowall. The example shows a coll to a Dashboard with the ID "first" and an item with the name "some-count" that runs on localhost:
<pre>
curl -v --upload-file src/scrapbook/json/value.json -H "Content-Type:application/json" http://localhost:8080/app/item/first/some-count
</pre>

###

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




