# day26-workshop
Day 26 Workshop - Query Document Database

1. pom.xml 
- add necessary dependency for mongo 

2. applications.properties
- mongo.url
- export MONGO_URL=mongodb://localhost:27017
- printenv (to check environment variables added)

3. AppConfig (MongoTemplate)
- methods to extract Document from mongo


## Working with documents
Retrieve values
- getString()
- getDouble()
- getDate()

Doc -> JSON string 
- doc.toJson();

JsonObject -> Doc (converts json string to Document)
- document.parse(json.toString());

## Flow
1. Requests to Controller
2. Controller -> Service
3. Service -> Repo
4. Repo -> MongoDB

## Queries in MongoDB
### a1) find an array of games with OFFSET & LIMIT (Multiple query)
```
===== Mongo =====
db.game.find({
    $and: [
        {gid: {$exists: true}},
        {name: {$exists: true}}
    ] 
}).skip(5).limit(30);
===== Java =====
Query query = Query.query(
    new Criteria().andOperator(
        Criteria.where("gid").exists(true),
        Criteria.where("name").exists(true))
).limit(limit).skip(offset); 

List<Document> docs = mongoTemplate.find(query, 
    Document.class, COLLECTION_GAME);
```
### a2) count TOTAL number of games
```
===== Mongo =====
db.game.find({}).count();
===== Java =====
Integer count = mongoTemplate
    .findAll(Document.class, COLLECTION_GAME)
    .size();
```
### b) find all and return by rank (ASC order)
```
===== Mongo =====
db.game.find({
    ranking: {$exists: true}
}).sort({ ranking: 1});
===== Java =====
Criteria c = Criteria.where("ranking").exists(true);
Query query = Query.query(c)
    .with(Sort.by(Sort.Direction.ASC, "ranking"))
    .skip(offset).limit(limit);
List<Document> result = mongoTemplate
    .find(query, Document.class, COLLECTION_GAME);
        
```
### c) Get the game details by gid (returns blank if not existing)
```
===== Mongo =====
db.game.find({
    gid: <game id>
});
===== Java =====

```
### c2) user_rated: query number of comments for based on id
```
===== Mongo =====
db.comment.find({
    gid: <game id>
}).count();
===== Java =====
Query query = Query.query(
    Criteria.where("gid").is(id));
Document result = mongoTemplate
    .findOne(query, Document.class, COLLECTION_GAME);

```

### additional: find query by mongo id
```
===== Mongo =====
db.comment.find({
    _id: ObjectId("63eafee609f5f8b5b10f399f")
});
===== Java =====
Criteria c = Criteria.where("gid").is(id);
Query query = Query.query(c);
List<Document> comments = mongoTemplate
    .find(query, Document.class, COLLECTION_COMMENT);

```
## Making Queries in Java
1. Criteria
2. Query (pass Criteria into Query)
3. MongoTemplate (query, result_type, template)