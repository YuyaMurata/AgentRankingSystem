
for i in `seq 1 $1`
do
    #確認
    sshpass -p 11m35584 ssh h${i}@h${i} 'echo `hostname`'
    echo "確認後 Enter"
    read

    #遠隔で実験用スクリプトを起動
    sshpass -p 11m35584 ssh h${i}@h${i} \
        "source /etc/profile;\
        cd $CETA_HOME/App/AgentSystem/AgentRankingSystem/setting;\
        pwd;\
        ./h${i}start.sh;\
        sleep 4s;\
        showRegion"

done