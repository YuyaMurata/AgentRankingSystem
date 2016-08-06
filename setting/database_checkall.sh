
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

    #確認
    sshpass -p 11m35584 ssh h${i}@h${i} \
        "source /etc/profile;\
        cd $CETA_HOME/App/AgentSystem/AgentRankingSystem/setting;\
        ./clex.sh"

done