import infowall.infrastructure.json.SimpleJSONBuilder

def builder = new SimpleJSONBuilder()

builder.array(['feck', 'arse', 'girls']) {
      foo(bar:'1', baz: '2') {
          hobbies(sport: 'snooker', drink: 'guinness')
          emptyObj([:])
          emptyArray([])
      }
};

System.out << builder.json