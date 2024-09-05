echo Run from the eclipse project folder
echo OPEN THIS FILE and set package=yourpackage
echo Ex: package=jhwang04.shapes.shapes

set package=frog

REM javadoc -author -version -tag pre:cm:"Preconditions:" -tag post:cm:"Postconditions:" -d doc -classpath ./lib/* -sourcepath ./src %package%

cd src
dir /s /b *.java > fileList.lst

javadoc -author -version -tag pre:cm:"Preconditions:" -tag post:cm:"Postconditions:" -d ../doc/javadoc -classpath ../lib/* -sourcepath @fileList.lst

del fileList.lst
cd ..