#Initialise
source ./git-remote.sh
mkdir -p logs/history
rm -f vmstat.log

#Setting CLASS PATH
source ./set_classpath.sh

#{1..10}
#Experimental

java -cp $CLASSPATH -Xms4096m -Xmx4096m rda.agent.disposer.Dispose

# Change Property (System Parameter)
#java -cp $CLASSPATH rda.property.RewriteProperty ${i}

vmstat -n -S m -a 1 | awk '{print strftime("%Y-%m-%d %H:%M:%S.000"), $0} { system(":") }' > vmstat.log &

java -cp $CLASSPATH -Xms4096m -Xmx4096m rda.main.AgentSystemMain

killall vmstat
cat vmstat.log | awk '{print $1 " " $2 "," $15 "," $16}' > vmstat.csv

cp -r ../property logs/

mv vmstat.* logs/

#zip & dropbox
mv logs `hostname`_`date +%Y%m%d%H%M%S`_logs
zip -r `hostname`_`date +%Y%m%d%H%M%S`_logs.zip *_logs
cp *_logs.zip $CETA_HOME/App/dropbox_log
dropbox upload $CETA_HOME/App/dropbox_log/*.zip logs/
mv $CETA_HOME/App/dropbox_log/*.zip $CETA_HOME/App/dropbox_log/old/

rm -fr *_logs
rm -f *_logs.zip

#done