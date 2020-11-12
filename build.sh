#!/usr/bin/env bash
RED='\033[1;31m'
GREEN='\033[1;32m'
YELLOW='\033[1;33m'
PURPLE='\033[1;35m'
NC='\033[0m' # No Color

core=('core' '.' 'named' '')
enterprise=('enterprise' '.' 'named' 'logger.service')
kafka=('integration-kafka' '.' 'named' 'logger.service')
rabbitmq=('integration-rabbitmq' '.' 'named' '')
kafkaexample=('kafka-example' '.' 'named' '')
rabbitmqexample=('rabbitmq-example' '.' 'named' '')

modules=(core enterprise kafka rabbitmq kafkaexample rabbitmqexample)

recreateFolder() {
    folder="$1"
    if [ -d $folder ]; then
       rm -rf $folder/*
    else
       mkdir $folder
    fi
}

recreateFolder "out"
recreateFolder "lib"
recreateFolder "modules"

mvn package -Pmodule-api -DskipTests

compile() {
    moduleName="$1"
    projectName="$2"
    apiModuleType="$3"
    modulesToAdd="$4"

    mkdir -p out/$moduleName

    addModules=""
    if [ ! -z "$modulesToAdd" ]; then
       addModules="--add-modules $modulesToAdd"
    fi

    if [ $apiModuleType = "named" ]; then
       cd out && javac --module-path ../modules -cp "../lib/*"  -d $moduleName $(find ../$2/$1/src/main/java -name '*.java') && cd ..
    else
       cd out && javac --module-path ../modules -cp "../lib/*" $addModules -d $moduleName $(find ../$2/$1/src/main/java -name '*.java' -not -name 'module-info.java')
       cd ..
    fi

    if [ -d $2/$1/src/main/resource ]; then
	cp -rf $2/$1/src/main/resource/* out/$moduleName/.
    fi

    echo -e "${YELLOW}COMPILE ::${NC} Module $moduleName@$projectName compiled to ${GREEN}out/$moduleName${NC}"
}

createJar() {
    moduleName=$1
    if [ -f out/$moduleName/META-INF/MANIFEST.MF ]; then
	cd out/$moduleName && jar --create --file ../../lib/$moduleName.jar --manifest META-INF/MANIFEST.MF -c . && cd ../..
    else
	cd out/$moduleName && jar --create --file ../../lib/$moduleName.jar  -c . && cd ../..
    fi
    echo -e "${YELLOW}JAR     ::${NC} Module $moduleName packaged to ${GREEN}lib/$moduleName.jar${NC}"
}

moveModulePathIfNeed() {
    moduleName="$1"
    moduleType="$3"
    if [ $moduleType != "unnamed" ]; then
       echo -e "${YELLOW}MODULE  ::${NC} Module $moduleName moved to module path ${GREEN}modules${NC}"
       mv lib/$moduleName.jar modules/.
    fi
}

cnt=0
total_Array=${#modules[*]}
while [ $cnt -lt $total_Array ] ; do
        srch_fld=${modules[$cnt]}
                eval var1=\${$srch_fld[@]}
                eval moduleName=\${$srch_fld[0]}
                echo -e  "*********************** BUILDING ${PURPLE}$moduleName${NC} ************************"
                compile $var1
		createJar $var1
                moveModulePathIfNeed $var1
        ((cnt=cnt+1))
done

echo -e "${GREEN}!!!MODULES GENERATED FOR JEVENTBUS PROJECT!!!${NC}"

#if [ $apiModuleType = "named" ]; then
   #java --module-path modules  -cp "lib/*" --module jeventbus.example.rabbitmq/jeventbus.rabbitmq.example.EventConsumer
   #java --module-path modules  -cp "lib/*" --module jeventbus.example.rabbitmq/jeventbus.rabbitmq.example.EventProducer
#elif [ $apiModuleType = "automatic" ]; then
   #java --module-path modules --add-modules ${api[3]} -cp "lib/*" --module jeventbus.example.rabbitmq/jeventbus.rabbitmq.example.EventConsumer
   #java --module-path modules --add-modules ${api[3]} -cp "lib/*" --module jeventbus.example.rabbitmq/jeventbus.rabbitmq.example.EventProducer
#else
   #java --module-path modules -cp "lib/*" jeventbus.rabbitmq.example.EventConsumer
   #java --module-path modules -cp "lib/*" jeventbus.rabbitmq.example.EventProducer
#fi
