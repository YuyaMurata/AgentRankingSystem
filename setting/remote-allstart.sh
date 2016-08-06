
for i in `seq 1 $1`
do
    #確認
    echo ""
    echo " -------------------  Server  ---------------------------"
    echo ""
    sshpass -p 11m35584 ssh h${i}@h${i} 'echo `hostname`'
    echo ""
    echo " --------------------------------------------------------"
    echo ""

    #遠隔で実験用スクリプトを起動
    sshpass -p 11m35584 ssh h${i}@h${i} \
        "source /etc/profile;\
        cd $CETA_HOME/App/AgentSystem/AgentRankingSystem/setting;\
        pwd;\
        ./h${i}start.sh"
     
    #権限
    sshpass -p 11m35584 ssh root@h${i} \
        "source /etc/profile;\
        cd $CETA_HOME/App/AgentSystem/AgentRankingSystem/setting;\
        chmod -R 777 *"

done