# property-database

Used for samples, this is a simple library for reading and writing properties to a text file using CRUD operations.

Sample usage:
```
PropsDB db = PropsDB.getDB("/tmp/myDB.properties");
db.insertProperty("favoriteColor", "blue");           // create
System.out.println(db.readProperty("favoriteColor")); // read -- prints "blue"
db.updateProperty("favoriteColor", "red");            // update
db.deleteProperty("favoriteColor");                   // delete
```
