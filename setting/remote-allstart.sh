#確認
sshpass -p 11m35584 ssh $1@$1 'echo `hostname`'
echo "確認後 Enter"
read

#遠隔で実験用スクリプトを起動
sshpass -p 11m35584 ssh $1@$1 \
"source /etc/profile;\
echo $CETA_HOME"
#cd $CETA_HOME/App/AgentSystem/AgentRankingSystem/setting;\
#pwd;\
#./$1start.sh;\
#sleep 2s;\
#showRegion"
