
rm -fR a? s? catalog

for i in `seq 1 $1`
do
    #遠隔でサーバーを停止
    sshpass -p 11m35584 ssh h${i}@h${i} \
        "source /etc/profile;\
        cd $CETA_HOME/App/AgentSystem/AgentRankingSystem/setting;\
        killall -9 java;\
        ps aux | grep java"

done