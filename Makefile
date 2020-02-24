JAR_NAME = p1608040.jar

all: bytecode
	javac -cp 'lib/*' -d bytecode src/java_etu/*.java

exec:
	java -cp 'bytecode:lib/gs-algo-1.1.2.jar:lib/gs-core-1.1.2.jar:lib/gs-ui-1.1.2.jar' java_etu.Java_Etu

jar: tmp extract
	cp -r bytecode/* lib/* tmp/ && cd tmp/ && rm *.jar && jar cfe $(JAR_NAME) java_etu.Java_Etu * && mv $(JAR_NAME) ..

clean:
	rm -rf bytecode/ tmp/ $(JAR_NAME)

jar2: tmp
	cp -r bytecode/* tmp/ && cd tmp/ && jar cfe $(JAR_NAME) java_etu.Java_Etu * && mv $(JAR_NAME) ..

bytecode:
	if [ ! -d bytecode ]; then mkdir bytecode/; fi

extract:
	cd lib/ && jar xf gs-algo-1.1.2.jar && jar xf gs-core-1.1.2.jar && jar xf gs-ui-1.1.2.jar

tmp:
	if [ ! -d tmp ]; then mkdir tmp/; fi

.PHONY: bytecode tmp
