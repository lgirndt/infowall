import infowall.infrastructure.json.SimpleJSONBuilder

def builder = new SimpleJSONBuilder()

builder.foo(bar:1, baz: '2') {
          hobbies(sport: 'snooker', drink: 'guinness')
          emptyObj([:])
          emptyArray([])
};

print builder.json