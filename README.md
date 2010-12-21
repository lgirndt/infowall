# Infowall

## Overview
Infowall is a Dashboard application, which displays a team's status on a wall
mounted display. A team status is broken down into single items of information. Each item is
displayed on its own and the dashboard cycles through all of them, displaying a single item for
distinct period of time.

### Dashboard Items

Dashboard Items represent a unit of information displayed on the dashboard. They could be retrieved
from various resources and are stored as JSON in a database. There are different View types requiring
a certain format of the stored JSON item.

Currently there are the following types of Views:

#### Single Value View
Just a single value is displayed in a huge colored box. A Threshold could be configured for the value,
if the current value is better than the threshold, the background is green, otherwise red. The relation,
which determines "being better" could be configured (either "less than" or "greater than"). Optionally
a unit (like "%") could be shown.

Furthermore the View shows the delta to the last update of the item, colored green if it has improved,
red otherwise.

This View should be used for distinct quantifiable, trackable pieces of the team's status, like:
* Code Coverage
* Known Issues
* Code Violations

An item should be stored as
<pre>
{
    "value" : "10"
}
</pre>

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




