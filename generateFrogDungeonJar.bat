set package=frog
set mainClass=frog.Main
set fileName=FrogDungeon.jar
set resources=..\resources

cd bin

dir

mkdir -p libs

copy ..\lib\processing_core.jar .\libs\processing_core.jar
cd libs
tar xf processing_core.jar
echo d | xcopy /E /y processing ..\processing

copy ..\..\lib\snakeyaml-1.23.jar snakeyaml-1.23.jar
tar xf snakeyaml-1.23.jar
echo d | xcopy /E /y .\org ..\org

copy ..\..\lib\javamp3-1.0.4.jar javamp3-1.0.4.jar
tar xf javamp3-1.0.4.jar
echo d | xcopy /E /y .\fr ..\fr

copy ..\..\lib\jsyn-20171016.jar jsyn-20171016.jar
tar xf jsyn-20171016.jar
echo d | xcopy /E /y .\com ..\com

copy ..\..\lib\sound.jar sound.jar
tar xf sound.jar
echo d | xcopy /E /y .\processing\sound ..\processing\sound

cd ..
rmdir .\libs /s /q

rem jar cvfe ..\dist\%fileName% %mainClass% %package% processing org ..\lib\sound.jar %resources%
jar cvfe ..\%fileName% %mainClass% %package% processing org fr com %resources%

rmdir processing /s /q
rmdir org /s /q
rmdir -p /s /q
rmdir fr /s /q
rmdir com /s /q

cd ..