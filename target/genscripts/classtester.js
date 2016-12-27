
var nashClass = {
	chance: 3,
	name: "name",
	function: { reference:speedBoost, void: false},
	func2: { reference: methodTwo, void: false }
}




/**
**
	This will create and return a class reference from a json object. This should be one deep. All values will have setters and getters created
**
**/
function ingestJsonObject(blob, className) {
	var newClass = new UnbornClass("com.nashclasses."+className);
	for(var name in blob) {
		if (blob[name].reference!=undefined) {
			newClass.addMethod(name, blob[name].reference, blob[name].void);
		}
		else if(!(name.equals("extension") && name.equals("interfaces"))) {
			newClass.addField(name, blob[name]);
		}
	}
	if(blob.extension!=undefined) {
		newClass.setToExtend(blob.extension.reference);
	}
	if(blob.interfaces!=undefined) {
		for(var i in blob.interfaces) {
			newClass.addInterface(blob.interfaces[i]);
		}
	}
	var coreClass = newClass.bake();
	return coreClass;
}

function methodTwo(t) {
	System.out.println(t);
}

function speedBoost() {

}


for(var i = 0; i<=5; i++) System.out.println(" ");
var newObj = ingestJsonObject(nashClass, "ItemClass");

if(newObj!=undefined) {
	for(var i in newObj.getDeclaredMethods()) {
		System.out.println(newObj.getDeclaredMethods()[i]+" declared method at: "+i);
	}
	for(var i in newObj.getDeclaredFields()) {
		System.out.println(newObj.getDeclaredFields()[i]+" declared field at: "+i);
	}	
}

System.out.println(newObj.getClass().getName());
for(var i = 0; i<=5; i++) System.out.println(" ");

var NewClass = Java.type(newObj.getCanonicalName());

var classInstance = new NewClass();
var arr = new Array();
arr.push("test");
System.out.println(classInstance.methodTwo(arr));

