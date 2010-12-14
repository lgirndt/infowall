package infowall.infrastructure.json

import org.json.JSONStringer

/**
 * An alternative JSON builder, because <code>grails.util.JSonBuilder</code> sucks!
 * The reasons why it sucks are described here: http://www.anyware.co.uk/2005/2008/06/19/a-grails-json-builder-that-doesnt-suck/
 * Although this page provides an alternative JSON builder, the author didn't provide any usage examples, and I couldn't
 * figure out how to use it, so I wrote my own instead.
 *
 * The underlying JSON functionality is provided by <code>json.org.JSONStringer</code>. An example usage is:
 *
 * <code>
 * builder.array(['feck', 'arse', 'girls']) {
 *     foo(bar:'1', baz: '2') {
 *         hobbies(sport: 'snooker', drink: 'guinness')
 *         emptyObj([:])
 *         emptyArray([])
 *     }
 * }
 * builder.json
 * </code>
 *
 * This will print:
 * <code>
 * ["feck","arse","girls", {"bar":"1", "baz":"2", "hobbies": {"sport":"snooker", "drink":"guinness"}, "emptyObj": {},"emptyArray":[]}]
 * </code>
 *
 * Verifiable usage examples are provided by the unit tests. A few points worth noting (the term 'element' is used below
 * to mean 'either a JSON object or JSON array'):
 *
 * <ul>
 *   <li>The nesting of elements is indicated by the nesting of closures in the usual Groovy builder fashion</li>
 *   <li>The name of the method is used as the name of the key when nesting an element within an object</li>
 *   <li>The name of the method is irrelevant when nesting an element within an array, but it is recommended
 *   to use either the method name 'object' or 'array' for the sake of code readability</li>
 *   <li>The decision to create an array or object is determined by the type of the method parameter. A map will cause
 *   an object to be created, any other type will cause an array to be created</li>
 *   <li>To create an empty array or an array whose contents are determined only by nested closures, either call
 *   <code>builder.array()</code> or <code>builder.keyName([])</code>. The latter should be used when nesting the empty
 *   array within an object and control over the key name is required.</li>
 *   <li>To create an empty object or an object whose contents are determined only by nested closures, either call
 *   <code>builder.object()</code> or <code>builder.keyName([:])</code>. The latter should be used when nesting the empty
 *   object within another object and control over the key name is required.</li>
 * </ul>
 */
class SimpleJSONBuilder extends BuilderSupport {

    private jsonText = new JSONStringer()

    /**
     * Returns the JSON text created by this builder
     */
    String getJson() {
        jsonText.toString()
    }

    String toString() {
        getJson()
    }

    protected void setParent(Object parent, Object child) {
        // Method is abstract in parent class, but an empty implementation is all we need
    }

    /**
     * Creates an array or object which is either empty, or whose contents are determined exclusively by nested closures.
     */
    protected Object createNode(Object name) {

        if (current == ElementType.OBJECT) {
            throw new IllegalStateException("""Error processing method $name() Empty argument methods should not be invoked
when nesting an element within an object because the key name cannot be determined. Replace this call with either
$name([]) or $name([:])""")
        }

        if (name == 'array') {
            jsonText.array()
            return ElementType.ARRAY

        } else if (name == 'object') {
            jsonText.object()
            return ElementType.OBJECT

        } else {
            throw new IllegalArgumentException("""When calling a method with no arguments, the method must be named either
'$array' or '$object' to indicate which you wish to create""")
        }

    }

    protected Object createNode(Object name, Map attributes, Object value) {
        throw new UnsupportedOperationException("Error invoking method $name. Method calls must supply either a single object (to create an array) or a Map (to create an object)")
    }

    /**
     * Ensures that an array/object is correctly nested within an object
     * @name Name of the key to use for the nested element
     * @return The type of element
     */
    private void nestElement(name) {
        if (current == ElementType.OBJECT) {
            jsonText.key(name)
        }
    }

    /**
     * Creates an array
     * @name Name of the method. This will be used as the key if the array is nested within an object
     * @value The contents of the array. This should be either a single value or a collection or array
     * @return The type of element
     */
    protected Object createNode(Object name, Object value) {
        nestElement(name)
        jsonText.array()

        if (value instanceof Collection || value instanceof Object[]) {
            value.each {jsonText.value(it)}

        } else {
            jsonText.value(value)
        }
        return ElementType.ARRAY
    }

    /**
     * Creates an object
     * @name Name of the method. This will be used as the key if the object is nested within an object
     * @value The name-value pairs contained by this object
     * @return The type of element
     */
    protected Object createNode(Object name, Map attributes) {
        nestElement(name)
        jsonText.object()
        attributes.each {key, value ->
            jsonText.key(key).value(value)
        }
        return ElementType.OBJECT
    }

    protected void nodeCompleted(Object parent, Object node) {
        node == ElementType.OBJECT ? jsonText.endObject() : jsonText.endArray()
    }
}

private enum ElementType {
    ARRAY, OBJECT
}