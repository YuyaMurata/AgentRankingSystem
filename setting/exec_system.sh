#Initialise
source ./git-remote.sh
mkdir -p logs/history
rm -f vmstat.log

#Setting CLASS PATH
source ./set_classpath.sh

#{1..10}
#20 30 40 50 60 70 80 90 100
#200 300 400 500 600 700 800 900 1000
#2000 3000 4000 5000 6000 7000 8000 9000 10000
#Experimental
#for i in $1
#do

    #mkdir logs/${LOG_FD}${i}

    # Change Property (System Parameter)
    #java -cp $CLASSPATH rda.property.RewriteProperty ${i}

    #vmstat -n -S m -a 1 | awk '{print strftime("%Y-%m-%d %H:%M:%S.000"), $0} { system(":") }' > vmstat.log &

    #java -cp $CLASSPATH -Xms4096m -Xmx4096m rdarank.main.RankingSystemMain
    java -cp $CLASSPATH -Xms4096m -Xmx4096m rdarank.main.RankingSystemMain2
    #java -cp $CLASSPATH rdarank.main.AgentLoggingMain
    #java -cp $CLASSPATH rda.test.ReadTest
    
    #java -cp $CLASSPATH rda.agent.disposer.Dispose

    #killall vmstat
    #cat vmstat.log | awk '{print $1 " " $2 "," $15 "," $16}' > vmstat.csv

    #cp logs/*.csv logs/${LOG_FD}${i}
    #cp -r logs/history logs/${LOG_FD}${i}
    #cp -r ../property logs/${LOG_FD}${i}

    #rm -f logs/*.csv
    #rm -f logs/history/*

    #mv vmstat.* logs/${LOG_FD}${i}

    #java -cp $CLASSPATH rda.result.ResultsDataForming ${LOG_FD}${i}
    
    #java -cp $CLASSPATH plot.DataRegenerate ${LOG_FD}${i}

#done