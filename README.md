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

- Code Coverage
- Known Issues
- Code Violations

An item is stored as
<pre>
{
    "value" : "10"
}
</pre>

#### Table View
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

## Configure Infowall
Infowall has a configuration directory on a local disc. The directory contains for instance the configuration
of the dasboard and its items or some executable scripts.

The directory has to be provided by a system property `-Dinfowall.config-root=TODIR`, otherwise
the application fails to start.

Infowall comes with an example directory at ROOT/example/config, have a look at it.

## Storing Items

Independent of a respective view, the JSON items have to be stored by Infowall. There are different
ways to provide the data to Infowall

### Using the REST API
You can push JSON documents to Infowall to the URL
`$APPLICATION_BASE_URL/app/item/$DASHBOARD_ID/$ITEM_NAME`

For instance, if the single item value is stored in the file value.json, you can use curl
to push it to Infowall. The example shows a coll to a Dashboard with the ID "first" and an item with the name "some-count" that runs on localhost:
<pre>
curl -v --upload-file src/scrapbook/json/value.json -H "Content-Type:application/json" http://localhost:8080/app/item/first/some-count
</pre>

### Using Groovy Jobs

You can store groovy scripts at $config-root/scripts/$dashboardId/$itemName.groovy, that
provide the JSON data for the defined item. These scripts will be triggerd by a cron like mechanism.
The triggering is configured in a the Dashboard configuration file.

The groovy script might perform arbitrary tasks, it simply has to return the JSON data to STDOUT.

For example, this script simply stores a random value as a single value item

<pre>
import infowall.infrastructure.json.SimpleJSONBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

final Logger logger = LoggerFactory.getLogger(getClass())

def builder = new SimpleJSONBuilder()

def rndVal = new Random().nextInt(30);
logger.info("Decided, we have #${rndVal} bugs now.");

builder.result(value:rndVal);
</pre>

See the example configuration for more examples of groovy scripts.

If you want to test a script, you can call
<pre>
curl http://localhost:8080/app/exec/$dashboardId/$itemName
</pre>
and you will retrieve the scripts output.

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

## Known limitations
- Just looks great on a Webkit based browser like Google Chrome
- Just works with HSLQDB


