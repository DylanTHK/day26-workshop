use bgg;

// db.comment.find();
db.game.find();

// a) find an array of games with OFFSET & LIMIT
db.game.find({}).skip(0).limit(25);

// b) find all and return by rank


// c) Get the game details by gid (returns blank if not existing)
db.game.find({
    gid: 9999999
})

